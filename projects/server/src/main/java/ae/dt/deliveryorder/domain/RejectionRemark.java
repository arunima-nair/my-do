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

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the REJECTION_REMARKS database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="REJECTION_REMARKS")
@NamedQuery(name="RejectionRemark.findAll", query="SELECT r FROM RejectionRemark r")
public class RejectionRemark extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="REJECTION_REMARKS_REJECTIONREMARKSID_GENERATOR", sequenceName="SEQ_REJECTION_REMARKS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REJECTION_REMARKS_REJECTIONREMARKSID_GENERATOR")
	@Column(name="REJECTION_REMARKS_ID")
	private Long rejectionRemarksId;

	private String remarks;

	//bi-directional many-to-one association to ApprovalLog
	@OneToMany(mappedBy="rejectionRemark",cascade=CascadeType.ALL, fetch=FetchType.LAZY,orphanRemoval=true)
	private Set<ApprovalLog> approvalLogs;

	@Column(name="CREATED_BY")
	private String createdBy;
}