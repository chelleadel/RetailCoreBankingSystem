/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import util.enumeration.TransactionStatus;
import util.enumeration.TransactionType;

/**
 *
 * @author mich
 */
@Entity
public class DepositAccountTransaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depositAccountTransactionId;
    
    @NotNull
    @Column(nullable = false)
    private Date transactionDateTime;
    
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    @NotNull
    @Column(unique= true, nullable = false)
    private String code; 
    
    @NotNull
    @Column(unique= true, nullable = false)
    private String reference; 
    
    @NotNull
    @Column(nullable = false)
    private BigDecimal amount;
    
    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private DepositAccount depositAccount;
    
    @OneToOne
    private DepositAccountTransaction sourceTransaction;
    
    @OneToOne
    private DepositAccountTransaction destinationTransaction;

    public DepositAccountTransaction() {
        
    }

    public DepositAccountTransaction(Date transactionDateTime, TransactionType type, String code, String reference, BigDecimal amount, TransactionStatus status) {
        this.transactionDateTime = transactionDateTime;
        this.type = type;
        this.code = code;
        this.reference = reference;
        this.amount = amount;
        this.status = status;
    }
    
    

    public Long getDepositAccountTransactionId() {
        return depositAccountTransactionId;
    }

    public void setDepositAccountTransactionId(Long depositAccountTransactionId) {
        this.depositAccountTransactionId = depositAccountTransactionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (depositAccountTransactionId != null ? depositAccountTransactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the depositAccountTransactionId fields are not set
        if (!(object instanceof DepositAccountTransaction)) {
            return false;
        }
        DepositAccountTransaction other = (DepositAccountTransaction) object;
        if ((this.depositAccountTransactionId == null && other.depositAccountTransactionId != null) || (this.depositAccountTransactionId != null && !this.depositAccountTransactionId.equals(other.depositAccountTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DepositAccountTransaction[ id=" + depositAccountTransactionId + " ]";
    }

    /**
     * @return the type
     */
    public TransactionType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TransactionType type) {
        this.type = type;
    }

    /**
     * @return the status
     */
    public TransactionStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    /**
     * @return the sourceTransaction
     */
    public DepositAccountTransaction getSourceTransaction() {
        return sourceTransaction;
    }

    /**
     * @param sourceTransaction the sourceTransaction to set
     */
    public void setSourceTransaction(DepositAccountTransaction sourceTransaction) {
        this.sourceTransaction = sourceTransaction;
    }

    /**
     * @return the destinationTransaction
     */
    public DepositAccountTransaction getDestinationTransaction() {
        return destinationTransaction;
    }

    /**
     * @param destinationTransaction the destinationTransaction to set
     */
    public void setDestinationTransaction(DepositAccountTransaction destinationTransaction) {
        this.destinationTransaction = destinationTransaction;
    }
    
}
