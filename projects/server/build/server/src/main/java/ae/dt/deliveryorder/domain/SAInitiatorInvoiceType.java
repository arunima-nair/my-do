package ae.dt.deliveryorder.domain;

import java.io.Serializable;

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
@Table(name="SA_INITIATOR_INV_TYPE_CONFIG")
@NamedQuery(name="SAInitiatorInvoiceType.findAll", query="SELECT i FROM SAInitiatorInvoiceType i")
public class SAInitiatorInvoiceType extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SA_INITIATOR_INVOICE_TYPE_SAINITIATORINVOICETYPEID_GENERATOR", sequenceName="SEQ_SA_INITIATOR_INVOICE_TYPE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SA_INITIATOR_INVOICE_TYPE_SAINITIATORINVOICETYPEID_GENERATOR")
	@Column(name="SA_INITIATOR_INVOICE_TYPE_ID")
	private Long saInitiatorInvoiceTypeId;

	

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_AGENT_ID", nullable = false)
	private ShippingAgent shippingAgent;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVOICE_TYPE_ID", nullable = false)
	private InvoiceType invoiceType;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INITIATOR_ID", nullable = false)
	private Initiator initiator;
	
}