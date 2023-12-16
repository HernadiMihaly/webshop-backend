package com.familywebshop.stylet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String color;
    private Double price;
    private String description;
    private String materials;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductStock> productStock;

}
