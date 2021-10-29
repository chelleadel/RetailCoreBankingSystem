/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author mich
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String string) {
        super(string);
    }
    
}
