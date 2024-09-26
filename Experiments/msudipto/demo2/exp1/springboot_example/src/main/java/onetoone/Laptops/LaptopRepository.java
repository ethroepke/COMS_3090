package onetoone.Laptops;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author Vivek Bengre
 *
 */

public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    // Updated to return Optional for null safety and to match JpaRepository standards
    Optional<Laptop> findById(int id);

    @Transactional
    void deleteById(int id);
}