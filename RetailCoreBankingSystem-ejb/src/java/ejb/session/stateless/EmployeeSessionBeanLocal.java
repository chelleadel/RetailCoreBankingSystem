/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import util.exception.EntityExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Local
public interface EmployeeSessionBeanLocal {
    
    public Long createNewEmployee(Employee newEmployee) throws EntityExistException, UnknownPersistenceException;
    public List<Employee> retrieveAllEmployees();
    public Employee retrieveEmployeeByEmployeeId(Long employeeId) throws EntityNotFoundException;
    public Employee retrieveEmployeeByUsername(String username) throws EntityNotFoundException;
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException;
    
        
    
}
