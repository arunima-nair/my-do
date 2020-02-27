package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Date;
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
 * The persistent class for the SHIPPING_AGENT database table.

 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="SA_INITIATOR_PAYMENT_CONFIG")
@NamedQuery(name="SAInitiatorPayment.findAll", query="SELECT i FROM SAInitiatorPayment i")
public class SAInitiatorPayment extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SA_INITIATOR_PAYMENT_SAINITIATORPAYMENTID_GENERATOR", sequenceName="SEQ_SA_INITIATOR_PAYMENT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SA_INITIATOR_PAYMENT_SAINITIATORPAYMENTID_GENERATOR")
	@Column(name="SA_INITIATOR_PAYMENT_ID")
	private Long saInitiatorPaymentId;
	
	@Column(name="PAYMENT_ALLOWED")
	private String paymentAllowed;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_AGENT_ID", nullable = false)
	private ShippingAgent shippingAgent;
	
		
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INITIATOR_ID", nullable = false)
	private Initiator initiator;
	
}