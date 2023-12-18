package com.example.demo.entity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @Column
    private String title;
    @Column
    private Instant timestamp;

    public Long getId() {
        return id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "Article_Tag",
            joinColumns = { @JoinColumn(name = "article_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private Set<Tag> tags;

    @PrePersist
    public void prePersist() {
        this.timestamp = Instant.now();
    }

}