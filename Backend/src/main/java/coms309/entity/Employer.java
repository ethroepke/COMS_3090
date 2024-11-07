package coms309.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class representing an Employer.
 */
@Entity
@Getter
@Setter
@Table(name = "employer")
public class Employer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private Long employerId;

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_id", unique = true)
    @JsonBackReference
    private UserProfile userProfile;



    @ManyToMany(mappedBy = "employers")
    @JsonBackReference
    private Set<Projects> projects = new HashSet<>();



    public Employer(){}

    public void addProject(Projects project) {
        this.projects.add(project);
        project.getEmployers().add(this);
    }

    public void removeProject(Projects project) {
        this.projects.remove(project);
        project.getEmployers().remove(this);
    }
}
