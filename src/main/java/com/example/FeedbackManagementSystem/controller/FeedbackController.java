package com.example.FeedbackManagementSystem.controller;

import com.example.FeedbackManagementSystem.Entity.Feedback;
import com.example.FeedbackManagementSystem.Repository.FeedbackRepository;
import com.example.FeedbackManagementSystem.Service.FeedbackService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final FeedbackRepository feedbackRepository;

    public FeedbackController(
            FeedbackService feedbackService,
            FeedbackRepository feedbackRepository) {
        this.feedbackService = feedbackService;
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Feedback createFeedback(
            @Valid @RequestBody FeedbackRequest request) {
        try {
            return feedbackService.createFeedback(
                    request.message(),
                    request.userId()
            );
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    // Get all feedback for the admin dashboard
    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    // Get feedback by ID
    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Feedback not found with ID: " + id
                        )
                );
    }

    public record FeedbackRequest(
            @NotBlank(message = "Message is required")
            String message,

            @NotNull(message = "User ID is required")
            Long userId
    ) {}
}
