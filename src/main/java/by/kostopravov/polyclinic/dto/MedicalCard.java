package by.kostopravov.polyclinic.dto;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medical_card", schema = "polyclinic")
public class MedicalCard {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private User owner;

    @OneToMany(mappedBy = "medicalCard")
    private List<Diagnosis> diagnosis;

    public MedicalCard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }
}
