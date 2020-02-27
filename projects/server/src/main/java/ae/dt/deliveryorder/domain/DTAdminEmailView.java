package ae.dt.deliveryorder.domain;

import java.io.Serializable;
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

import ae.dt.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Kamala.Devi on 4/9/2019.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DT_ADMIN_EMAIL_VIEW")
@NamedQuery(name="DTAdminEmailView.findAll", query="SELECT d FROM DTAdminEmailView d")
public class DTAdminEmailView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@SequenceGenerator(name="DT_AGENT_VIEW_DTAGENTVIEWID_GENERATOR", sequenceName="SEQ_DT_AGENT_VIEW")
  //  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DT_AGENT_VIEW_DTAGENTVIEWID_GENERATOR")
    @Column(name="AGENT_CODE")
    private String agentCode;

    @Column(name="AGENT_NAME")
    private String agentName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="BUSINESS_SUB_ID")
    private String businessSubId;


    @Column(name="BUSINESS_GROUP_DESC")
    private String businessGroupDesc;

    @Column(name="BUSINESS_UNIT_ID")
    private String businessUnitId;

    @Column(name="BUSINESS_UNIT_NAME")
    private String businessUnitName;

    @Column(name="REF_PARTY")
    private String refParty;

}

