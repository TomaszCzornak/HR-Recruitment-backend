package com.spring.hrrecruitmentbackend.registration.model.entity;

import com.spring.hrrecruitmentbackend.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Table(name = "resetoperations")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetOperations {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "users")
    private User user;
    @Column(name = "createdate")
    private String createDate;
    private String uuid;
}
