/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import entity.AtmCard;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;
import util.enumeration.DepositAccountType;
import util.exception.InvalidLoginCredentialException;
import util.exception.UpdateException;

/**
 *
 * @author mich
 */
public class MainApp {

    private AtmCard currentAtmCard;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;

    public MainApp() {}

    public MainApp(AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Automated Teller Machine ***");
            System.out.println("1: Insert Card");
            System.out.println("2: Exit");

            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doInsertCard();
                        System.out.println("Atm Card Verification Successful!\n");

                        // cashierOperationModule = new CashierOperationModule(productEntitySessionBeanRemote, saleTransactionEntitySessionBeanRemote, checkoutBeanRemote, emailSessionBeanRemote, queueCheckoutNotification, queueCheckoutNotificationFactory, currentStaffEntity);
                        // systemAdministrationModule = new SystemAdministrationModule(staffEntitySessionBeanRemote, productEntitySessionBeanRemote, currentStaffEntity);
                        menuMain();

                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }

    }

    private void doInsertCard() throws InvalidLoginCredentialException {

        Scanner scanner = new Scanner(System.in);
        String cardNumber = "";
        String pin = "";

        System.out.println("*** ATM System :: Insert Card ***\n");
        System.out.print("Enter ATM Card Number> ");
        cardNumber = scanner.nextLine().trim();
        System.out.print("Enter PIN> ");
        pin = scanner.nextLine().trim();

        try {

            this.currentAtmCard = atmCardSessionBeanRemote.insertAtmCard(cardNumber, pin);

        } catch (InvalidLoginCredentialException ex) {

            throw new InvalidLoginCredentialException(ex.getMessage());
        }

    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Automated Teller Machines ***\n");
            System.out.println("1: Enquire Available Balance");
            System.out.println("2: Change PIN Number");
            System.out.println("-----------------------");
            System.out.println("3: Exit\n");

            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doRetrieveAccountBalance();
                } else if (response == 2) {
                    doChangePINNumber();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }

        }
    }

    private void doRetrieveAccountBalance() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Automated Teller Machine :: Enquire Available Balance ***\n");
        
        for (DepositAccount depositAccount: currentAtmCard.getDepositAccounts()) {
            
            if (depositAccount.getAccountType() == DepositAccountType.CURRENT) {
                System.out.println("*** Current Deposit Account ***");
                System.out.println("*** Available Balance > $" + depositAccount.getAvailableBalance());
             
            } else {
                System.out.println("*** Savings Deposit Account ***");
                
                BigDecimal bigDecimal = new BigDecimal(0.00);
                MathContext mc = new MathContext(2);
                
                if (depositAccount.getHoldBalance().compareTo(bigDecimal) == 1) {
                    
                    BigDecimal avail = depositAccount.getLedgerBalance().subtract(depositAccount.getHoldBalance(), mc);
                    System.out.println("*** Available Balance > $" + avail);
                    
                } else {
                    
                    System.out.println("*** Available Balance > $" + depositAccount.getLedgerBalance());
                    
                }
                
            }
            
        }
        

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    private void doChangePINNumber() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Automated Teller Machine :: Change PIN Number ***\n");
        String newPIN;
        String verifyPIN;

        while (true) {
            System.out.print("Enter New PIN > ");
            newPIN = scanner.nextLine().trim();

            System.out.print("Verify New PIN > ");
            verifyPIN = scanner.nextLine().trim();

            if (newPIN.equals(verifyPIN)) {
                
                try {
                    atmCardSessionBeanRemote.changePIN(currentAtmCard);
                } catch (UpdateException ex) {
                    System.out.println(ex.getMessage());
                }
              
            } else {
                System.out.println("PIN Verification Failed: PINs are not the same!");
                System.out.println("Try Again");
            }
            
            break; // for now set it to break. 
        }

        System.out.print("Press any key to continue...> ");
        scanner.nextLine();

    }

    private BigDecimal BigDecimal(double d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
