package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the SHIPPING_AGENT database table.

 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Initiator")
@NamedQuery(name="Initiator.findAll", query="SELECT i FROM Initiator i")
public class Initiator extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INITIATOR_INITIATORID_GENERATOR", sequenceName="SEQ_INITIATOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INITIATOR_INITIATORID_GENERATOR")
	@Column(name="INITIATOR_ID")
	private Long initiatorId;

	@Column(name="INITIATOR_NAME")
	private String initiatorName;
	
	@Column(name="INITIATOR_TYPE")
	private String initiatorType;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "initiator",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<SAInitiatorInvoiceType> saInitiatorInvoiceType;
	
	
}