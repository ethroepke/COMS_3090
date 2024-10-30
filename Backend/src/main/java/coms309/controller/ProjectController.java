package coms309.controller;

import coms309.entity.Projects;
import coms309.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projects> getProjectById(@PathVariable Long id) {
        Optional<Projects> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(project.get());
    }

    
}
