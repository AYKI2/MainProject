package hospitalProject.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@Getter @Setter
public class Appointment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appointment_gen")
    @SequenceGenerator(
            name = "appointment_gen",
            sequenceName = "appointment_seq",
            allocationSize = 1)
    private Long Id;
    private LocalDate date;
    @ManyToOne(cascade = {
            DETACH,
            MERGE,
            PERSIST,
            REFRESH
    })
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(cascade = {
            DETACH,
            MERGE,
            PERSIST,
            REFRESH
    })
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    @ManyToOne(cascade = {
            DETACH,
            MERGE,
            PERSIST,
            REFRESH
    })
    @JoinColumn(name = "department_id")
    private Department department;

    @Transient
    private Long doctorId;
    @Transient
    private Long departmentId;
    @Transient
    private Long patientId;

    public Appointment(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment: " +
                "date = " + date +
                "patient =" + patient +
                "doctor = " + doctor +
                "department = " + department;
    }
}
