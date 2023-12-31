package com.perficient.ruleMaster.repository;

import com.perficient.ruleMaster.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {

    @Query("SELECT rule FROM Rule rule WHERE rule.ruleName = :name")
    Optional<Rule> findByName(@Param("name") String name);
}
