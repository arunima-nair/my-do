package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the COLLECTIONS database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COLLECTIONS")
@NamedQuery(name = "Collection.findAll", query = "SELECT c FROM Collection c")
public class Collection extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "COLLECTIONS_COLLECTIONID_GENERATOR", sequenceName = "SEQ_COLLECTIONS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COLLECTIONS_COLLECTIONID_GENERATOR")
	@Column(name = "COLLECTION_ID")
	private Long collectionId;

	private String amount;

	@Column(name = "COLLECTION_TYPE")
	private String collectionType;

	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name = "RECEIPT_NO")
	private String receiptNo;

	private String status;

	@Column(name = "TRANSACTION_ID")
	private String transactionId;

	@Column(name = "PAY_STATUS")
	private Integer payStatus;

	// bi-directional many-to-one association to DoAuthRequest
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DO_AUTH_REQUEST_ID")
	private DoAuthRequest doAuthRequest;

	// bi-directional many-to-one association to Credit
	@JsonIgnore
	@OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Credit> credits;

	// bi-directional many-to-one association to PaymentOfProof
	@JsonIgnore
	@OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<PaymentOfProof> paymentOfProofs;

	// bi-directional many-to-one association to Rosoom
	@JsonIgnore
	@OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Rosoom> rosooms;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOL_INVOICE_ID")
	private BolInvoice bolInvoice;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bolInvoice == null) ? 0 : bolInvoice.hashCode());
		result = prime * result + ((collectionId == null) ? 0 : collectionId.hashCode());
		result = prime * result + ((invoiceNumber == null) ? 0 : invoiceNumber.hashCode());
		result = prime * result + ((payStatus == null) ? 0 : payStatus.hashCode());
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collection other = (Collection) obj;
		if (bolInvoice == null) {
			if (other.bolInvoice != null)
				return false;
		} else if (!bolInvoice.equals(other.bolInvoice))
			return false;
		if (collectionId == null) {
			if (other.collectionId != null)
				return false;
		} else if (!collectionId.equals(other.collectionId))
			return false;
		if (invoiceNumber == null) {
			if (other.invoiceNumber != null)
				return false;
		} else if (!invoiceNumber.equals(other.invoiceNumber))
			return false;
		if (payStatus == null) {
			if (other.payStatus != null)
				return false;
		} else if (!payStatus.equals(other.payStatus))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}
	
	public void addCreditsLogs(Credit credit) {
		credit.setCollection(this);
		credits.add(credit);
	}
	
	public void addPaymentOfProofsLogs(PaymentOfProof paymentOfProof) {
		paymentOfProof.setCollection(this);
		paymentOfProofs.add(paymentOfProof);
	}
}