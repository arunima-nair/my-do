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
 * The persistent class for the BOL_INVOICE database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BOL_INVOICE")
@NamedQuery(name="BolInvoice.findAll", query="SELECT b FROM BolInvoice b")
public class BolInvoice extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BOL_INVOICE_BOLINVOICEID_GENERATOR", sequenceName="SEQ_BOL_INVOICE",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOL_INVOICE_BOLINVOICEID_GENERATOR")
	@Column(name="BOL_INVOICE_ID")
	private Long bolInvoiceId;

	@Temporal(TemporalType.DATE)
	@Column(name="INVOICE_DATE")
	private Date invoiceDate;

	@Column(name="INVOICE_NUMBER")
	private String invoiceNumber;

	@Column(name="INVOICE_VALUE")
	private Double invoiceValue;

	@Column(name="PATH")
	private String path;

	@Column(name="CURRENCY")
	private String currency;

	@Temporal(TemporalType.DATE)
	@Column(name="INVOICE_VALIDITY_DATE")
	private Date invoiceValidityDate;

	//bi-directional many-to-one association to Bol
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BOL_ID", nullable = false)
	private Bol bol;
	
    @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVOICE_TYPE_ID", nullable = true)
	private InvoiceType invoiceType;

    @JsonIgnore
   	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bolInvoice",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<PaymentLog> paymentLogs;
    
    @JsonIgnore
   	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bolInvoice",cascade=CascadeType.ALL,orphanRemoval=true)
    private Set<Collection> collections;
    
	@Column(name="STATUS")
	private String status;
	
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bolInvoiceId == null) ? 0 : bolInvoiceId.hashCode());
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
		BolInvoice other = (BolInvoice) obj;
		if (bolInvoiceId == null) {
			if (other.bolInvoiceId != null)
				return false;
		} else if (!bolInvoiceId.equals(other.bolInvoiceId))
			return false;
		return true;
	}

}