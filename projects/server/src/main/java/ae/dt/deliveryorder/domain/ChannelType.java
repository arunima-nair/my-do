package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the CHANNEL_TYPE database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="CHANNEL_TYPE")
@NamedQuery(name="ChannelType.findAll", query="SELECT c FROM ChannelType c")
public class ChannelType extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHANNEL_TYPE_CHANNELTYPEID_GENERATOR", sequenceName="SEQ_CHANNEL_TYPE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHANNEL_TYPE_CHANNELTYPEID_GENERATOR")
	@Column(name="CHANNEL_TYPE_ID")
	private long channelTypeId;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="VALUE")
	private String value;

	//bi-directional many-to-one association to Channel
	@OneToMany(mappedBy="channelType")
	private Set<BolChannel> bolChannels;

	
}