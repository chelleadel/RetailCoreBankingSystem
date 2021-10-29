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
public class EntityExistException extends Exception {

    public EntityExistException(String string) {
        super(string);
    }

    public EntityExistException() {
    }
    
    
}
