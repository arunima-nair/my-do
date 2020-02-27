package ae.dt.deliveryorder.domain;

import java.io.Serializable;

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
 * The persistent class for the DO_AUTH_REQUEST_AUDIT database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DO_AUTH_REQUEST_AUDIT")
@NamedQuery(name="DoAuthRequestAudit.findAll", query="SELECT d FROM DoAuthRequestAudit d")
public class DoAuthRequestAudit extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DO_AUTH_REQUEST_AUDIT_DOAUTHREQUESTAUDITID_GENERATOR", sequenceName="SEQ_DO_AUTH_REQUEST_AUDIT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DO_AUTH_REQUEST_AUDIT_DOAUTHREQUESTAUDITID_GENERATOR")
	@Column(name="DO_AUTH_REQUEST_AUDIT_ID")
	private Long doAuthRequestAuditId;

	@Column(name="BOL_CONTACT_NUMBER")
	private String bolContactNumber;

	@Column(name="BOL_CONTACT_PERSON")
	private String bolContactPerson;

	@Column(name="BOL_EMAIL")
	private String bolEmail;

	@Column(name="BOL_ID")
	private Long bolId;

	@Column(name="BOL_PARTY_NAME")
	private String bolPartyName;

	@Column(name="DO_AUTH_REQUEST_ID")
	private Long doAuthRequestId;

	@Column(name="DO_CONTACT_NUMBER")
	private String doContactNumber;

	@Column(name="DO_CONTACT_PERSON")
	private String doContactPerson;

	@Column(name="DO_EMAIL")
	private String doEmail;

	@Column(name="DO_PARTY_NAME")
	private String doPartyName;


	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="REQ_CONTACT_NUMBER")
	private String reqContactNumber;

	@Column(name="REQ_CONTACT_PERSON")
	private String reqContactPerson;

	@Column(name="REQ_EMAIL")
	private String reqEmail;

	@Column(name="REQ_PARTY_NAME")
	private String reqPartyName;

	private String status;
	
	 @Column(name = "CREATED_BY", length = 400)
	private String createdBy;

	
}