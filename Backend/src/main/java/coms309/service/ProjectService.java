package coms309.service;

import coms309.dto.ProjectDTO;
import coms309.entity.Employer;
import coms309.entity.Projects;
import coms309.entity.UserProfile;
import coms309.entity.UserType;
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
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

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

    @Transactional
    public ResponseEntity<String> createProject(ProjectDTO projectDTO) {
        // Check if a project with the same name already exists
        Optional<Projects> existingProject = projectRepository.findByProjectName(projectDTO.getProjectName());
        if (existingProject.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Project with the same name already exists");
        }

        // Validate and fetch employers
        Set<Employer> employers;
        try {
            employers = validateAndFetchEmployers(projectDTO.getEmployerUsernames());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }


        Projects project = new Projects();
        project.setProjectName(projectDTO.getProjectName());
        project.setDescription(projectDTO.getDescription());
        project.setDueDate(projectDTO.getDueDate());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setPriority(projectDTO.getPriority());
        project.setStatus(projectDTO.getStatus());



        project.setEmployers(employers);
        employers.forEach(employer -> employer.getProjects().add(project));

        // Save the project
        projectRepository.save(project);

        return ResponseEntity.status(HttpStatus.CREATED).body("Project created successfully");
    }
//        Optional<Projects> existingProject = projectRepository.findByProjectName(projectDTO.getProjectName());
//        if (existingProject.isPresent()) {
//            return ResponseEntity.badRequest().body("Project with the same name already exists");
//        }
//
//
//        if (projectDTO.getPriority() == null) {
//            return ResponseEntity.badRequest().body("Priority level is required");
//        }
//
//
//        Projects project = new Projects();
//        project.setProjectName(projectDTO.getProjectName());
//        project.setDescription(projectDTO.getDescription());
//        project.setDueDate(projectDTO.getDueDate());
//        project.setPriority(projectDTO.getPriority());
//        project.setStatus(projectDTO.getStatus());
//
//
//
//
//        Set<Employer> employers = validateAndFetchEmployers(projectDTO.getEmployerUsernames());
//        project.setEmployers(employers);
//        employers.forEach(employer -> employer.getProjects().add(project));
//
//        projectRepository.save(project);
//        return ResponseEntity.ok("Project created successfully");


    @Transactional
    public ResponseEntity<String> updateProject(Long id, ProjectDTO projectDTO) {
        // 1. Retrieve the existing project by ID
        Optional<Projects> existingProjectOpt = projectRepository.findById(id);
        if (!existingProjectOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Projects existingProject = existingProjectOpt.get();

        existingProject.setDescription(projectDTO.getDescription());
        existingProject.setDueDate(projectDTO.getDueDate());
        existingProject.setProjectName(projectDTO.getProjectName());
        existingProject.setStatus(projectDTO.getStatus());

        if (projectDTO.getPriority() != null) {
            existingProject.setPriority(projectDTO.getPriority());
        }


        List<String> employerUsernames = projectDTO.getEmployerUsernames();

        if (employerUsernames != null) {

            Set<Employer> currentEmployers = existingProject.getEmployers();
            for (Employer employer : currentEmployers) {
                employer.getProjects().remove(existingProject);
            }
            existingProject.getEmployers().clear();


            if (!employerUsernames.isEmpty()) {

                List<Employer> newEmployers = userProfileRepository.findAllEmployersByUsernameInAndUserType(
                        employerUsernames,
                        UserType.EMPLOYER
                );


                if (newEmployers.size() != employerUsernames.size()) {

                    List<String> notFoundUsernames = new ArrayList<>();

                    for (String username : employerUsernames) {
                        boolean found = false;
                        for (Employer employer : newEmployers) {
                            if (employer.getUserProfile().getUsername().equals(username)) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            notFoundUsernames.add(username);
                        }
                    }


                    return ResponseEntity.badRequest().body(
                            "Employers not found or not of type EMPLOYER: " + String.join(", ", notFoundUsernames)
                    );
                }

                Set<Employer> employerSet = new HashSet<>();
                for (Employer employer : newEmployers) {
                    employerSet.add(employer);
                    employer.getProjects().add(existingProject); // Maintain bidirectional relationship
                }
                existingProject.setEmployers(employerSet);
            }
        }


        projectRepository.save(existingProject);

        return ResponseEntity.ok("Project updated successfully");
    }

    private Set<Employer> validateAndFetchEmployers(List<String> employerUsernames) {


        if (employerUsernames == null || employerUsernames.isEmpty()) {
            logger.debug("No employer usernames provided.");
            return Collections.emptySet();
        }

        logger.debug("Validating employer usernames: {}", employerUsernames);

        // Fetch employers from the EmployerRepository based on usernames and UserType
        List<Employer> employers = userProfileRepository.findAllEmployersByUsernameInAndUserType(
                employerUsernames,
                UserType.EMPLOYER
        );

        logger.debug("Employers fetched from repository: {}", employers);


        Set<String> foundUsernames = employers.stream()
                .map(emp -> emp.getUserProfile().getUsername())
                .collect(Collectors.toSet());

        logger.debug("Found usernames: {}", foundUsernames);

        // Determine which usernames were not found or are not of type EMPLOYER
        Set<String> inputUsernames = new HashSet<>(employerUsernames);
        inputUsernames.removeAll(foundUsernames);

        if (!inputUsernames.isEmpty()) {
            logger.warn("Employers not found or not of type EMPLOYER: {}", String.join(", ", inputUsernames));
            throw new IllegalArgumentException("Employers not found or not of type EMPLOYER: " + String.join(", ", inputUsernames));
        }

        logger.debug("Successfully validated employers: {}", foundUsernames);

        return new HashSet<>(employers);

    }
}