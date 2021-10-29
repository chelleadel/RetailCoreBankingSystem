/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import ejb.session.stateless.DepositAccountTransactionSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import javax.ejb.EJB;

/**
 *
 * @author mich
 */
public class Main {

    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBean;

    @EJB
    private static DepositAccountTransactionSessionBeanRemote depositAccountTransactionSessionBean;

    @EJB
    private static DepositAccountSessionBeanRemote depositAccountSessionBean;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBean;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBean;
    
    

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        MainApp mainApp = new MainApp(depositAccountTransactionSessionBean, depositAccountSessionBean, employeeSessionBean, customerSessionBean, atmCardSessionBean);
        mainApp.runApp();
    }

}
