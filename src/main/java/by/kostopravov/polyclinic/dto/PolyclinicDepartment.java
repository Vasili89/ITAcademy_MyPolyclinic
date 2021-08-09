package by.kostopravov.polyclinic.dto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "polyclinic_department", schema = "polyclinic")
public class PolyclinicDepartment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="polyclinicDepartment")
    private List<User> doctors;

    public PolyclinicDepartment() {
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

    public List<User> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<User> doctors) {
        this.doctors = doctors;
    }
}
