
package coms309.controller;

import coms309.entity.PerformanceReview;
import coms309.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance-reviews")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService performanceReviewService;

    // Create a new performance review
    @PostMapping("/create")
    public PerformanceReview createPerformanceReview(@RequestBody PerformanceReview review) {
        return performanceReviewService.savePerformanceReview(review);
    }

    // Get performance reviews by username (or all reviews for admin)
    @GetMapping("/all")
    public List<PerformanceReview> getPerformanceReviews(@RequestParam(required = false) String username) {
        if (username != null) {
            return performanceReviewService.getPerformanceReviewsByUsername(username);
        }
        return performanceReviewService.getAllPerformanceReviews();
    }
}
