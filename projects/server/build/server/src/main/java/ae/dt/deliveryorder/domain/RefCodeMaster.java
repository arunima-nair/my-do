package ae.dt.deliveryorder.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="REF_CODE_MASTER")
public class RefCodeMaster extends BaseEntity {

	@Id
	@SequenceGenerator(name="REF_CODE_MASTER_ID_GENERATOR", sequenceName="SEQ_REF_CODE_MASTER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="REF_CODE_MASTER_ID_GENERATOR")
	@Column(name="REF_CODE_MASTER_ID")
	private Long refCodeMasterId;

	@Column(name="DESCRIPTION")
	private Long deccription;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "refCodeMaster",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<CodeMaster> codeMaster=new HashSet<CodeMaster>();
	
}
