package coms309.repository;

import coms309.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUsernameAndPassword(@Param("user_name") String username, @Param("password") String password);

    Optional<UserProfile> findByUsername(String username);

    Optional<UserProfile> findByEmail(String email);

    @Query("SELECT e FROM Employer e WHERE e.userProfile.username IN :usernames AND e.userProfile.userType = :userType")
    List<Employer> findAllEmployersByUsernameInAndUserType(@Param("usernames") List<String> usernames, @Param("userType") UserType userType);

    List<UserProfile> findAllByGroupChats(GroupChat groupChat);

}
