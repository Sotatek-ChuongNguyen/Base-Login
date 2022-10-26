package com.capstone_project.hbts.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Entity
@Table(name = "Role")
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // default in spring security, Role is in format: ROLE_NAME, ex: ROLE_ADMIN
    @Column(name = "role_name")
    private String name;

    @ManyToOne @JoinColumn(name = "user_id")
    private Users users;

    public Role(Users users, String name) {
        this.users = users; this.name = name;
    }

}
