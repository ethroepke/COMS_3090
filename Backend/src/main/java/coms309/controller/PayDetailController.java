package coms309.controller;

import coms309.entity.Salary;
import coms309.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/salary")
public class PayDetailsController {
    @Autowired
    private final SalaryRepository salaryRepository;

    public PayDetailsController(SalaryRepository salaryRepository){
        this.salaryRepository= salaryRepository;
    }
    @GetMapping("/all")
    public List<Salary> getAllSalary(){return salaryRepository.findAll();}

    @GetMapping("/username")


}
