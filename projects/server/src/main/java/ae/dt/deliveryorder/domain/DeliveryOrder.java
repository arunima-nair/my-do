package ae.dt.deliveryorder.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
 * The persistent class for the DELIVERY_ORDER database table.
 * 
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DELIVERY_ORDER")
@NamedQuery(name="DeliveryOrder.findAll", query="SELECT d FROM DeliveryOrder d")
public class DeliveryOrder extends BaseEntity implements Serializable {
	private static final Long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DELIVERY_ORDER_DELIVERYORDERID_GENERATOR", sequenceName="SEQ_DELIVERY_ORDER")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DELIVERY_ORDER_DELIVERYORDERID_GENERATOR")
	@Column(name="DELIVERY_ORDER_ID")
	private Long deliveryOrderId;


	@Column(name="DOCUMENT_PATH")
	private String documentPath;


	//bi-directional one-to-one association to DoAuthRequest
	 @JsonIgnore
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DO_AUTH_REQ_ID")
	private DoAuthRequest doAuthRequest;

	

	

}