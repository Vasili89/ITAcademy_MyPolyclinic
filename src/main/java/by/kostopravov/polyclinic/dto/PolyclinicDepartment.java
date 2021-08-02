package by.kostopravov.polyclinic.dto;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "polyclinic_department", schema = "polyclinic")
public class PolyclinicDepartment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String departmentName;

    @OneToMany(mappedBy="id")
    private Set<User> doctors;

    public PolyclinicDepartment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<User> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<User> doctors) {
        this.doctors = doctors;
    }
}
