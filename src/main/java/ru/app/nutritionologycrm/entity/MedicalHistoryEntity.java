package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Table(name = "medical_history")
@Entity
public class MedicalHistoryEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "anthropometry")
    private String anthropometry;

    @Column(name = "life_mode")
    private String lifeMode;

    @Column(name = "complaints")
    private String complaints;

    @Column(name = "hypotheses")
    private String hypotheses;

    @Column(name = "nutrition")
    private String nutrition;

    @Column(name = "drinking_mode")
    private String drinkingMode;

    @Column(name = "physical_activity")
    private String physicalActivity;

    @Column(name = "goals")
    private String goals;

    @Column(name = "special_conditions")
    private String specialConditions;

    @OneToOne(mappedBy = "medicalHistory")
    private ClientEntity client;
}
