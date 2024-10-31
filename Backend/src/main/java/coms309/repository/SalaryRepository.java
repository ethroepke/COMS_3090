package coms309.repository;
import coms309.entity.Projects;
import coms309.entity.Salary;
import coms309.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByUserProfile(UserProfile user);

}
