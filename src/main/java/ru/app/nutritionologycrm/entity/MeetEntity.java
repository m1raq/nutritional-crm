package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Table(name = "meet")
@Entity
public class MeetEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "place")
    private String place;

    @Column(name = "duration")
    private String duration;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private ClientEntity client;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;
}
