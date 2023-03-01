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
@Table(name="departments")
@NoArgsConstructor
@Getter @Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="department_gen")
    @SequenceGenerator(name = "department_gen",
            sequenceName = "department_seq",
            allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Department must not be empty!")
    @Size(min = 2,max = 30,message = "Length cannot be longer than 30 characters!")
    private String name;
    private String image;
    @ManyToOne(cascade = {
            DETACH,
            MERGE,
            REFRESH,
            PERSIST
    })
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;
    @ManyToMany(mappedBy = "departments",cascade = {
            DETACH,
            MERGE,
            REFRESH,
            PERSIST
    })
    private List<Doctor> doctors = new ArrayList<>();

    public Department(String name,String image) {
        this.name = name;
        this.image = image;
    }
}
