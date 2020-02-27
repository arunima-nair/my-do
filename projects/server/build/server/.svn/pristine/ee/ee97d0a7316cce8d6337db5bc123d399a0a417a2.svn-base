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
 * The persistent class for the SHIPPING_AGENT_ATTRIBUTES database table.
 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="SHIPPING_AGENT_ATTRIBUTES")
@NamedQuery(name="ShippingAgentAttributes.findAll", query="SELECT s FROM ShippingAgentAttributes s")
public class ShippingAgentAttributes extends BaseEntity  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHIPPING_AGENT_ATTRIBUTES_SHIPPINGAGENTATTRIBUTESID_GENERATOR", sequenceName="SEQ_SHIPPING_AGENT_ATTRIBUTES")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHIPPING_AGENT_ATTRIBUTES_SHIPPINGAGENTATTRIBUTESID_GENERATOR")
	@Column(name="SHIPPING_AGENT_ATTRIBUTES_ID")
	private Long shippingAgentAttributesId;


	@Column(name="ATTRIBUTE_NAME")
	private String attributeName;

	@Column(name="ATTRIBUTE_VALUE")
	private String attributeValue;
	
	@Column(name="ATTRIBUTE_PARAM")
	private String attributeParam;
	
	 @JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHIPPING_AGENT_ID", nullable = false)
	private ShippingAgent shippingAgent;
}