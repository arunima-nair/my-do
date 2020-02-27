package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Joseph Vibik on 06/23/2019.
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILE_LOG")
public class FileLog extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FILE_LOG_ID_GENERATOR", sequenceName="SEQ_FILE_LOG",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILE_LOG_ID_GENERATOR")
	@Column(name="FILE_LOG_ID")
	private Long fileLogId;
	
	@Column(name="REFERENCE_NUMBER")
	private String referenceNumber;
	
	@Column(name="FILE_NAME")
	private String fileName;
	
	@Column(name="FILE_PATH")
	private String filePath;
	
	@Column(name="UPLOAD_TYPE")
	private String uploadType;
	
	@Column(name="STATUS")
	private String Status;
	
	@Column(name ="SHIPPING_AGENT_CODE")
	private String shippingAgentCode;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fileLog",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<FileLogDetail> fileLogDetails=new HashSet<FileLogDetail>();

	public void addFileLogDetails(FileLogDetail fileLogDetail){
		fileLogDetail.setFileLog(this);
		fileLogDetails.add(fileLogDetail);
	}


}
