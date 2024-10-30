package org.sopt.diary.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long id;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    public UserEntity(final String loginId, final String password, final String nickname) {
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }

    public final Long getId() {
        return this.id;
    }

    public final String getPassword() {
        return this.password;
    }

    public UserEntity() {

    }
}