package com.example.FeedbackManagementSystem.Repository;

import com.example.FeedbackManagementSystem.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Long> {

    List<Feedback> findByUserId(Long userId);
}
