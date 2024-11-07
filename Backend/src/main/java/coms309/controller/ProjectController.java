package coms309.controller;

import coms309.dto.ProjectDTO;

import coms309.entity.Notification;
import coms309.entity.Projects;

import coms309.service.NotificationService;
import coms309.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import coms309.service.ProjectService;
import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private final NotificationService notificationService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ProjectService projectService;


    public ProjectController( NotificationService notificationService , UserService userService, ProjectService projectService) {

        this.notificationService = notificationService;
        this.userService=userService;
        this.projectService = projectService;

    }

    @GetMapping("/all")
    public List<Projects> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projects> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@PathVariable Long id , @RequestBody ProjectDTO updatedProjectDTO) {
        return projectService.createProject(id, updatedProjectDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProject(@PathVariable Long id, @RequestBody Projects updatedProjects) {
        return projectService.updateProject(id, updatedProjects);
    }

    @PostMapping("/notify/{id}")
    public ResponseEntity<String> notifyEmployees(@PathVariable Long id) {
        return notificationService.notifyEmployees(id);
    }

    @PostMapping("/meeting/create")
    public ResponseEntity<String> createMeeting(@RequestBody String meetingDescription) {
        return notificationService.createMeetingNotification(meetingDescription);
    }

    @GetMapping("/user/{id}/next-shift")
    public ResponseEntity<String> getNextShift(@PathVariable Long id) {
        return userService.getNextShift(id);
    }

    @GetMapping("/user/{id}/time-worked")
    public ResponseEntity<String> getTimeWorked(@PathVariable Long id) {
        return userService.getTimeWorked(id);
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/notifications/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }


}



