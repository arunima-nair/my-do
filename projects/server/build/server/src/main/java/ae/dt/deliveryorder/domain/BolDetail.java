package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
 * The persistent class for the BOL_DETAILS database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BOL_DETAILS")
@NamedQuery(name="BolDetail.findAll", query="SELECT b FROM BolDetail b")
public class BolDetail extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BOL_DETAILS_BOLDETAILSID_GENERATOR", sequenceName="SEQ_BOL_DETAILS")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BOL_DETAILS_BOLDETAILSID_GENERATOR")
	@Column(name="BOL_DETAILS_ID")
	private Long bolDetailsId;

	@Column(name="CHANNEL_ID")
	private BigDecimal channelId;

	@Column(name="IMPORTER_CODE")
	private String importerCode;

	@Column(name="SHIPPING_AGENT_CODE")
	private String shippingAgentCode;

	@Column(name="SHIPPING_AGENT_NAME")
	private String shippingAgentName;

	@Temporal(TemporalType.DATE)

	@Column(name="VESSEL_ETA")
	private Date vesselEta;


	@Column(name="VESSEL_NAME")
	private String vesselName;

	@Column(name="VOYAGE_NUMBER")
	private String voyageNumber;

	@Column(name="CONTAINER_COUNT")
	private String containerCount;

	@Column(name="CONSIGNEE_NAME")
	private String consigneeName;


	@Temporal(TemporalType.DATE)
	@Column(name="VESSEL_ATA")
	private Date vesselAta;



	//bi-directional many-to-one association to Bol

	 @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOL_ID", nullable = false)
	private Bol bol;



}