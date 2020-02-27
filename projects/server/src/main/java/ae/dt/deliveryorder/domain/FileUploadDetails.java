package ae.dt.deliveryorder.domain;

import java.io.Serializable;

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
@NamedQuery(name = "FileUploadDetails.findAll", query = "SELECT f FROM FileUploadDetails f")
public class FileUploadDetails extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "FILEUPLOADDETAILS_FILEUPLOADDETAILSID_GENERATOR", sequenceName = "SEQ_FILEUPLOADDETAILS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEUPLOADDETAILS_FILEUPLOADDETAILSID_GENERATOR")
	@Column(name = "DETAILS_ID")
	private Long detailsId;

	@Column(name = "STATUS")
	private String status;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_UPLOAD_ID", nullable = false)
	private FileUpload fileUpload;
}
