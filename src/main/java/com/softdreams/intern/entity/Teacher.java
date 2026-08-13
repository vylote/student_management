package com.softdreams.intern.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Teacher implements HasAccount{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String code;

    @Column(nullable = false)
    String fullName;

    @Column(nullable = false)
    String gender;

    @Column(nullable = false)
    LocalDate dateOfBirth;

    @Column(nullable = false)
    String department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", unique = true)
    Account account;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    List<Score> scores;
}
