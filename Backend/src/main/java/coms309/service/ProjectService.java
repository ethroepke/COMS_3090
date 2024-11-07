package coms309.service;

import coms309.dto.ProjectDTO;
import coms309.entity.Projects;
import coms309.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public List<Projects> getAllProjects() {
        return projectRepository.findAll();
    }

    public ResponseEntity<Projects> getProjectById(Long id) {
        Optional<Projects> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> createProject(Long id, ProjectDTO updatedProjectDTO) {
        Optional<Projects> existingProjectOpt = projectRepository.findById(id);
        if (existingProjectOpt.isPresent()) {
            Projects existingProject = existingProjectOpt.get();
            existingProject.setDescription(updatedProjectDTO.getDescription());
            existingProject.setDueDate(updatedProjectDTO.getDueDate());
            existingProject.setProjectName(updatedProjectDTO.getProjectName());
            existingProject.setStatus(updatedProjectDTO.getStatus());

            // Update priority if provided
            if (updatedProjectDTO.getPriority() != null) {
                existingProject.setPriority(updatedProjectDTO.getPriority());
            }

            projectRepository.save(existingProject);
            return ResponseEntity.ok("Project updated successfully");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> updateProject(Long id, Projects updatedProjects) {
        Optional<Projects> existingProject = projectRepository.findById(id);
        if (existingProject.isPresent()) {
            Projects projects = existingProject.get();
            projects.setDescription(updatedProjects.getDescription());
            projects.setDueDate(updatedProjects.getDueDate());
            projects.setProjectName(updatedProjects.getProjectName());
            projects.setStatus(updatedProjects.getStatus());
            projectRepository.save(projects);
            return ResponseEntity.ok("Projects updated successfully");
        }
        return ResponseEntity.notFound().build();
    }





}