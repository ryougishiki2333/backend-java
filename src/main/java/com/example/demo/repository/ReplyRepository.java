package com.example.demo.repository;

import com.example.demo.entity.Reply;
import com.example.demo.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByArticleId(Long articleId);
}
