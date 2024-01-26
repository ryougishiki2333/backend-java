package com.example.demo.controller;

import com.example.demo.entity.Reply;
import com.example.demo.repository.ReplyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://198.144.180.142", "http://viviblog.xyz/"})
@RestController
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyRepository replyRepository;

    public ReplyController(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    @GetMapping
    public ResponseEntity<String> findReplyByArticleId(@RequestParam Long id) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Reply> replies = replyRepository.findByArticleId(id);
            String responseBody = objectMapper.writeValueAsString(replies);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addReply(@RequestBody Reply newReply) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Reply savedReply = replyRepository.save(newReply);
            String responseBody = objectMapper.writeValueAsString(savedReply);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
