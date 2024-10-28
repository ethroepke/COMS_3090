package coms309.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
@Table(name= "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String message;

    private LocalDateTime timeStamp;


    public Notification(){}
    public Notification(String message, String type){
        this.message= message;
        this.type= type;
         this.timeStamp=LocalDateTime.now();

    }

}
