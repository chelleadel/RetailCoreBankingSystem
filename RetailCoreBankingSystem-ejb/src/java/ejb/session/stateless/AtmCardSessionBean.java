/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import util.exception.AccountDoesNotMatchException;
import util.exception.DeleteException;
import util.exception.EntityExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateException;

/**
 *
 * @author mich
 */
@Stateless
public class AtmCardSessionBean implements AtmCardSessionBeanRemote, AtmCardSessionBeanLocal {

    @EJB
    private DepositAccountSessionBeanLocal depositAccountSessionBean;

    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    
    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public AtmCardSessionBean() {
    }

    @Override
    public Long issueAtmCard(AtmCard newAtmCard, Long cusId, List<Long> accounts) throws EntityExistException, UnknownPersistenceException, AccountDoesNotMatchException {

        try {
            em.persist(newAtmCard);
            Customer customer = customerSessionBean.retrieveCustomerByCustomerId(cusId);
            newAtmCard.setCustomer(customer);
            customer.setAtmCard(newAtmCard);
            
            for (Long accId : accounts) {
                System.out.println("here: " + accId);
                DepositAccount depoAcc = depositAccountSessionBean.retrieveDepositAccountByDepositAccountId(accId);
                
                if(depoAcc.getCustomer().equals(customer)) {
                    newAtmCard.getDepositAccounts().add(depoAcc);
                    depoAcc.setAtmCard(newAtmCard);
                } else {
                    throw new AccountDoesNotMatchException("Deposit Account holder is different from ATM card holder");
                }
            }
            
            em.flush();
            return newAtmCard.getAtmCardId();
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new EntityExistException("ATM Card Number exist");
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        } 

    }

    @Override
    public AtmCard insertAtmCard(String cardNumber, String pin) throws InvalidLoginCredentialException {

        AtmCard atmCard;
        try {
            atmCard = retrieveAtmCardByAtmCardNumber(cardNumber);
        } catch (EntityNotFoundException ex) {
            throw new InvalidLoginCredentialException("ATM Number not found or incorrect PIN number");
        }

        if (atmCard.getPin().equals(pin)) {

            return atmCard;

        }

        throw new InvalidLoginCredentialException("ATM Number not found or incorrect PIN number");

    }

    @Override
    public AtmCard retrieveAtmCardByAtmCardNumber(String findAtmCardNumber) throws EntityNotFoundException {

        Query query = em.createQuery("SELECT a from AtmCard a WHERE a.cardNumber = :findAtmCardNumber");
        query.setParameter("findAtmCardNumber", findAtmCardNumber);

        try {
            return (AtmCard) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EntityNotFoundException("ATM Card Number " + findAtmCardNumber + " does not exist!");
        }

    }

    @Override
    public AtmCard retrieveAtmCardByAtmCardId(Long findAtmId) throws EntityNotFoundException {

        Query query = em.createQuery("SELECT a from AtmCard a WHERE a.atmCardId = :findAtmCardId");
        query.setParameter("findAtmCardId", findAtmId);

        try {
            return (AtmCard) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new EntityNotFoundException("ATM Card ID " + findAtmId + " does not exist!");
        }

    }

    @Override
    public void changePIN(Long atmCardId, String oldPin, String newPin) throws UpdateException {

        AtmCard card = retrieveAtmCardByAtmCardId(atmCardId);
        
        if (card.getPin().equals(oldPin)) {
            card.setPin(newPin);
        } else {
            throw new UpdateException("Update Pin Error: Old Pin Verification Fail.");
        }

    }
    
    @Override
    public void deleteAtmCard(Long atmCardId) throws DeleteException {
        
        try {
            AtmCard atmCard = retrieveAtmCardByAtmCardId(atmCardId);
            atmCard.getCustomer().setAtmCard(null);
            
            for (DepositAccount depoAcc : atmCard.getDepositAccounts()) {
                depoAcc.setAtmCard(null);
            }
         
            em.remove(atmCard);
        } catch (EntityNotFoundException ex) {
            throw new DeleteException("Entity can't be deleted!: " + ex.getMessage());
        }
        
    }

}
