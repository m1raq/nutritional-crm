package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Table(name = "client")
@Entity
public class ClientEntity {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private String sex;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "tg_url")
    private String tgUrl;

    @Column(name = "tg_bot_chat_id")
    private String tgBotChatId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;

    @JoinColumn(name = "medical_history_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MedicalHistoryEntity medicalHistory;

    @Column(name = "meet_id")
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<MeetEntity> meets;

    @Column(name = "biomarkers_id")
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<BiomarkerEntity> biomarkers;

    @Column(name = "recommendation_id")
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<RecommendationEntity> recommendations;

    @Column(name = "documents_id")
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<DocumentEntity> documents;

}
