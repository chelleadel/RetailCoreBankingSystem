/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccountTransaction;
import javax.ejb.Local;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Local
public interface DepositAccountTransactionSessionBeanLocal {
    
    public Long createDepositAccountTransaction(DepositAccountTransaction newDepositAccountTransaction) throws EntityExistException, UnknownPersistenceException ;
    
}
