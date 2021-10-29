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
import entity.Employee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.DepositAccountType;
import util.enumeration.EmployeeAccessRightEnum;
import util.exception.AccountDoesNotMatchException;
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
        initializeData();
    }

    private void initializeData() {

        Employee em1 = new Employee("mich", "adel", "mich", "password", EmployeeAccessRightEnum.TELLER);
        try {
            employeeSessionBean.createNewEmployee(em1);
            System.out.println("Employee created!");

            Customer cust1 = new Customer("mich", "adel", "1", "12345678", "rc4", "nus", "138614");
            

            AtmCard atm1 = new AtmCard("1234567812345678", "mich", true, "123456");
            

            DepositAccount depoAcc1 = new DepositAccount("12345678", DepositAccountType.CURRENT, new BigDecimal(100.00), new BigDecimal(100.00), new BigDecimal(50.00), true);
            DepositAccount depoAcc2 = new DepositAccount("87654321", DepositAccountType.CURRENT, new BigDecimal(100.00), new BigDecimal(100.00), new BigDecimal(50.00), true);

            Long custid1 = customerSessionBean.createNewCustomer(cust1);
            System.out.println("Customer created!");
            
            Long depoAccId1 = depositAccountSessionBean.createDepositAccount(depoAcc1, custid1);
            Long depoAccId2 = depositAccountSessionBean.createDepositAccount(depoAcc2, custid1);
            List<Long> depoAccounts = new ArrayList<>();
            depoAccounts.add(depoAccId1);
            depoAccounts.add(depoAccId2);
            System.out.println("Depo created!");
            
            Long atm1id = atmCardSessionBean.issueAtmCard(atm1, custid1, depoAccounts);
            System.out.println("AtmCard created!");
            
        } catch (EntityExistException ex) {
            System.out.println("There is an error: Entity Exist! " + ex.getMessage());
        } catch (UnknownPersistenceException ex) {
            System.out.println("There is an error: Unknown Persistence Error! " + ex.getMessage());
        } catch (AccountDoesNotMatchException ex) {
            System.out.println("There is an error: " + ex.getMessage());
        }

    }

}
