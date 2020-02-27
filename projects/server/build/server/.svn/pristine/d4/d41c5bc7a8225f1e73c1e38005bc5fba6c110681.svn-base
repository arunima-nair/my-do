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
@Table(name="SA_ROSOOM_CONFIG")
@NamedQuery(name="SARosoom.findAll", query="SELECT i FROM SARosoom i")
public class SARosoom extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SA_ROSOOM_SAROSOOMID_GENERATOR", sequenceName="SEQ_SA_ROSOOM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SA_ROSOOM_SAROSOOMID_GENERATOR")
	@Column(name="SA_ROSOOM_ID")
	private Long saRosoomId;
	
	@Column(name="CURRENCY")
	private String currency;

	@Column(name="SERVICE_OWNER_ID")
	private String serviceOwnerId;
	
	@Column(name="LICENSE_KEY")
	private String rosoomLicenseKey;
	
	@Column(name="SERVICE_ID")
	private String rosoomServiceId;

	@Column(name="KEY_STORE_LOC")
	private String keyStoreLoc;

	@Column(name="KEY_STORE_PASSWORD")
	private String keyStorePassword;

	@Column(name="KEY_PASSWORD")
	private String keyPassword;

	@Column(name="KEY_ALIAS")
	private String keyAlias;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_AGENT_ID", nullable = false)
	private ShippingAgent shippingAgent;
	
	
	
}