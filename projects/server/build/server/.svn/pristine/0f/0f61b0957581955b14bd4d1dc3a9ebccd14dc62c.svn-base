package ae.dt.deliveryorder.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the DO_AUTH_DOCS database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DO_AUTH_DOCS")
@NamedQuery(name="DoAuthDoc.findAll", query="SELECT d FROM DoAuthDoc d")
public class DoAuthDoc extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DO_AUTH_DOCS_DOAUTHDOCSID_GENERATOR", sequenceName="SEQ_DO_AUTH_DOCS",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DO_AUTH_DOCS_DOAUTHDOCSID_GENERATOR")
	@Column(name="DO_AUTH_DOCS_ID")
	private Long doAuthDocsId;

	//private BigDecimal document;

	@Column(name="DOCUMENT_PATH")
	private String documentPath;

	//bi-directional one-to-one association to Document
	 @JsonIgnore
	 @ManyToOne( fetch=FetchType.LAZY, optional = false)
	@JoinColumn(name="DOCUMENT_ID", nullable = false)
	private Document documentBean;

	//bi-directional many-to-one association to DoAuthRequest
	 @JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DO_AUTH_REQUEST_ID",nullable = false)
	private DoAuthRequest doAuthRequest;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		DoAuthDoc doAuthDoc = (DoAuthDoc) o;

		if (getDoAuthDocsId() != null ? !getDoAuthDocsId().equals(doAuthDoc.getDoAuthDocsId()) : doAuthDoc.getDoAuthDocsId() != null)
			return false;
		if (!getDocumentPath().equals(doAuthDoc.getDocumentPath())) return false;
		if (!getDocumentBean().equals(doAuthDoc.getDocumentBean())) return false;
		return getDoAuthRequest() != null ? getDoAuthRequest().equals(doAuthDoc.getDoAuthRequest()) : doAuthDoc.getDoAuthRequest() == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (getDoAuthDocsId() != null ? getDoAuthDocsId().hashCode() : 0);
		result = 31 * result + getDocumentPath().hashCode();
		result = 31 * result + getDocumentBean().hashCode();
		result = 31 * result + (getDoAuthRequest() != null ? getDoAuthRequest().hashCode() : 0);
		return result;
	}
}