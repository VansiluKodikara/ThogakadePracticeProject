package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "customer")

public class Customer {
    @Id
    private String id;
    private String title;
    private String name;
    private LocalDate dob;
    private Double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
