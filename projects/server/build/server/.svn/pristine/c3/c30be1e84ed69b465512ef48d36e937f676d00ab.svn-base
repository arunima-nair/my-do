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
@Table(name="SHIPPING_AGENT")
@NamedQuery(name="ShippingAgent.findAll", query="SELECT s FROM ShippingAgent s")
public class ShippingAgent extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHIPPING_AGENT_SHIPPINGAGENTID_GENERATOR", sequenceName="SEQ_SHIPPING_AGENT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHIPPING_AGENT_SHIPPINGAGENTID_GENERATOR")
	@Column(name="SHIPPING_AGENT_ID")
	private Long shippingAgentId;

	@Column(name="SHIPPING_AGENT_CODE")
	private String shippingAgentCode;

	@Column(name="SHIPPING_AGENT_NAME")
	private String shippingAgentName;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shippingAgent",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<ShippingAgentAttributes> shippingAgentAttributes;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shippingAgent",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<SAInitiatorInvoiceType> saInitiatorInvoiceType;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "shippingAgent",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<PaymentLog> paymentLog;
	
}