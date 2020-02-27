package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.HashSet;
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


/**
 * The persistent class for the BOL database table.
 * 
 */

/**
 * @author ARUNIMA NAIR
 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name="Bol.findAll", query="SELECT b FROM Bol b")
public class Bol extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BOL_BOLID_GENERATOR", sequenceName="SEQ_BOL",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOL_BOLID_GENERATOR")
	@Column(name="BOL_ID")
	private Long bolId;

	@Column(name="BOL_NUMBER")
	private String bolNumber;

	@Column(name="BOL_TYPE")
	private String bolType;

	@Column(name="STATUS")
	private String status;

	@Column(name="BOL_GROUP_ID")
	private Long bolGroupId;

	//bi-directional many-to-one association to BolDetail

	 @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bol",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<BolDetail> bolDetails=new HashSet<BolDetail>();

	//bi-directional many-to-one association to BolInvoice
	 @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bol",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<BolInvoice> bolInvoices=new HashSet<BolInvoice>();

	//bi-directional many-to-one association to DoAuthRequest
	 @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bol",cascade=CascadeType.ALL)
	private Set<DoAuthRequest> doAuthRequests=new HashSet<DoAuthRequest>();

	//bi-directional many-to-one association to BolDetail
	 @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bol",cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<BolChannel> bolChannels=new HashSet<BolChannel>();

	public void addInvoices(BolInvoice bolInvoice){
		bolInvoice.setBol(this);
		bolInvoices.add(bolInvoice);
	}

	public void addBolDetails(BolDetail bolDetail){
		bolDetail.setBol(this);
		bolDetails.add(bolDetail);
	}
	public void clearBolInvoices(){
		bolInvoices.clear();
	}

}