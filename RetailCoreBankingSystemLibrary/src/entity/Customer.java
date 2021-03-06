/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mich
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    
    @NotNull
    @Size(min=1, max=35)
    @Column(nullable = false)
    private String firstName;
    
    @NotNull
    @Size(min=1, max=35)
    @Column(nullable = false)
    private String lastName;
    
    @NotNull
    @Size(min=1, max=35)
    @Column(unique = true, nullable = false)
    private String identificationNumber;
    
    @NotNull
    @Size(min=1, max=8)
    @Column(unique = true, nullable = false)
    private String contactNumber; 
    
    @NotNull
    @Size(min=1, max=250)
    @Column(nullable = false)
    private String adressLine1;
    
    @NotNull
    @Size(min=1, max=250)
    @Column(nullable = false)
    private String addressLine2;
    
    @NotNull
    @Size(min=6, max= 6)
    @Column(nullable = false)
    private String postalCode;
    
    @OneToOne
    @JoinColumn
    private AtmCard atmCard;
    
    @OneToMany(mappedBy ="customer", fetch = FetchType.EAGER)
    private List<DepositAccount> depositAccounts;

    public Customer() {
        this.depositAccounts = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String identificationNumber, String contactNumber, String adressLine1, String addressLines2, String postalCode) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.contactNumber = contactNumber;
        this.adressLine1 = adressLine1;
        this.addressLine2 = addressLines2;
        this.postalCode = postalCode;
    }
    
    

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }
    
     /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the identificationNumber
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * @param identificationNumber the identificationNumber to set
     */
    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    /**
     * @return the contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber the contactNumber to set
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return the adressLine1
     */
    public String getAdressLine1() {
        return adressLine1;
    }

    /**
     * @param adressLine1 the adressLine1 to set
     */
    public void setAdressLine1(String adressLine1) {
        this.adressLine1 = adressLine1;
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2 the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the atmCard
     */
    public AtmCard getAtmCard() {
        return atmCard;
    }

    /**
     * @param atmCard the atmCard to set
     */
    public void setAtmCard(AtmCard atmCard) {
        this.atmCard = atmCard;
    }

    /**
     * @return the depositAccounts
     */
    public List<DepositAccount> getDepositAccounts() {
        return depositAccounts;
    }

    /**
     * @param depositAccounts the depositAccounts to set
     */
    public void setDepositAccounts(List<DepositAccount> depositAccounts) {
        this.depositAccounts = depositAccounts;
    }
    
}
