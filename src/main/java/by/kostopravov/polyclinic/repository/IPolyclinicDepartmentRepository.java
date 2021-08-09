package by.kostopravov.polyclinic.repository;

import by.kostopravov.polyclinic.dto.PolyclinicDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPolyclinicDepartmentRepository extends JpaRepository<PolyclinicDepartment, Long> {

}
