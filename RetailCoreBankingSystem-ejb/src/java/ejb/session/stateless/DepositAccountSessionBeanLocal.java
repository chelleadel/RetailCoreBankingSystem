/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Local
public interface DepositAccountSessionBeanLocal {
    
    public Long createDepositAccount(DepositAccount newDepositAccount) throws EntityExistException, UnknownPersistenceException;
    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depoAccId) throws EntityNotFoundException;
}
