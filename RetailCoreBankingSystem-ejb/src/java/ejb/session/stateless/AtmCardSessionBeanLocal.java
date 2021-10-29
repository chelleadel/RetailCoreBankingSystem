/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
import util.exception.EntityExistException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateException;

/**
 *
 * @author mich
 */
@Local
public interface AtmCardSessionBeanLocal {
    
    public Long issueAtmCard(AtmCard newAtmCard) throws EntityExistException, UnknownPersistenceException;
    public AtmCard insertAtmCard(String cardNumber, String pin) throws InvalidLoginCredentialException;
    public AtmCard retrieveAtmCardByAtmCardNumber(String findAtmCardNumber) throws EntityNotFoundException;
    public AtmCard retrieveAtmCardByAtmCardId(Long findAtmId) throws EntityNotFoundException;
    public void changePIN(AtmCard newAtmCard) throws UpdateException;
    
}
