
package coms309.service;

import coms309.entity.Paycheck;
import coms309.entity.UserProfile;
import coms309.repository.PaycheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaycheckService {

    @Autowired
    private PaycheckRepository paycheckRepository;

    public Optional<Paycheck> getPaycheckForUser(UserProfile userProfile) {
        return paycheckRepository.findByUserProfile(userProfile);
    }

    public List<Paycheck> getAllPaychecksForUser(UserProfile userProfile) {
        return paycheckRepository.findAllByUserProfile(userProfile);
    }

    public Paycheck createOrUpdatePaycheck(Paycheck paycheck) {
        return paycheckRepository.save(paycheck);
    }

    public void deletePaycheck(Long paycheckId) {
        paycheckRepository.deleteById(paycheckId);
    }
}
