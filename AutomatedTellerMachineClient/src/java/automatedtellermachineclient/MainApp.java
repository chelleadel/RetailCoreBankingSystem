/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import entity.AtmCard;
import java.util.Scanner;

/**
 *
 * @author mich
 */
public class MainApp {
    
    private AtmCard currentAtmCard;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;

    public MainApp() {
    }

    public MainApp(AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }
    
   
    public void runApp()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Automated Teller Machine ***");
            System.out.println("1: Insert Card");
            System.out.println("2: Exit");
        }
    }
    
    private void doInsertCard() {
        
        Scanner scanner = new Scanner(System.in);
        String cardNumber = "";
        String pin = "";
        
        System.out.println("*** ATM System :: Insert Card ***\n");
        System.out.print("Enter ATM Card Number> ");
        cardNumber = scanner.nextLine().trim();
        System.out.print("Enter PIN> ");
        pin = scanner.nextLine().trim();
        
        currentAtmCard = atmCardSessionBeanRemote.insertAtmCard(cardNumber, pin);
        
    }
    
}
