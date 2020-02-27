package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * The persistent class for the APPROVALALERT_NOTIFICATION_LOG database table.

 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="PAYMENT_OPTION")
@NamedQuery(name="PaymentOption.findAll", query="SELECT P FROM PaymentOption P")
public class PaymentOption extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PAYMENT_OPTION_PAYMENTOPTIONID_GENERATOR", sequenceName="SEQ_PAYMENT_OPTION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAYMENT_OPTION_PAYMENTOPTIONID_GENERATOR")
	@Column(name="PAYMENT_OPTION_ID")
	private Long paymentOptionId;


	@Column(name="SHIPPING_AGENT_CODE")
	private String shippingAgentCode;


	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name="DEFAULT_MODE")
	private String defaultMode;
	
	@Column(name="createdBy")
	private String createdBy;
	
	@Column(name="createdDate")
	private Date createdDate;
	
}