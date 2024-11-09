package coms309.controller;

import coms309.dto.TaskDTO;
import coms309.entity.Tasks;
import coms309.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    // Create a new task
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        try {
            Tasks tasks = taskService.createTask(taskDTO);
            return new ResponseEntity<>(tasks, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while creating task", e);
            return new ResponseEntity<>("Internal Server Error: Unable to create task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<?> getTasks() {
        try {
            List<Tasks> tasks = taskService.getAllTasks();
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving tasks", e);
            return new ResponseEntity<>("Internal Server Error: Unable to fetch tasks", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Long id) {
        try {
            Tasks tasks = taskService.getTaskById(id);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving task with ID: " + id, e);
            return new ResponseEntity<>("Internal Server Error: Unable to fetch task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get tasks by status
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTasksByStatus(@PathVariable String status) {
        try {
            List<Tasks> tasks = taskService.getTasksByStatus(status);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving tasks with status: " + status, e);
            return new ResponseEntity<>("Internal Server Error: Unable to fetch tasks by status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update task by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        try {
            Tasks updatedTasks = taskService.updateTask(id, taskDTO);
            return ResponseEntity.ok(updatedTasks);
        } catch (Exception e) {
            logger.error("Error occurred while updating task with ID: " + id, e);
            return new ResponseEntity<>("Internal Server Error: Unable to update task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error occurred while deleting task with ID: " + id, e);
            return new ResponseEntity<>("Internal Server Error: Unable to delete task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}