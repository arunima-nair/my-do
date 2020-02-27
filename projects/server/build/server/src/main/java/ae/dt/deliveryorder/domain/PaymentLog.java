package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the PAYMENT_LOG database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PAYMENT_LOG")
@NamedQuery(name = "PaymentLog.findAll", query = "SELECT p FROM PaymentLog p")
public class PaymentLog extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PAYMENT_LOG_PAYMENTLOGID_GENERATOR", sequenceName = "SEQ_PAYMENT_LOG")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAYMENT_LOG_PAYMENTLOGID_GENERATOR")
	@Column(name = "PAYMENT_LOG_ID")
	private Long paymentLogId;

	private Double amount;

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

	@Column(name = "CREATED_BY", length = 400)
	private String createdBy;

	private long isActive = 1L;

	@Column(name = "PAID_BY")
	private String paidBy;
	
	@Column(name = "INITIATOR_ID")
	private Long initiatorId;

	@Column(name = "INITIATED_BY")
	private String initiatedBy;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOL_INVOICE_ID")
	private BolInvoice bolInvoice;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_AGENT_ID", nullable = false)
	private ShippingAgent shippingAgent;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bolInvoice == null) ? 0 : bolInvoice.hashCode());
		result = prime * result + ((paymentLogId == null) ? 0 : paymentLogId.hashCode());
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
		PaymentLog other = (PaymentLog) obj;
		if (bolInvoice == null) {
			if (other.bolInvoice != null)
				return false;
		} else if (!bolInvoice.equals(other.bolInvoice))
			return false;
		if (paymentLogId == null) {
			if (other.paymentLogId != null)
				return false;
		} else if (!paymentLogId.equals(other.paymentLogId))
			return false;
		return true;
	}

}