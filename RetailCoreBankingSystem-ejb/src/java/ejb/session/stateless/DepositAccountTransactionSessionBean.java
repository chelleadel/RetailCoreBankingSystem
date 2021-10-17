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
    
    public Long createDepositAccountTransaction(DepositAccountTransaction newDepositAccountTransaction) {
        em.persist(newDepositAccountTransaction);
        em.flush();
        return newDepositAccountTransaction.getDepositAccountTransactionId();
    }
}
