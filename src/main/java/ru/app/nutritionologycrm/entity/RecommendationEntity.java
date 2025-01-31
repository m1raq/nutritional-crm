package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "recommendation")
@Entity
public class RecommendationEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "food_recommendation")
    private String foodRecommendation;

    @Column(name = "drinking_mode")
    private String drinkingMode;

    @Column(name = "nutraceuticals")
    private String nutraceuticals;

    @Column(name = "physical_activity", nullable = false)
    private String physicalActivity;

    @Column(name = "life_mode")
    private String lifeMode;

    @Column(name = "stress_control")
    private String stressControl;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private ClientEntity client;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;
}
