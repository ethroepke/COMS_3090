//package coms309;
//
//import coms309.entity.Employee;
//import coms309.entity.EmployeeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//class EmployeeController {
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//
//    @PostMapping("/user")
//    public String createStudent(@RequestBody Employee newEmployee){
//        // lam object studnet moi voi thong tin cua newstudent, save vao db
//        employeeRepo.save(newEmployee);
//        return "new student "+ newEmployee.getName() +"has been added";
//    }
//}
