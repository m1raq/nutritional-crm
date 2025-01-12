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

    @Column(name = "anthropometry", nullable = false)
    private String anthropometry;

    @Column(name = "life_mode", nullable = false)
    private String lifeMode;

    @Column(name = "complaints", nullable = false)
    private String complaints;

    @Column(name = "hypotheses", nullable = false)
    private String hypotheses;

    @Column(name = "nutrition", nullable = false)
    private String nutrition;

    @Column(name = "drinking_mode", nullable = false)
    private String drinkingMode;

    @Column(name = "physical_activity", nullable = false)
    private String physicalActivity;

    @Column(name = "goals", nullable = false)
    private String goals;

    @Column(name = "special_conditions", nullable = false)
    private String specialConditions;

    @OneToOne(mappedBy = "medicalHistory")
    private ClientEntity client;
}
