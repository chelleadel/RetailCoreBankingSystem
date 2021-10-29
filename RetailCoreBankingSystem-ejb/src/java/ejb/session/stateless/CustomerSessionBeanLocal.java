/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import util.exception.DeleteCreditCardException;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Local
public interface CustomerSessionBeanLocal {
    
    public Long createNewCustomer(Customer newCustomer) throws EntityExistException, UnknownPersistenceException;
    public Customer retrieveCustomerByCustomerId(Long customerId) throws EntityNotFoundException;
    public void deleteCreditCardofCustomerbyCustomerId(Long customerId) throws DeleteCreditCardException;
    
}
