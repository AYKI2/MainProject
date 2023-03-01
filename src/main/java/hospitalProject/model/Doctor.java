package hospitalProject.model;

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
@Table(name = "doctors")
@NoArgsConstructor
@Getter @Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "doctors_gen")
    @SequenceGenerator(name = "doctors_gen",
            sequenceName = "doctors_seq",
            allocationSize = 1)
    private Long Id;
    @NotEmpty(message = "Name must by not empty!")
    @Size(max = 30, message = "Name cannot be longer than 30 characters!")
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "Surname must by not empty!")
    @Size(max = 30, message = "Surname cannot be longer than 30 characters!")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Fill in the field!")
    private String position;
    @NotEmpty(message = "Email must by not empty!")
    @Email(message = "Incorrect email!")
    @Column(name = "email",unique = true)
    private String email;
    @NotEmpty(message = "Fill in the field!")
    private String image;
    @Transient
    private List<Long> departmentsId = new ArrayList<>();
    @ManyToOne(cascade = {
            DETACH,
            REFRESH,
            PERSIST,
            MERGE
    })
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @ManyToMany(cascade = {
            DETACH,
            REFRESH,
            PERSIST,
            MERGE
    })
    private List<Department> departments = new ArrayList<>();
    @OneToMany(mappedBy = "doctor",cascade = {ALL})
    private List<Appointment> appointments = new ArrayList<>();

    public Doctor(String firstName, String lastName, String position, String email, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.email = email;
        this.image = image;
    }
}
