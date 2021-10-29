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
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import entity.Employee;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.DepositAccountType;
import util.exception.DeleteCreditCardException;
import util.exception.EntityExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author mich
 */
public class MainApp {

    private DepositAccountTransactionSessionBeanRemote depositAccountTransactionSessionBean;

    private DepositAccountSessionBeanRemote depositAccountSessionBean;

    private EmployeeSessionBeanRemote employeeSessionBean;

    private CustomerSessionBeanRemote customerSessionBean;

    private AtmCardSessionBeanRemote atmCardSessionBean;

    private Employee currentEmployee;

    private Customer currentCustomer;

    public MainApp() {
    }

    public MainApp(DepositAccountTransactionSessionBeanRemote depositAccountTransactionSessionBean, DepositAccountSessionBeanRemote depositAccountSessionBean, EmployeeSessionBeanRemote employeeSessionBean, CustomerSessionBeanRemote customerSessionBean, AtmCardSessionBeanRemote atmSessionBean) {
        this.depositAccountTransactionSessionBean = depositAccountTransactionSessionBean;
        this.depositAccountSessionBean = depositAccountSessionBean;
        this.employeeSessionBean = employeeSessionBean;
        this.customerSessionBean = customerSessionBean;
        this.atmCardSessionBean = atmSessionBean;
    }

    public void runApp() {

        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {

            System.out.println("*** Welcome to Teller Terminal ***");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");

            response = 0;
            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {

                    try {
                        doLogin();
                        subMenu();

                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Invalid Login Credentials: " + ex.getMessage() + "\n");

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

    private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";

        System.out.println("*** POS System :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
            currentEmployee = employeeSessionBean.employeeLogin(username, password);
        } else {
            throw new InvalidLoginCredentialException("Missing login credential!");
        }
    }

    private void subMenu() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {

            System.out.println("*** Welcome to Teller Terminal ***");
            System.out.println("1: Create Customer");
            System.out.println("2: Open Deposit Account");
            System.out.println("3: Issue ATM Card");
            System.out.println("4: Exit\n");

            response = 0;
            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doCreateCustomer();

                } else if (response == 2) {

                    doOpenDepositAccount();

                } else if (response == 3) {
                    doIssueAtmCard();

                } else if (response == 4) {
                    break;

                } else {
                    System.out.println("Invalid option, please try again\n");
                }
            }

            if (response == 5) {
                break;
            }
        }

    }

    private void doCreateCustomer() {
        Scanner scanner = new Scanner(System.in);
        Customer newCustomer = new Customer();

        System.out.println("*** Teller Terminal :: Create New Customer ***\n");
        System.out.print("Enter First Name> ");
        newCustomer.setFirstName(scanner.nextLine().trim());
        System.out.print("Enter Last Name> ");
        newCustomer.setLastName(scanner.nextLine().trim());
        System.out.print("Enter Identification Number> ");
        newCustomer.setIdentificationNumber(scanner.nextLine().trim());
        System.out.print("Enter Contact Number> ");
        newCustomer.setContactNumber(scanner.nextLine().trim());
        System.out.print("Enter Address Line 1> ");
        newCustomer.setAdressLine1(scanner.nextLine().trim());
        System.out.print("Enter Address Line 2> ");
        newCustomer.setAddressLine2(scanner.nextLine().trim());
        System.out.print("Enter Postal Code> ");
        newCustomer.setPostalCode(scanner.nextLine().trim());

        try {
            Long newCustomerId = customerSessionBean.createNewCustomer(newCustomer);
            currentCustomer = customerSessionBean.retrieveCustomerByCustomerId(newCustomerId);
            System.out.println("New customer created successfully!: " + newCustomerId + "\n");

        } catch (EntityExistException ex) {
            System.out.println("An error has occurred while creating the new customer!: The identification number already exist\n");
        } catch (UnknownPersistenceException ex) {
            System.out.println("An unknown error has occurred while creating the new customer!: " + ex.getMessage() + "\n");
        }

    }

