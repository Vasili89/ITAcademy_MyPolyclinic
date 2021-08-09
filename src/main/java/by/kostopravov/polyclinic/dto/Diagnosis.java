package by.kostopravov.polyclinic.dto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosis", schema = "polyclinic")
public class Diagnosis {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @Column(name = "date")
    private LocalDateTime dateTime;

    @OneToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "medical_card_id")
    private MedicalCard medicalCard;

    public Diagnosis() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public MedicalCard getMedicalCard() {
        return medicalCard;
    }

    public void setMedicalCard(MedicalCard medicalCard) {
        this.medicalCard = medicalCard;
    }
}
