/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import ejb.session.stateless.DepositAccountTransactionSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.DepositAccountType;
import util.exception.EntityExistException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
@Singleton
@LocalBean
@Startup

public class DataInitSessionBean {

    @EJB
    private EmployeeSessionBeanRemote employeeSessionBean;

    @EJB
    private DepositAccountTransactionSessionBeanRemote depositAccountTransactionSessionBean;

    @EJB
    private DepositAccountSessionBeanRemote depositAccountSessionBean;

    @EJB
    private CustomerSessionBeanRemote customerSessionBean;

    @EJB
    private AtmCardSessionBeanRemote atmCardSessionBean;
    
    
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PostConstruct
    public void postConstruct() {
        // initializeData();
    }
    
    private void initializeData() {
        
        
        try {
            
            Customer cust1 = new Customer("mich", "adel", "1", "12345678", "rc4", "nus", "138614");
            AtmCard atm1 = new AtmCard("1234567812345678", "mich", true, "123456");
            DepositAccount depoAcc1 = new DepositAccount("12345678", DepositAccountType.CURRENT, new BigDecimal(100.00),new BigDecimal(100.00), new BigDecimal(50.00), true);
            DepositAccount depoAcc2 = new DepositAccount("87654321", DepositAccountType.CURRENT, new BigDecimal(100.00), new BigDecimal(100.00), new BigDecimal(50.00), true);
           
            
            cust1.setAtmCard(atm1);
            cust1.getDepositAccounts().add(depoAcc1);
            cust1.getDepositAccounts().add(depoAcc2);
            
            atm1.setCustomer(cust1);
            atm1.getDepositAccounts().add(depoAcc1);
            atm1.getDepositAccounts().add(depoAcc2);
            
            depoAcc1.setAtmCard(atm1);
            depoAcc2.setAtmCard(atm1);
            
            depoAcc1.setAtmCard(atm1);
            depoAcc2.setAtmCard(atm1);
            
            Long custid1 = customerSessionBean.createNewCustomer(cust1);
            Long atm1id = atmCardSessionBean.issueAtmCard(atm1);
            Long depoAccId1 = depositAccountSessionBean.createDepositAccount(depoAcc1);
            Long depoAccId2 = depositAccountSessionBean.createDepositAccount(depoAcc2);
            
        } catch (EntityExistException | UnknownPersistenceException ex) {
            
            System.out.println(ex.getMessage());
            
        }
        
        
    }
}
