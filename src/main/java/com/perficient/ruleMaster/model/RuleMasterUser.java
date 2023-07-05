package com.perficient.ruleMaster.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RuleMasterUser {

    @Id
    private UUID userId;

    private String name;

    private String lastName;

    private String email;

    private String password;

    private String role;

}
