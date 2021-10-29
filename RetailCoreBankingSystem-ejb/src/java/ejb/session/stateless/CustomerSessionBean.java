/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.DeleteCreditCardException;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public CustomerSessionBean() {
    }

    @Override
    public Long createNewCustomer(Customer newCustomer) throws EntityExistException, UnknownPersistenceException {
        try {
            em.persist(newCustomer);
            em.flush();
            return newCustomer.getCustomerId();
            
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new EntityExistException("Customer Identification Number Exist");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public Customer retrieveCustomerByCustomerId(Long customerId) throws EntityNotFoundException {

        Query query = em.createQuery("SELECT c from Customer c WHERE c.customerId = :findCustomerId");
        query.setParameter("findCustomerId", customerId);
        try {
            return (Customer) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EntityNotFoundException("Customer Id " + customerId + " does not exist!");
        }

    }

    @Override
    public void deleteCreditCardofCustomerbyCustomerId(Long customerId) throws DeleteCreditCardException {

        Customer currentCustomer = retrieveCustomerByCustomerId(customerId);
        currentCustomer.setAtmCard(null);

    }

}
