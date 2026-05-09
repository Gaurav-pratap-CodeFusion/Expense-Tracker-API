package com.gpcf.expense_tracker_api.entity;

import com.gpcf.expense_tracker_api.enums.AppRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private AppRole roleName;
    public Role(AppRole roleName) {
        this.roleName = roleName;
    }
}