    private void doOpenDepositAccount() {

        try {
            Scanner scanner = new Scanner(System.in);
            DepositAccount depositAccount = new DepositAccount();

            System.out.println("*** Teller Terminal :: Create New Saving Deposits ***");
            System.out.println("*** Select Existing Customer ***");
            System.out.print("Enter Customer Id> ");
            Long cusId = scanner.nextLong();
            scanner.nextLine();
            currentCustomer = customerSessionBean.retrieveCustomerByCustomerId(cusId);
            System.out.println("*** Existing Customer Found! ***");

            System.out.println("*** Teller Terminal :: Create New Savings Deposit Account For " + currentCustomer.getFirstName() + "***\n");

            System.out.println("Enter Account Number> ");
            depositAccount.setAccountNumber(scanner.nextLine().trim());
            depositAccount.setAccountType(DepositAccountType.SAVINGS);
            System.out.println("Enter Cash Deposit Amount> ");
            BigDecimal amount = scanner.nextBigDecimal();
            depositAccount.setAvailableBalance(amount);
            depositAccount.setHoldBalance(amount);
            depositAccount.setLedgerBalance(amount);
            depositAccount.setEnabled(true);

            depositAccountSessionBean.createDepositAccount(depositAccount, currentCustomer.getCustomerId());
            System.out.println("Deposit Account " + depositAccount.getDepositAccountId() + " is successfully created!");

        } catch (EntityExistException ex) {

            System.out.println("An error has occurred while creating the new deposit account!: " + ex.getMessage() + "\n");

        } catch (UnknownPersistenceException ex) {

            System.out.println("An unknown error has occurred while creating the new customer!: " + ex.getMessage() + "\n");

        }

    }

    private void doIssueAtmCard() {

        Scanner scanner = new Scanner(System.in);
        boolean canIssueNewCard = false;

        System.out.println("*** Teller Terminal :: Issue New ATM Card ***\n");
        System.out.println("*** Select Existing Customer ***");
        System.out.print("Enter Customer Id> ");
        Long cusId = scanner.nextLong();
        currentCustomer = customerSessionBean.retrieveCustomerByCustomerId(cusId);
        System.out.println("*** Existing Customer Found! ***");

        if (currentCustomer.getAtmCard() != null) {
            System.out.println("Customers are not allowed to own more than 1 ATM Card. Would you like to replace your ATM Card?(Y/N) ");
            String answer = scanner.nextLine().trim();

            if (answer.equals("Y")) {
                try {
                    customerSessionBean.deleteCreditCardofCustomerbyCustomerId(cusId);
                    System.out.println("Old ATM Card is successfully deleted!");
                } catch (DeleteCreditCardException ex) {
                    System.out.println("There is an error when deleting your ATM Card: " + ex.getMessage());
                }
            } else {
                System.out.println("Old ATM Card not deleted. New Atm Card cannot be issued.");

            }

        } else {

            canIssueNewCard = true;

        }

        AtmCard newAtmCard = new AtmCard();
        if (canIssueNewCard) {

            try {
                System.out.println("*** Teller Terminal :: Issue New ATM Card For " + currentCustomer.getFirstName() + "***\n");
                System.out.print("Enter Card Number> ");
                newAtmCard.setCardNumber(scanner.nextLine().trim());
                System.out.print("Enter Card Number> ");
                newAtmCard.setCardNumber(scanner.nextLine().trim());
                System.out.print("Enter Name on ATM Card> ");
                newAtmCard.setCardNumber(scanner.nextLine().trim());
                newAtmCard.setEnabled(true);
                System.out.print("Enter PIN on ATM Card> ");
                newAtmCard.setPin(scanner.nextLine().trim());

                //Linking deposit accounts to card
                List<Long> accounts = new ArrayList<>();
                System.out.println("Finding existing deposit accounts to link to card");
                for (DepositAccount account : currentCustomer.getDepositAccounts()) {
                    System.out.print("Do you want to link " + account.getAccountNumber() + "? (Enter Y to link)> ");
                    String reply = scanner.nextLine().trim();
                    if (reply.equals("Y")) {
                        accounts.add(account.getDepositAccountId());
                    }
                }

                atmCardSessionBean.issueAtmCard(newAtmCard, currentCustomer.getCustomerId(), accounts);
                System.out.println("ATM Card with ATM Card ID" + newAtmCard.getAtmCardId() + " is successfully created!");
            } catch (EntityExistException ex) {

                System.out.println("There is an error issuing a new ATM Card: " + ex.getMessage());
            } catch (UnknownPersistenceException ex) {

                System.out.println("An unknown error has occurred while creating the new customer!: " + ex.getMessage() + "\n");
            }
        }

    }

}
