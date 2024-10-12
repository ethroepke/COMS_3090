package coms309.repository;

import coms309.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Projects, Long> {

}
