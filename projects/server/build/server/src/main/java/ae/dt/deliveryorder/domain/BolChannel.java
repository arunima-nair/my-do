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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the CHANNEL database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="BOL_CHANNEL")
@NamedQuery(name="BolChannel.findAll", query="SELECT c FROM BolChannel c")
public class BolChannel extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CHANNEL_CHANNELID_GENERATOR", sequenceName="SEQ_BOL_INVOICE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CHANNEL_CHANNELID_GENERATOR")
	@Column(name="CHANNEL_ID")
	private Long channelId;
	@Column(name="REFERENCE")
	private String reference;

	 @JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BOL_ID")
	private Bol bol;


	//bi-directional many-to-one association to ChannelType
	 @JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CHANNEL_TYPE_ID")
	private ChannelType channelType;



}