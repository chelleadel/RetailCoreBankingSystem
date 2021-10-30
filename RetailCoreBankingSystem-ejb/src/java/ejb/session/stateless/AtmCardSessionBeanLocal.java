/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityNotFoundException;
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
@Local
public interface AtmCardSessionBeanLocal {
    
    public Long issueAtmCard(AtmCard newAtmCard, Long cusId, List<Long> accounts) throws EntityExistException, UnknownPersistenceException, AccountDoesNotMatchException;
    public AtmCard insertAtmCard(String cardNumber, String pin) throws InvalidLoginCredentialException;
    public AtmCard retrieveAtmCardByAtmCardNumber(String findAtmCardNumber) throws EntityNotFoundException;
    public AtmCard retrieveAtmCardByAtmCardId(Long findAtmId) throws EntityNotFoundException;
    public void changePIN(Long atmId, String oldPin, String newPin) throws UpdateException;
    public void deleteAtmCard(Long atmCardId) throws DeleteException;
}
