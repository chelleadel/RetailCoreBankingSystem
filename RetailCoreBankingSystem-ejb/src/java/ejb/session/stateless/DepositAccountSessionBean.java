/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccount;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Stateless
public class DepositAccountSessionBean implements DepositAccountSessionBeanRemote, DepositAccountSessionBeanLocal {

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public DepositAccountSessionBean() {
    }

    public Long createDepositAccount(DepositAccount newDepositAccount) throws EntityExistException, UnknownPersistenceException {
        try {
            em.persist(newDepositAccount);
            em.flush();
            return newDepositAccount.getDepositAccountId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new EntityExistException("Deposit Account Number exist!");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }
    
    public DepositAccount retrieveDepositAccountByDepositAccountId(Long depoAccId) throws EntityNotFoundException {

        Query query = em.createQuery("SELECT c from DepositAccount c WHERE c.depoAccId = :findDepositAccountId");
        query.setParameter("findDepositAccountId", depoAccId);

        try {
            return (DepositAccount) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EntityNotFoundException("Deposit Account Id " + depoAccId + " does not exist!");
        }
       
    }

}
