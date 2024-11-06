package coms309.repository;

import coms309.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

    // Find tasks by status (e.g., "Assigned", "In Progress", "Completed")
    List<Tasks> findByStatus(String status);

    // Find tasks by progress level (e.g., all tasks that are 100% completed)
    List<Tasks> findByProgress(int progress);

    // Find tasks that are partially completed (progress between a specified range)
    List<Tasks> findByProgressBetween(int minProgress, int maxProgress);

    // Find tasks by project ID
    List<Tasks> findByProjectProjectId(Long projectId);

    // Find tasks assigned to a specific user by user ID
    List<Tasks> findByAssignedEmployees_Id(Long userId);
}
