package com.example.shop.entity;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
@Data
public class Cart {
    private List<OrderItem> items;

    private Double price;

    @PostConstruct
    public void init(){
        items = new ArrayList<>();
        price = 0.0;
    }

    public void addOrIncrement(Product product){
        for (OrderItem item: items){
            if (item.getProduct().getId().equals(product.getId())){
                item.increment();
                recalculate();
                return;
            }
        }
        items.add(new OrderItem(product));
    }

    public void addOnly(Product product){
        for (OrderItem item: items){
            if (item.getProduct().getId().equals(product.getId())){
                items.add(new OrderItem(product));
                recalculate();
                return;
            }
        }
    }

    public void decrementOrRemove(Product product){
        Iterator<OrderItem> iter = items.iterator();
        while (iter.hasNext()){
            OrderItem item = iter.next();
            if (item.getProduct().getId().equals(product.getId())){
                item.decrement();
                if (item.getQuantity() == 0){
                    items.remove(item);
                }
                recalculate();
                return;
            }
        }
    }

    public void recalculate(){
        price = 0.0;
        for (OrderItem item: items){
            price += item.getPrice();
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clearCart() {
        items = new ArrayList<>();
    }
}
