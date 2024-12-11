
package coms309.repository;

import coms309.entity.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long> {
    Optional<Projects> findByProjectId(Long projectId);
    Optional<Projects> findByProjectName(String projectName);

}
