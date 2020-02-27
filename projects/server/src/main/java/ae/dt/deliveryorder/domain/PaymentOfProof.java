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

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the PAYMENT_OF_PROOF database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PAYMENT_OF_PROOF")
@NamedQuery(name="PaymentOfProof.findAll", query="SELECT p FROM PaymentOfProof p")
public class PaymentOfProof extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PAYMENT_OF_PROOF_PAYMENTOFPROOFID_GENERATOR", sequenceName="SEQ_PAYMENT_OF_PROOF")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAYMENT_OF_PROOF_PAYMENTOFPROOFID_GENERATOR")
	@Column(name="PAYMENT_OF_PROOF_ID")
	private Long paymentOfProofId;
	
	@Column(name="PAYMENT_AMOUNT")
	private BigDecimal paymentAmount;

	@Column(name="REFERENCE")
	private String reference;
	
	@Column(name="REFERENCE_NUMBER")
	private String refNumber;

	//bi-directional many-to-one association to Collection
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COLLECTION_ID")
	private Collection collection;

	
}