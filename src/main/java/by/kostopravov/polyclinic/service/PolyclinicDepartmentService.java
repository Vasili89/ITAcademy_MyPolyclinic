package by.kostopravov.polyclinic.service;

import by.kostopravov.polyclinic.dto.PolyclinicDepartment;
import by.kostopravov.polyclinic.repository.IPolyclinicDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PolyclinicDepartmentService {

    private IPolyclinicDepartmentRepository polyclinicDepartmentRepository;

    @Autowired
    public PolyclinicDepartmentService(IPolyclinicDepartmentRepository polyclinicDepartmentRepository) {
        this.polyclinicDepartmentRepository = polyclinicDepartmentRepository;
    }

    public ArrayList<PolyclinicDepartment> getAllDepartments() {
        return (ArrayList<PolyclinicDepartment>) polyclinicDepartmentRepository.findAll();
    }

    public PolyclinicDepartment findDepartmentById(Long id) {
        return polyclinicDepartmentRepository.findById(id).orElse(null);
    }

    public void createNewDepartment(PolyclinicDepartment department) {
        polyclinicDepartmentRepository.save(department);
    }

}
