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

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_group")
    private UserGroup userGroup;

    @Column(name = "enabled")
    private boolean enabled = false;

    @Column(name = "activation_time")
    private LocalDateTime activationTime;

    @Column(name = "locked")
    private boolean locked = false;

    @Column(name = "token")
    private String token;

    @Column(name = "token_create_time")
    private LocalDateTime tokenCreateTime;

    @Column(name = "token_expire_time")
    private LocalDateTime tokenExpireTime;

    public User(String email, String username, String password, UserGroup userGroup) {
        this.email = email;
        this.username = username;
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

}
