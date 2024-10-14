package coms309.repository;

import coms309.entity.PayDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayDetailRepository extends JpaRepository<PayDetails, Long> {
    Optional<PayDetails> findByUserProfileId(Long userProfileId);
}