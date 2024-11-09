package coms309.service;

import coms309.dto.TaskDTO;
import coms309.entity.Tasks;
import coms309.entity.Projects;
import coms309.entity.User;
import coms309.exception.ResourceNotFoundException;
import coms309.repository.ProjectRepository;
import coms309.repository.TaskRepository;
import coms309.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository; // Add this line

    @Autowired
    private TaskWebSocketService taskWebSocketService;

    // Create a new task
    public Tasks createTask(TaskDTO taskDTO) {
        if (taskDTO.getProjectId() == null) {
            throw new IllegalArgumentException("Project ID must not be null");
        }

        // Fetch the project associated with the task
        Projects projects = projectRepository.findById(taskDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + taskDTO.getProjectId()));

        Tasks tasks = new Tasks();
        tasks.setName(taskDTO.getName());
        tasks.setDescription(taskDTO.getDescription());
        tasks.setStatus("Assigned");
        tasks.setProgress(0);
        tasks.setProject(projects); // Set the project

        taskRepository.save(tasks);

        // Notify all users about the new task creation
        taskWebSocketService.sendTaskNotification("New task created: " + tasks.getName());

        return tasks;
    }

    // Retrieve all tasks
    public List<Tasks> getAllTasks() {
        return taskRepository.findAll();
    }

    // Retrieve task by ID
    public Tasks getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasks not found with id: " + id));
    }

    public List<Tasks> getTasksByStatus(String status) {
        return taskRepository.findByStatus(status);
    }

    // Update an existing task
    @Transactional
    public Tasks updateTask(Long id, TaskDTO taskDTO) {
        Tasks tasks = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasks not found with id: " + id));

        tasks.setStatus(taskDTO.getStatus());
        tasks.setProgress(taskDTO.getProgress());
        taskRepository.save(tasks);

        // Trigger WebSocket update to all relevant users
        taskWebSocketService.sendTaskUpdate(tasks);

        return tasks;
    }

    // Delete a task by ID
    public void deleteTask(Long id) {
        Tasks tasks = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tasks not found with id: " + id));

        taskRepository.delete(tasks);

        // Notify about tasks deletion
        taskWebSocketService.sendTaskNotification("Tasks deleted: " + tasks.getName());
    }

    // Assign a user to a task
    @Transactional
    public Tasks assignTaskToUser(Long taskId, Long userId) {
        Tasks tasks = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Tasks not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        tasks.getAssignedEmployees().add(user);
        taskRepository.save(tasks);

        // Notify the specific user about tasks assignment
        taskWebSocketService.sendTaskUpdateToUser(userId, tasks);

        return tasks;
    }
}