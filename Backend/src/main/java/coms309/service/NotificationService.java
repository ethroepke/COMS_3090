package coms309.service;

import coms309.entity.Notification;
import coms309.entity.Projects;
import coms309.repository.NotificationRepository;
import coms309.repository.ProjectRepository;
import coms309.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public ResponseEntity<String> notifyEmployees(Long projectId) {
        Optional<Projects> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            Projects proj = project.get();
            String message = "Notification for project: " + proj.getProjectName();
            Notification notification = new Notification(message, "Project Information");
            notificationRepository.save(notification);
            return ResponseEntity.ok("Notification sent successfully");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> createMeetingNotification(String meetingDescription) {
        String message = "New meeting scheduled: " + meetingDescription;
        Notification notification = new Notification(message, "Meeting Information");
        notificationRepository.save(notification);
        return ResponseEntity.ok("Meeting notification created successfully");
    }

    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return ResponseEntity.ok(notifications);
    }

    public ResponseEntity<Notification> getNotificationById(Long id) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            return ResponseEntity.ok(notification.get());
        }
        return ResponseEntity.notFound().build();
    }

    public void sendNotification(String message) {
        Notification notification = new Notification(message, "General");
        notificationRepository.save(notification);
    }
}
