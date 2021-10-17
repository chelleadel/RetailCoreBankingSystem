/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DepositAccountTransaction;
import javax.ejb.Remote;

/**
 *
 * @author mich
 */
@Remote
public interface DepositAccountTransactionSessionBeanRemote {
    
    public Long createDepositAccountTransaction(DepositAccountTransaction newDepositAccountTransaction);
    
}
