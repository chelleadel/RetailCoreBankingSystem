/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public Long createNewCustomer(Customer newCustomer) {
        em.persist(newCustomer);
        em.flush();
        return newCustomer.getCustomerId();
    }
    
    public Customer retrieveCustomerByCustomerId(Long customerId) {
        
        Query query = em.createQuery("SELECT c from Customer c WHERE c.customerId = :findCustomerId");
        query.setParameter("findCustomerId", customerId);
        
        try
        {
            return (Customer)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            // throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
        return null;
    }
    
    
}
