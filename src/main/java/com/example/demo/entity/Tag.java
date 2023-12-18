package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles;
    @Column
    private Instant timestamp;

    public Long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
    public String getName() {
        return name;
    }

    @PrePersist
    public void prePersist() {
        this.timestamp = Instant.now();
    }

}