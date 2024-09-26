package onetoone.Persons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 *
 * @author Vivek Bengre
 *
 */

public interface PersonRepository extends JpaRepository<Person, Long> {

    // Updated to return Optional for null safety
    Optional<Person> findById(Long id);

    @Transactional
    void deleteById(Long id);

    // Kept as is, assuming `laptop_id` is still int
    Person findByLaptop_Id(int id);
}