package com.familywebshop.stylet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscribed_users")
public class SubscribedUser {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

}
