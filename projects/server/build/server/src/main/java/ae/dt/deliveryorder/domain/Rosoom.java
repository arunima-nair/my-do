package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the ROSOOM database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name="Rosoom.findAll", query="SELECT r FROM Rosoom r")
public class Rosoom extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ROSOOM_ROSOOMID_GENERATOR", sequenceName="SEQ_ROSOOM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ROSOOM_ROSOOMID_GENERATOR")
	@Column(name="ROSOOM_ID")
	private Long rosoomId;

	@Temporal(TemporalType.DATE)
	@Column(name="FI_DATE")
	private Date fiDate;

	@Column(name="FI_INSTRUMENT")
	private String fiInstrument;

	@Column(name="FI_TRANSACTION_ID")
	private String fiTransactionId;

	@Column(name="GATEWAY_TRANSACTION_ID")
	private String gatewayTransactionId;
	@Column(name="PAYMENT_AMOUNT")
	private Double paymentAmount;

	private String remarks;
	//bi-directional many-to-one association to Collection
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COLLECTION_ID")
	private Collection collection;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((collection == null) ? 0 : collection.hashCode());
		result = prime * result + ((rosoomId == null) ? 0 : rosoomId.hashCode());
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
		Rosoom other = (Rosoom) obj;
		if (collection == null) {
			if (other.collection != null)
				return false;
		} else if (!collection.equals(other.collection))
			return false;
		if (rosoomId == null) {
			if (other.rosoomId != null)
				return false;
		} else if (!rosoomId.equals(other.rosoomId))
			return false;
		return true;
	}


}