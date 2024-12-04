package com.app.csapp.models;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.ValueGenerationType;

@Entity
@Table(name = "tags")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag_name", nullable = false)
    private String tagName;
}