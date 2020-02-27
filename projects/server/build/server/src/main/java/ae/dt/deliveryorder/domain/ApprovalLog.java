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
 * The persistent class for the APPROVAL_LOG database table.

 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="APPROVAL_LOG")
@NamedQuery(name="ApprovalLog.findAll", query="SELECT a FROM ApprovalLog a")
public class ApprovalLog extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="APPROVAL_LOG_APPROVALLOGID_GENERATOR", sequenceName="SEQ_APPROVAL_LOG")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="APPROVAL_LOG_APPROVALLOGID_GENERATOR")
	@Column(name="APPROVAL_LOG_ID")
	private Long approvalLogId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	
	@Column(name="STATUS")
	private String status;

	//bi-directional many-to-one association to RejectionRemark
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REJECTION_REMARKS_ID")
	private RejectionRemark rejectionRemark;

	//bi-directional many-to-one association to ReturnRemark
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="RETURN_REMARKS_ID")
	private ReturnRemark returnRemark;
	
	@Column(name="COMMENTS")
	private String comments;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DO_AUTH_REQUEST_ID")
	private DoAuthRequest doAuthRequest;

	
}