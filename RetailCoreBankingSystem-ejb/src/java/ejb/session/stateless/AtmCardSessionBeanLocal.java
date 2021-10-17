/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AtmCard;
import javax.ejb.Local;

/**
 *
 * @author mich
 */
@Local
public interface AtmCardSessionBeanLocal {
    
    public Long issueAtmCard(AtmCard newAtmCard);
    public AtmCard insertAtmCard(String cardNumber, String pin);
    public AtmCard retrieveAtmCardByAtmCardNumber(String findAtmCardNumber);
    public AtmCard retrieveAtmCardByAtmCardId(Long findAtmId);
    public void changePIN(AtmCard newAtmCard);
    
}
