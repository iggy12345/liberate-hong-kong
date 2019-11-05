package company.Entity.Controller;

import company.Entity.Abstract.AVault;
import company.Entity.Interface.IEmployee;
import company.Entity.Interface.IEmployeeController;

import java.util.UUID;

import company.Entity.Person;
import company.exceptions.EmployeeNotFoundException;

public class EmployeeController implements IEmployeeController {
    private AVault vault;

    public EmployeeController(AVault vault) {
        this.vault = vault;
    }

    @Override
    public UUID createEmployee(String first_name, String last_name, String position) throws Exception {
        Person p = new Person(first_name, last_name);

        IEmployee employee = vault.getEmployee(p);
        if (employee != null)
            throw new Exception("Person is already an employee");

        UUID employee_id = null;
        switch (position) {
            case "Teller":
                employee_id = vault.createTeller(p);
                break;
            case "Loan Officer":
                employee_id = vault.createLoanOfficer(p);
                break;
            case "Manager":
                employee_id = vault.createManager(p);
                break;
            case "HR Manager":
                employee_id = vault.createHRManager(p);
                break;
            case "Owner":
                employee_id = vault.createOwner(p);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }

        return employee_id;
    }

    public UUID findEmployee(String username) throws EmployeeNotFoundException {
        IEmployee employee = vault.getEmployee(username);
        if(employee == null)
            throw new EmployeeNotFoundException("Employee not found", username);
        return employee.getEmployeeID();
    }

    public IEmployee findEmployee(UUID user_id) throws EmployeeNotFoundException {
        IEmployee employee = vault.getEmployee(user_id);
        if(employee == null)
            throw new EmployeeNotFoundException("Employee not found", user_id);
        return employee;
    }

    public UUID fireEmployee(UUID employee_id) throws EmployeeNotFoundException {
        IEmployee employee = vault.getEmployee(employee_id);
        if (employee == null)
            throw new EmployeeNotFoundException("Employee not found", employee_id);
        return vault.fireEmployee(employee_id);
    }

    public UUID modifyEmployeePassword(UUID employee_id, String password) throws EmployeeNotFoundException
    {
        IEmployee employee = vault.getEmployee(employee_id);
        if(employee == null)
            throw new EmployeeNotFoundException("Employee not found", employee_id);
        employee.setEmployeePassword(password);
        return employee_id;
    }
}
