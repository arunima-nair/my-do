package ae.dt.common.domain;

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
@Table(name="DT_AGENT_VIEW")
@NamedQuery(name="DTAgentView.findAll", query="SELECT d FROM DTAgentView d")
public class DTAgentView  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DT_AGENT_VIEW_DTAGENTVIEWID_GENERATOR", sequenceName="SEQ_DT_AGENT_VIEW")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DT_AGENT_VIEW_DTAGENTVIEWID_GENERATOR")
	@Column(name="GCID")
	private Long gcId;

	@Column(name="COMPANY_NAME")
	private String companyName;

	@Column(name="BUSINESS_UNIT_ID")
	private String businessUnitId;

	@Column(name="BUSINESS_UNIT_NAME")
	private String businessUnitName;

	@Column(name="BUSINESS_GROUP_ID")
	private String businessGroupId;

	@Column(name="AGENT_CODE")
	private String agentCode;

	@Column(name="AGENT_NAME")
	private String agentName;
	
	@Column(name="BUSINESS_GROUP_DESC")
	private String businessGroupDesc;
	
	@Column(name="BUSINESS_SUB_ID")
	private String businessSubId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DTAgentView that = (DTAgentView) o;

		if (!getGcId().equals(that.getGcId())) return false;
		if (!getBusinessGroupId().equals(that.getBusinessGroupId())) return false;
		if (!getAgentCode().equals(that.getAgentCode())) return false;
		if (!getAgentName().equals(that.getAgentName())) return false;
		return getBusinessSubId().equals(that.getBusinessSubId());
	}

	@Override
	public int hashCode() {
		int result = getGcId().hashCode();
		result = 31 * result + getBusinessGroupId().hashCode();
		result = 31 * result + getAgentCode().hashCode();
		result = 31 * result + getAgentName().hashCode();
		result = 31 * result + getBusinessSubId().hashCode();
		return result;
	}
}