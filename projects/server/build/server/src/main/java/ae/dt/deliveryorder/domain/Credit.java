package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.math.BigDecimal;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the CREDIT database table.
 * 
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQuery(name="Credit.findAll", query="SELECT c FROM Credit c")
public class Credit extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CREDIT_CASHID_GENERATOR", sequenceName="SEQ_CREDIT")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CREDIT_CASHID_GENERATOR")
	@Column(name="CASH_ID")
	private Long cashId;

	@Column(name="PAYMENT_AMOUNT")
	private BigDecimal paymentAmount;

	private String reference;

	/*
	 * @Column(name="VERSION") private BigDecimal version;
	 */

	//bi-directional many-to-one association to Collection
	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="COLLECTION_ID")
	private Collection collection;

	
}