package coms309.repository;

import coms309.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Projects, Long> {
    Optional<Projects> findByProjectName(String projectName);

}
