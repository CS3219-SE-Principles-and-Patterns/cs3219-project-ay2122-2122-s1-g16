package org.cs3219.project.peerprep.repository;

import org.cs3219.project.peerprep.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = true, u.activationTime= ?2 WHERE u.email = ?1")
    int updateActivationStatus(String email, LocalDateTime dateTime);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.passwordToken = ?2, u.passwordTokenExpireTime = ?3 WHERE u.email = ?1")
    int updatePasswordToken(String email, String token, LocalDateTime dateTime);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?2 WHERE u.email = ?1")
    int updatePassword(String email, String password);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.nickname = ?2 WHERE u.id = ?1")
    int updateNickname(Integer id, String nickname);
}
