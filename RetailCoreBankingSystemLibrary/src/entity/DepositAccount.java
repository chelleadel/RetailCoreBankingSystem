/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.enumeration.DepositAccountType;

/**
 *
 * @author mich
 */
@Entity
public class DepositAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountId;
    
    @NotNull
    @Column(unique= true, nullable = false)
    @Size(min=8, max=8)
    private String accountNumber;
    
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositAccountType accountType;
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal availableBalance; 
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal holdBalance;
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal ledgerBalance;
    
    @NotNull
    @Column(nullable = false)
    private Boolean enabled; 
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer; 
    
    @ManyToOne
    private AtmCard atmCard;
    
    @OneToMany(mappedBy ="depositAccount", fetch = FetchType.EAGER)
    private List<DepositAccountTransaction> transactions;

    public DepositAccount() {
        this.transactions = new ArrayList<>();
    }

    public DepositAccount(String accountNumber, DepositAccountType accountType, BigDecimal availableBalance, BigDecimal holdBalance, BigDecimal ledgerBalance, Boolean enabled) {
        this();
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.availableBalance = availableBalance;
        this.holdBalance = holdBalance;
        this.ledgerBalance = ledgerBalance;
        this.enabled = enabled;
    }

    public Long getDepositAccountId() {
        return depositAccountId;
    }

    public void setDepositAccountId(Long depositAccountId) {
        this.depositAccountId = depositAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountId != null ? depositAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountId fields are not set
        if (!(object instanceof DepositAccount)) {
            return false;
        }
        DepositAccount other = (DepositAccount) object;
        if ((this.depositAccountId == null && other.depositAccountId != null) || (this.depositAccountId != null && !this.depositAccountId.equals(other.depositAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccount[ id=" + depositAccountId + " ]";
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the availableBalance
     */
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    /**
     * @param availableBalance the availableBalance to set
     */
    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * @return the holdBalance
     */
    public BigDecimal getHoldBalance() {
        return holdBalance;
    }

    /**
     * @param holdBalance the holdBalance to set
     */
    public void setHoldBalance(BigDecimal holdBalance) {
        this.holdBalance = holdBalance;
    }

    /**
     * @return the ledgerBalance
     */
    public BigDecimal getLedgerBalance() {
        return ledgerBalance;
    }

    /**
     * @param ledgerBalance the ledgerBalance to set
     */
    public void setLedgerBalance(BigDecimal ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the accountType
     */
    public DepositAccountType getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(DepositAccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
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
     * @return the transactions
     */
    public List<DepositAccountTransaction> getTransactions() {
        return transactions;
    }

    /**
     * @param transactions the transactions to set
     */
    public void setTransactions(List<DepositAccountTransaction> transactions) {
        this.transactions = transactions;
    }
    
}
