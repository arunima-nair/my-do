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
 * Created by Arunima.Nair on 9/29/2019.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="DT_USER_VIEW")
@NamedQuery(name="DTUserView.findAll", query="SELECT d FROM DTUserView d")
public class DTUserView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="USER_ID")
    private String userId;
    
    @Column(name="USERNAME")
    private String userName;

    @Column(name="AGENT_NAME")
    private String agentName;

    @Column(name="EMAIL")
    private String email;

    @Column(name="BUSINESS_SUB_ID")
    private String businessSubId;


    @Column(name="AGENT_CODE")
    private String agentCode;

    @Column(name="USER_TYPE")
    private String userType;

   

}

