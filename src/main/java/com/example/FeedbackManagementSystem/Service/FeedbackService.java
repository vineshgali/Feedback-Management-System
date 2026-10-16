
package com.example.FeedbackManagementSystem.Service;

import com.example.FeedbackManagementSystem.Entity.Feedback;
import com.example.FeedbackManagementSystem.Entity.User;
import com.example.FeedbackManagementSystem.Repository.FeedbackRepository;
import com.example.FeedbackManagementSystem.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Feedback createFeedback(String message, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found with ID: " + userId));

        Feedback feedback = new Feedback();
        feedback.setMessage(message);
        feedback.setUser(user);

        return feedbackRepository.save(feedback);
    }
}
