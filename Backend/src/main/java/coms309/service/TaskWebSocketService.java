package coms309.service;

import coms309.entity.Tasks;
import coms309.dto.TaskUpdateMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskWebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final String TASK_PROGRESS_DESTINATION = "/topic/task-progress";
    private static final String TASK_NOTIFICATION_DESTINATION = "/topic/task-notification";

    // Send a tasks progress update
    public void sendTaskUpdate(Tasks tasks) {
        TaskUpdateMessageDTO message = new TaskUpdateMessageDTO(
                tasks.getId(),
                tasks.getName(),
                tasks.getStatus(),
                tasks.getProgress()
        );
        messagingTemplate.convertAndSend(TASK_PROGRESS_DESTINATION, message);
    }

    // Send a custom notification message for task-related events
    public void sendTaskNotification(String notificationMessage) {
        messagingTemplate.convertAndSend(TASK_NOTIFICATION_DESTINATION, notificationMessage);
    }

    // Send an update to a specific user about a tasks
    public void sendTaskUpdateToUser(Long userId, Tasks tasks) {
        TaskUpdateMessageDTO message = new TaskUpdateMessageDTO(
                tasks.getId(),
                tasks.getName(),
                tasks.getStatus(),
                tasks.getProgress()
        );
        messagingTemplate.convertAndSend("/user/" + userId + "/queue/tasks-progress", message);
    }
}