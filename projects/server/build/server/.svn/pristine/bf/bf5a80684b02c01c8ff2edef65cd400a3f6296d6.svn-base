package ae.dt.deliveryorder.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import ae.dt.deliveryorder.domain.RefCodeMaster.RefCodeMasterBuilder;
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
@Table(name="CODE_MASTER")
public class CodeMaster extends BaseEntity {

	@Id
	@SequenceGenerator(name="CODE_MASTER_CODEMASTERID_GENERATOR", sequenceName="REF_CODE_MASTER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CODE_MASTER_CODEMASTERID_GENERATOR")
	@Column(name="CODE_MASTER_ID")
	private Long codeMasterId;
	
	@Column(name="CODE_MASTER_VALUE")
	private String codeMasterValue;
	
	@Column(name="CODE_MASTER_DESC")
	private String codeMasterDesc;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="REF_CODE_MASTER_ID", nullable = false)
	private RefCodeMaster refCodeMaster;
	
}
