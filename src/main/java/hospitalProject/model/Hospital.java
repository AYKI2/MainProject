package hospitalProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "hospitals")
@NoArgsConstructor
@Getter @Setter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "hospitals_gen")
    @SequenceGenerator(name = "hospitals_gen",
            sequenceName = "hospitals_seq",
    allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Name must not be empty!")
    @Size(max = 50,message = "Name cannot be longer than 50 characters!")
    @Column(unique = true)
    private String name;
    @NotEmpty(message = "Address must not be empty!")
    @Column(nullable = false)
    private String address;
    @NotEmpty(message = "Insert photo!")
    private String image;
    @OneToMany(mappedBy = "hospital",cascade = {ALL})
    private List<Doctor>doctors = new ArrayList<>();
    @OneToMany(mappedBy = "hospital",cascade = {ALL})
    private List<Patient>patients = new ArrayList<>();
    @OneToMany(mappedBy = "hospital",cascade = {ALL})
    private List<Department>departments = new ArrayList<>();
    @OneToMany(cascade = {ALL})
    private List<Appointment> appointments = new ArrayList<>();

    public Hospital(String name, String address, String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }
}
