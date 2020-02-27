package ae.dt.deliveryorder.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The persistent class for the APPROVALALERT_NOTIFICATION_LOG database table.

 *
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="ALERT_NOTIFICATION")
@NamedQuery(name="AlertNotification.findAll", query="SELECT a FROM AlertNotification a")
public class AlertNotification extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ALERT_NOTIFICATION_ALERTNOTIFICATIONID_GENERATOR", sequenceName="SEQ_ALERT_NOTIFICATION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ALERT_NOTIFICATION_ALERTNOTIFICATIONID_GENERATOR")
	@Column(name="ALERT_ID")
	private Long alertId;


	@Column(name="STATUS")
	private String status;


	@Column(name="BOL_ID")
	private String bolId;
	
	@Column(name="EMAILID")
	private String emailId;
	
	@Column(name="createdBy")
	private String createdBy;
	
	@Column(name="createdDate")
	private Date createdDate;
	
}