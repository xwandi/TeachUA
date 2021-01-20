package com.softserve.teachua.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User {

    @NonNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column
    private String email;

    @NonNull
    @Column
    private String password;

    @NonNull
    @Column
    private String name;


    @NonNull
    @ManyToOne
    private Role role;

    @OneToMany
    List<News> newsList;
}
