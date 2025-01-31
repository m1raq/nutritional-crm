package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Table(name = "meet")
@Entity
public class MeetEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "`end`")
    private LocalDateTime end;

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
