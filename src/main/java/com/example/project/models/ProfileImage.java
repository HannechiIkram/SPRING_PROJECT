package com.example.project.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="Image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // other fields and methods
}

