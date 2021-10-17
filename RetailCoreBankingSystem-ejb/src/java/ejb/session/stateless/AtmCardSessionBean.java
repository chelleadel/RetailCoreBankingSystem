/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    public Long issueAtmCard(AtmCard newAtmCard) {
        em.persist(newAtmCard);
        em.flush();
        return newAtmCard.getAtmCardId();
    }
    
    public AtmCard insertAtmCard(String cardNumber, String pin) {
        
        AtmCard atmCard = retrieveAtmCardByAtmCardNumber(cardNumber);
        
        if(atmCard.getPin().equals(pin)) {
            
            return atmCard;
            
        }
        
        return null;
        
    }
    
    public AtmCard retrieveAtmCardByAtmCardNumber(String findAtmCardNumber) {
        
        Query query = em.createQuery("SELECT a from AtmCard a WHERE a.cardNumber = :findAtmCardNumber");
        query.setParameter("findAtmCardId", findAtmCardNumber);
        
        try
        {
            return (AtmCard)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            // throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
        return null;
    }
    
    public AtmCard retrieveAtmCardByAtmCardId(Long findAtmId) {
        
        Query query = em.createQuery("SELECT a from AtmCard a WHERE a.atmCardId = :findAtmCardId");
        query.setParameter("findAtmCardId", findAtmId);
        
        try
        {
            return (AtmCard)query.getSingleResult();
        }
        catch(NoResultException | NonUniqueResultException ex)
        {
            // throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
        return null;
    }
    
    public void changePIN(AtmCard newAtmCard) {
        
        if(newAtmCard != null && newAtmCard.getAtmCardId() != null) {
            
            AtmCard atmCardToUpdate = retrieveAtmCardByAtmCardId(newAtmCard.getAtmCardId());
            
            if (atmCardToUpdate != null) {
                
                atmCardToUpdate.setPin(newAtmCard.getPin());
                
            }
            
        }
        
    }

}
