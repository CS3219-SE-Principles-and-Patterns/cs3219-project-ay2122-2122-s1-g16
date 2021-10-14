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

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.enabled = true, u.activationTime= ?2 WHERE u.email = ?1")
    int updateActivationStatus(String email, LocalDateTime dateTime);
}
