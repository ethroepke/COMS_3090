package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Table(name= "Request")
public enum RequestType {
    PENDING,
    APPROVED,
    REJECTED
}
