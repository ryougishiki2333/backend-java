package com.example.demo.entity;

import com.example.demo.type.ArticleState;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @Column
    private String synopsis;

    @Column
    private String title;
    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    @Column
    private ArticleState articleState;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "Article_Tag", joinColumns = {@JoinColumn(name = "article_id")}, inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", content='" + content + '\'' + ", synopsis='" + synopsis + '\'' + ", title='" + title + '\'' + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", articleState=" + articleState + ", tags=" + tags + '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setArticleState(ArticleState articleState) {
        this.articleState = articleState;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}

