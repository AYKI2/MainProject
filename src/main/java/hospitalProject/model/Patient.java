package hospitalProject.model;

import hospitalProject.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@Getter @Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "patients_gen")
    @SequenceGenerator(name = "patients_gen",
            sequenceName = "patients_seq",
            allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Name must not be empty!")
    @Size(max = 30, message = "Name cannot be longer than 30 characters!")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Surname must by not empty!")
    @Size(max = 30, message = "Surname cannot be longer than 30 characters!")
    @Column(name = "last_name")
    private String lastName;
    @Pattern(regexp = "^\\+996\\d{9}$", message = "Phone number must start with +996 and contain 12 digits")
    @Column(name = "phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty(message = "Email must by not empty!")
    @Email(message = "Incorrect email!")
    @Column(name = "email",unique = true)
    private String email;
    @ManyToOne(cascade = {
            DETACH,
            MERGE,
            PERSIST,
            REFRESH
    })
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @OneToMany(mappedBy = "patient", cascade = {ALL})
    private List<Appointment> appointments = new ArrayList<>();

    public Patient(String firstName, String lastName, String phoneNumber, Gender gender, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
    }
}
