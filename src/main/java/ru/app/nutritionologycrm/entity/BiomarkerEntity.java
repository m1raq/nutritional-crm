package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "biomarker")
@Entity
public class BiomarkerEntity {

    @Column(name = "biomarker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "normalValue", nullable = false)
    private String normalValue;

    @Column(name = "clinical_references", nullable = false)
    private String clinicalReferences;

    @Column(name = "nutritionist", nullable = false)
    private String nutritionist;

    @Column(name = "unit", nullable = false)
    private String unit;

    @CreationTimestamp
    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private ClientEntity client;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;
}
