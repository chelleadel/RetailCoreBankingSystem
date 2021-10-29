/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.EntityExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Stateless
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Long createNewEmployee(Employee newEmployee) throws EntityExistException, UnknownPersistenceException {
        try {
            em.persist(newEmployee);
            em.flush();
            return newEmployee.getEmployeeId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new EntityExistException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
      
    public List<Employee> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT e FROM Employee e");

        return query.getResultList();
    }

    public Employee retrieveEmployeeByEmployeeId(Long employeeId) throws EntityNotFoundException {
        Employee employee = em.find(Employee.class, employeeId);

        if (employee != null) {
            return employee;
        } else {
            throw new EntityNotFoundException("Employee ID " + employeeId + " does not exist!");
        }
    }

    public Employee retrieveEmployeeByUsername(String username) throws EntityNotFoundException {
        
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EntityNotFoundException("Employee Username " + username + " does not exist!");
        }
    }
    
    @Override
    public Employee employeeLogin(String username, String password) throws InvalidLoginCredentialException
    {
        try
        {
            Employee employee = retrieveEmployeeByUsername(username);
            
            if(employee.getPassword().equals(password))
            {
                              
                return employee;
            }
            else
            {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EntityNotFoundException ex)
        {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

}
