package org.cs3219.project.peerprep.model.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_group", nullable = false)
    private UserGroup userGroup;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = false;

    @Column(name = "locked", nullable = false)
    private boolean locked = false;

    @Column(name = "activate_token")
    private String activationToken;

    @Column(name = "activate_token_expire_time")
    private LocalDateTime activationTokenExpireTime;

    @Column(name = "activation_time")
    private LocalDateTime activationTime;

    @Column(name = "password_token")
    private String passwordToken;

    @Column(name = "password_token_expire_time")
    private LocalDateTime passwordTokenExpireTime;

    public User(String email, String nickname, String password, UserGroup userGroup) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userGroup = userGroup;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(userGroup.name());
        return Collections.singletonList(authority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
