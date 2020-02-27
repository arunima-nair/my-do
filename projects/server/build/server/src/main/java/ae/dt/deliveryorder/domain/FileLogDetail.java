package ae.dt.deliveryorder.domain;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "FILE_LOG_DETAILS")

public class FileLogDetail extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FILE_LOG_DETAILS_ID_GENERATOR", sequenceName="SEQ_FILE_LOG_DETAILS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILE_LOG_DETAILS_ID_GENERATOR")
	@Column(name = "FILE_LOG_DETAILS_ID")
	private Long fileLogDetailsId;


	@Column(name = "IS_PROCESSED")
	private String isProcessed;

	@Column(name = "ERROR_CODE")
	private String errorCode;
	
	@Column(name ="BOL_NUMBER")
	private String bolNumber;
	
	@Column(name ="INVOICE_NUMBER")
	private String invoiceNumber;
	
	// bi-directional many-to-one association to Bol

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FILE_LOG_ID", nullable = false)
	private FileLog fileLog;

}
