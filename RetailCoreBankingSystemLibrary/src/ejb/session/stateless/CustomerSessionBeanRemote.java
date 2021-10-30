/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import javax.ejb.Remote;
import javax.persistence.EntityNotFoundException;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Remote
public interface CustomerSessionBeanRemote {
    
    public Long createNewCustomer(Customer newCustomer) throws EntityExistException, UnknownPersistenceException;
    public Customer retrieveCustomerByCustomerId(Long customerId) throws EntityNotFoundException;
    
}
