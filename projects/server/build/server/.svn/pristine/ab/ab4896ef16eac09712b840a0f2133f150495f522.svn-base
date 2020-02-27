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
@NamedQuery(name="FileUpload.findAll", query="SELECT f FROM FileUpload f")
public class FileUpload extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FILEUPLOAD_FILEUPLOADID_GENERATOR", sequenceName="SEQ_FILEUPLOAD")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILEUPLOAD_FILEUPLOADID_GENERATOR")
	@Column(name="FILE_UPLOAD_ID")
	private Long filUploadId;

	@Column(name="FILE_UPLOAD_NAME")
	private String fileUploadName;

	@Column(name="FILE_UPLOAD_TYPE")
	private String fileUploadType;
	
	@Column(name="FILE_UPLOAD_PATH")
	private String fileUploadPath;

	@Column(name="FILE_UPLOAD_STATUS")
	private String fileUploadstatus;

	@Column(name="FILE_RECEIVED")
	private String fileReceived;
	
	@Column(name="FILE_PROCESSED")
	private String fileProcessed;
	
	@Column(name="FILE_IGNORED")
	private String fileIgnored;
	
	@Column(name="FILE_REJECTED")
	private String fileRejected;

	 @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fileUpload",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<FileUploadDetails>fileUploadDetails;
}
