package ru.app.nutritionologycrm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
@Table(name = "`user`")
@Entity
public class UserEntity implements UserDetails {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Column(name = "client_id")
    private List<ClientEntity> clients;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Column(name = "biomarker_id")
    private List<BiomarkerEntity> biomarkers;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Column(name = "recommendation_id")
    private List<RecommendationEntity> recommendations;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Column(name = "meet_id")
    private List<MeetEntity> meets;

    @Column(name = "documents_id")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<DocumentEntity> documents;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleType role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
