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
 * The persistent class for the SHIPPING_AGENT database table.

 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="INVOICE_TYPE")
@NamedQuery(name="InvoiceType.findAll", query="SELECT i FROM InvoiceType i")
public class InvoiceType extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INVOICE_TYPE_INVOICETYPEID_GENERATOR", sequenceName="SEQ_INVOICE_TYPE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INVOICE_TYPE_INVOICETYPEID_GENERATOR")
	@Column(name="INVOICE_TYPE_ID")
	private Long invoiceTypeId;

	@Column(name="INVOICE_TYPE_NAME")
	private String invoiceTypeName;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceType",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<SAInitiatorInvoiceType> saInitiatorInvoiceType;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoiceType",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<BolInvoice> bolInvoice;
}