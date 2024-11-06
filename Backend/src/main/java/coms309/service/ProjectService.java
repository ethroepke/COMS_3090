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

    public ResponseEntity<String> createProject(ProjectDTO newProject) {
        Optional<Projects> existingProject = projectRepository.findByName(newProject.getProjectName());
        if (existingProject.isPresent()) {
            return ResponseEntity.badRequest().body("Projects with the same name already exists");
        }
        Projects projects = new Projects(newProject.getDescription(), newProject.getDueDate(), newProject.getProjectName(), newProject.getStatus());
        projectRepository.save(projects);
        return ResponseEntity.ok("Projects created successfully");
    }

    public ResponseEntity<String> updateProject(Long id, Projects updatedProjects) {
        Optional<Projects> existingProject = projectRepository.findById(id);
        if (existingProject.isPresent()) {
            Projects projects = existingProject.get();
            projects.setDescription(updatedProjects.getDescription());
            projects.setDueDate(updatedProjects.getDueDate());
            projects.setName(updatedProjects.getName());
            projects.setStatus(updatedProjects.getStatus());
            projectRepository.save(projects);
            return ResponseEntity.ok("Projects updated successfully");
        }
        return ResponseEntity.notFound().build();
    }



//    public Projects getProjects(Long projectId, Long userProfileId) {
//        Optional<Projects> projectOpt = projectRepository.findById(projectId);
//        Optional<Projects> userIdOpt = projectRepository.findById(userProfileId);
//
//
//            return null;
//    }
//
//
//    // view project with limit access for each user
//    private boolean canViewProject(UserProfile userProfile, Projects project) {
//        if (userProfile.getUserType() == UserType.EMPLOYER || userProfile.getUserType() == UserType.ADMIN) {
//            return true;
//        } else if (userProfile.getUserType() == UserType.EMPLOYEE) {
//            return project.getProjectId().equals(userProfile.getUserId()); // Employees can view only their own projects
//        }
//        return false;
//
//    }
//
//
//    private boolean canCommentOnProject(UserProfile userProfile, Projects project) {
//        if (userProfile.getUserType() == UserType.EMPLOYER || userProfile.getUserType() == UserType.EMPLOYEE) {
//            return true;
//        } else if (userProfile.getUserType() == UserType.ADMIN) {
//            return false;
//        }
//        return false;
//    }

}