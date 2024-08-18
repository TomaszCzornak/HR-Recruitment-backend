package com.spring.hrrecruitmentbackend.candidate.model.entity;

import com.spring.hrrecruitmentbackend.recruitment.entity.Recruitment;
import com.spring.hrrecruitmentbackend.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Data
@Table(name="candidate")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @ManyToOne(fetch = FetchType.LAZY,  cascade=CascadeType.MERGE)
    @JoinColumn(name = "users", nullable = false)
    private User createdBy;
    @OneToMany(mappedBy = "candidate",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recruitment> recruitmentList;
}

