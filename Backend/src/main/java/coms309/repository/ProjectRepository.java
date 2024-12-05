package coms309.repository;

import coms309.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Projects, Long> {
    Optional<Projects> findByProjectId(Long projectId);
    Optional<Projects> findByProjectName(String projectName);
}
