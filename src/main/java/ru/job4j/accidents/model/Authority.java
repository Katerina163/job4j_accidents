package ru.job4j.accidents.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authorities")
@AllArgsConstructor
@NoArgsConstructor
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String authority;
}
