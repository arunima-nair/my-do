package ae.dt.deliveryorder.domain;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the DO_AUTH_DOCS database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="REQUEST_BOL_INVOICE")
@NamedQuery(name="RequestBolInvoice.findAll", query="SELECT r FROM RequestBolInvoice r")
public class RequestBolInvoice extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REQUEST_BOL_INVOICE_REQUESTBOLINVOICEID_GENERATOR", sequenceName="SEQ_REQUEST_BOL_INVOICE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REQUEST_BOL_INVOICE_REQUESTBOLINVOICEID_GENERATOR")
	@Column(name="REQUEST_BOL_INVOICE_ID")
	private Long requestBolInvoiceId;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	@Column(name="BOL_NO")
	private String bolNo;
	@Column(name="BOL_INVOICE_NO")
	private String bolInvoiceNo;
	@Column(name="STATUS")
	private String status;
	@Column(name="REQUEST_TYPE")
	private String reqType;
	@Column(name="SHIPPING_CODE")
	private String shippingCode;
	@Column(name="INITIATOR_CODE")
	private String initiatorCode;
	@Column(name="SHIPPING_NAME")
	private String shippingName;
	@Column(name="SHIPPING_AGENT_TYPE")
	private String shippingType;
}