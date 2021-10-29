/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccountTransaction;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Stateless
public class DepositAccountTransactionSessionBean implements DepositAccountTransactionSessionBeanRemote, DepositAccountTransactionSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public DepositAccountTransactionSessionBean() {
    }

    public Long createDepositAccountTransaction(DepositAccountTransaction newDepositAccountTransaction) throws EntityExistException, UnknownPersistenceException {
        try {
            em.persist(newDepositAccountTransaction);
            em.flush();
            return newDepositAccountTransaction.getDepositAccountTransactionId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new EntityExistException("Deposit Account Transaction exist! Have same code or reference.");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
}
