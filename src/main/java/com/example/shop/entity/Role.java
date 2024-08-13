package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "roles")
@RequiredArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Getter
    @Column
    private String name;
}