package coms309.controller;

import coms309.dto.TaskDTO;
import coms309.entity.Tasks;
import coms309.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Create a new task
    @PostMapping
    public ResponseEntity<Tasks> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        Tasks tasks = taskService.createTask(taskDTO);
        return new ResponseEntity<>(tasks, HttpStatus.CREATED);
    }

    // Get all tasks
    @GetMapping
    public ResponseEntity<List<Tasks>> getTasks() {
        List<Tasks> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    // Get task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tasks> getTaskById(@PathVariable Long id) {
        Tasks tasks = taskService.getTaskById(id);
        return ResponseEntity.ok(tasks);
    }

    // Get tasks by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Tasks>> getTasksByStatus(@PathVariable String status) {
        List<Tasks> tasks = taskService.getTasksByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    // Update task by ID
    @PutMapping("/{id}")
    public ResponseEntity<Tasks> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDTO taskDTO) {
        Tasks updatedTasks = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTasks);
    }

    // Delete task by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}