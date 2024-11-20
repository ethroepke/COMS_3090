package coms309.service;

import coms309.dto.ProjectDTO;
import coms309.entity.*;
import coms309.repository.EmployerRepository;
import coms309.repository.ProjectRepository;
import coms309.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    public List<Projects> getAllProjects() {
        return projectRepository.findAll();
    }

    public ResponseEntity<Projects> getProjectById(Long projectId) {
        Optional<Projects> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<String> createProject(ProjectDTO projectDTO) {
        Optional<Projects> existingProject = projectRepository.findByProjectName(projectDTO.getProjectName());
        if (existingProject.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Project with the same name already exists");
        }
        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUsername(projectDTO.getEmployerUsername());
        if (!userProfileOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with username " + projectDTO.getEmployerUsername() + " does not exist");
        }
        UserProfile userProfile = userProfileOpt.get();
        if (userProfile.getUserType() != UserType.EMPLOYER) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with username " + projectDTO.getEmployerUsername() + " is not an employer");
        }
        Projects project = new Projects();
        project.setProjectId(projectDTO.getProjectId());
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        project.setDueDate(projectDTO.getDueDate());
        project.setPriority(projectDTO.getPriority());
        project.setEmployer(userProfile.getEmployer());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus(projectDTO.getStatus());


        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body("Project created successfully");
    }
}


//    @Transactional
//    public ResponseEntity<String> updateProject(Long id, ProjectDTO projectDTO) {
//        Optional<Projects> existingProjectOpt = projectRepository.findById(id);
//        if (!existingProjectOpt.isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        Projects existingProject = existingProjectOpt.get();
//
//        // Update fields if they are provided
//        if (projectDTO.getProjectName() != null) {
//            existingProject.setProjectName(projectDTO.getProjectName());
//        }
//        if (projectDTO.getDescription() != null) {
//            existingProject.setDescription(projectDTO.getDescription());
//        }
//        if (projectDTO.getDueDate() != null) {
//            existingProject.setDueDate(projectDTO.getDueDate());
//        }
//        if (projectDTO.getStatus() != null) {
//            existingProject.setStatus(projectDTO.getStatus());
//        }

        // Handle employer assignment
////        String employerUsername = projectDTO.getEmployerUsername();
//
//        if (employerUsername != null) {
//            // Fetch the UserProfile by username
//            Optional<UserProfile> userProfileOpt = userProfileRepository.findByUsername(employerUsername);
//            if (!userProfileOpt.isPresent()) {
//                return ResponseEntity.badRequest().body("User with username " + employerUsername + " does not exist");
//            }
//
//            UserProfile userProfile = userProfileOpt.get();
//
//            // Check if the UserProfile is of type EMPLOYER
//            if (userProfile.getUserType() != UserType.EMPLOYER) {
//                return ResponseEntity.badRequest().body("User with username " + employerUsername + " is not an employer");
//            }
//
//            // Get the Employer associated with the UserProfile
//            Employer newEmployer = userProfile.getEmployer();
//            if (newEmployer == null) {
//                return ResponseEntity.badRequest().body("Employer profile not found for username " + employerUsername);
//            }
//
//            // Disassociate existing employer if different
//            Employer currentEmployer = existingProject.getEmployer();
//            if (currentEmployer != null && !currentEmployer.equals(newEmployer)) {
//                currentEmployer.getProjects().remove(existingProject);
//            }
//
//            // Associate new employer
//            existingProject.setEmployer(newEmployer);
//            newEmployer.getProjects().add(existingProject);
//        }
//
//        // Save the updated project
//        projectRepository.save(existingProject);
//
//        return ResponseEntity.ok("Project updated successfully");
//
//
//    }

//    private Set<Employer> validateAndFetchEmployers(List<String> employerUsernames) {
//
//        if (employerUsernames == null || employerUsernames.isEmpty()) {
//            // No employer usernames provided
//            return Collections.emptySet();
//        }
//
//        // Fetch employers from the UserProfileRepository based on usernames and UserType
//        List<Employer> employers = userProfileRepository.findAllByUserProfileUsernameInAndUserProfileUserType(
//                employerUsernames,
//                UserType.EMPLOYER
//        );
//
//        // Collect found usernames
//        Set<String> foundUsernames = employers.stream()
//                .map(emp -> emp.getUserProfile().getUsername())
//                .collect(Collectors.toSet());
//
//        // Determine which usernames were not found or are not of type EMPLOYER
//        Set<String> inputUsernames = new HashSet<>(employerUsernames);
//        inputUsernames.removeAll(foundUsernames);
//
//        if (!inputUsernames.isEmpty()) {
//            throw new IllegalArgumentException("Employers not found or not of type EMPLOYER: " + String.join(", ", inputUsernames));
//        }
//
//        // Return the set of validated employers
//        return new HashSet<>(employers);
//    }
