/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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

    @PersistenceContext(unitName = "RetailCoreBankingSystem-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public AtmCardSessionBean() {
    }

    @Override
    public Long issueAtmCard(AtmCard newAtmCard) throws EntityExistException, UnknownPersistenceException {

        try {
            em.persist(newAtmCard);
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
        query.setParameter("findAtmCardId", findAtmCardNumber);

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
    public void changePIN(AtmCard newAtmCard) throws UpdateException {

        if (newAtmCard != null && newAtmCard.getAtmCardId() != null) {

            AtmCard atmCardToUpdate;
            try {
                atmCardToUpdate = retrieveAtmCardByAtmCardId(newAtmCard.getAtmCardId());
            } catch (EntityNotFoundException ex) {
                throw new UpdateException("ATM Card does not exist");
            }

            if (atmCardToUpdate != null) {

                atmCardToUpdate.setPin(newAtmCard.getPin());

            } else {
                throw new UpdateException("Update Card Error");
            }

        }

    }

}
