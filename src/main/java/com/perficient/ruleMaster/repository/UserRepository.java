package com.perficient.ruleMaster.repository;

import com.perficient.ruleMaster.model.RuleMasterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<RuleMasterUser, UUID> {

    @Query("SELECT user FROM RuleMasterUser user WHERE user.email = :email")
    Optional<RuleMasterUser> findByEmail(@Param("email") String email);
}
