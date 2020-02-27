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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the DOCUMENT database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name="Document.findAll", query="SELECT d FROM Document d")
public class Document extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DOCUMENT_DOCUMENTID_GENERATOR", sequenceName="SEQ_DOCUMENT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DOCUMENT_DOCUMENTID_GENERATOR")
	@Column(name="DOCUMENT")
	private Long documentId;

	@Column(name="DOCUMENT_VALUE")
	private String documentValue;

	//bi-directional one-to-one association to DoAuthDoc
	 @JsonIgnore
	@OneToMany(mappedBy="documentBean",cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
	//private DoAuthDoc doAuthDoc;
	 private Set<DoAuthDoc> doAuthDoc;

	

}