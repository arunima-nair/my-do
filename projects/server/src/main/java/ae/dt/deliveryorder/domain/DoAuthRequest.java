package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * The persistent class for the DO_AUTH_REQUEST database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DO_AUTH_REQUEST")
@NamedQuery(name = "DoAuthRequest.findAll", query = "SELECT d FROM DoAuthRequest d")
public class 	DoAuthRequest extends BaseEntity implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DO_AUTH_REQUEST_DOAUTHREQUESTID_GENERATOR", sequenceName = "SEQ_DO_AUTH_REQUEST",allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DO_AUTH_REQUEST_DOAUTHREQUESTID_GENERATOR")
	@Column(name = "DO_AUTH_REQUEST_ID")
	private Long doAuthRequestId;

	@Column(name = "BOL_CONTACT_NUMBER")
	private String bolContactNumber;

	@Column(name = "BOL_CONTACT_PERSON")
	private String bolContactPerson;

	@Column(name = "BOL_EMAIL")
	private String bolEmail;

	@Column(name = "BOL_PARTY_NAME")
	private String bolPartyName;

	@Column(name = "DO_CONTACT_NUMBER")
	private String doContactNumber;

	@Column(name = "DO_CONTACT_PERSON")
	private String doContactPerson;

	@Column(name = "DO_EMAIL")
	private String doEmail;

	@Column(name = "DO_PARTY_NAME")
	private String doPartyName;

	@Column(name = "REQ_CONTACT_NUMBER")
	private String reqContactNumber;

	@Column(name = "REQ_CONTACT_PERSON")
	private String reqContactPerson;

	@Column(name = "REQ_EMAIL")
	private String reqEmail;

	@Column(name = "REQ_PARTY_NAME")
	private String reqPartyName;

	private String status;

	@Column(name = "DO_REF_NO")
	private String doRefNo;
	
	@Column(name = "OBL_COPY")
	private String oblCopy;
	
	@Column(name = "APPROVER_VIEWED")
	private String approvedViewed;
	
	@Column(name = "VIEWED_BY")
	private String viewedBy;
	
	@Column(name = "VIEWED_DATE")
	private Date viewedDate;
	
	@Column(name = "INITIATOR_ID")
	private Long initiatorId;
	
	@Column(name = "INITIATOR_CODE")
	private String initiatorCode;
	
	@Column(name = "INITIATED_BY")
	private String initiatedBy;

	@Column(name = "INITIATOR_TYPE")
	private String initiatorType;
	
	@Column(name = "DO_PARTY_CODE")
	private String doPartyCode;

	@Column(name = "DO_PARTY_TYPE")
	private String doPartyType;
	
	@Column(name = "BL_PARTY_CODE")
	private String blPartyCode;

	@Column(name = "BL_PARTY_TYPE")
	private String blPartyType;
	
	@Column(name = "IS_RETURNED")
	private String isReturned;
	
	// bi-directional one-to-one association to DeliveryOrder
	@JsonIgnore
	@OneToOne(mappedBy = "doAuthRequest", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private DeliveryOrder deliveryOrder;

	// bi-directional many-to-one association to DoAuthDoc
	@JsonIgnore
	@OneToMany(mappedBy = "doAuthRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<DoAuthDoc> doAuthDocs;

	// bi-directional many-to-one association to Bol
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOL_ID")
	private Bol bol;

	// bi-directional many-to-one association to PaymentLog
	@JsonIgnore
	@OneToMany(mappedBy = "doAuthRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<PaymentLog> paymentLogs;

	// bi-directional many-to-one association to Collection
	@JsonIgnore
	@OneToMany(mappedBy = "doAuthRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Collection> collections;

	@JsonIgnore
	@OneToMany(mappedBy = "doAuthRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ApprovalLog> approvalLog;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bol == null) ? 0 : bol.hashCode());
		result = prime * result + ((collections == null) ? 0 : collections.hashCode());
		result = prime * result + ((doAuthRequestId == null) ? 0 : doAuthRequestId.hashCode());
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
		DoAuthRequest other = (DoAuthRequest) obj;
		if (bol == null) {
			if (other.bol != null)
				return false;
		} else if (!bol.equals(other.bol))
			return false;
		if (collections == null) {
			if (other.collections != null)
				return false;
		} else if (!collections.equals(other.collections))
			return false;
		if (doAuthRequestId == null) {
			if (other.doAuthRequestId != null)
				return false;
		} else if (!doAuthRequestId.equals(other.doAuthRequestId))
			return false;
		return true;
	}
	
	public void addPaymentLogs(PaymentLog paymentLog){
		paymentLog.setDoAuthRequest(this);
		paymentLogs.add(paymentLog);
	}
	

	public void addCollectionLogs(Collection collection) {
		collection.setDoAuthRequest(this);
		collections.add(collection);
	}
	
	public void clearAuthDocs() {
		doAuthDocs.clear();
	}

}