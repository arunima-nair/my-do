package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.ApprovalLog;
import ae.dt.deliveryorder.dto.ApprovalLogDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class ApprovalLogMapperImpl implements ApprovalLogMapper {

    @Autowired
    private RejectionRemarksMapper rejectionRemarksMapper;
    @Autowired
    private ReturnRemarksMapper returnRemarksMapper;

    @Override
    public ApprovalLog dTOtoDoman(ApprovalLogDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ApprovalLog approvalLog = new ApprovalLog();

        approvalLog.setApprovalLogId( dto.getApprovalLogId() );
        approvalLog.setCreatedBy( dto.getCreatedBy() );
        approvalLog.setCreatedDate( dto.getCreatedDate() );
        approvalLog.setStatus( dto.getStatus() );
        approvalLog.setRejectionRemark( rejectionRemarksMapper.dTOtoDoman( dto.getRejectionRemark() ) );
        approvalLog.setReturnRemark( returnRemarksMapper.dTOtoDoman( dto.getReturnRemark() ) );
        approvalLog.setComments( dto.getComments() );

        return approvalLog;
    }

    @Override
    public ApprovalLogDTO domaintoDTO(ApprovalLog destination) {
        if ( destination == null ) {
            return null;
        }

        ApprovalLogDTO approvalLogDTO = new ApprovalLogDTO();

        approvalLogDTO.setApprovalLogId( destination.getApprovalLogId() );
        approvalLogDTO.setStatus( destination.getStatus() );
        approvalLogDTO.setRejectionRemark( rejectionRemarksMapper.domaintoDTO( destination.getRejectionRemark() ) );
        approvalLogDTO.setReturnRemark( returnRemarksMapper.domaintoDTO( destination.getReturnRemark() ) );
        approvalLogDTO.setComments( destination.getComments() );
        approvalLogDTO.setCreatedBy( destination.getCreatedBy() );
        approvalLogDTO.setCreatedDate( destination.getCreatedDate() );

        return approvalLogDTO;
    }

    @Override
    public Set<ApprovalLogDTO> domaintoDTOset(Set<ApprovalLog> dTOset) {
        if ( dTOset == null ) {
            return null;
        }

        Set<ApprovalLogDTO> set = new HashSet<ApprovalLogDTO>( Math.max( (int) ( dTOset.size() / .75f ) + 1, 16 ) );
        for ( ApprovalLog approvalLog : dTOset ) {
            set.add( domaintoDTO( approvalLog ) );
        }

        return set;
    }

    @Override
    public Set<ApprovalLog> dTOtoDomanset(Set<ApprovalLogDTO> dTOset) {
        if ( dTOset == null ) {
            return null;
        }

        Set<ApprovalLog> set = new HashSet<ApprovalLog>( Math.max( (int) ( dTOset.size() / .75f ) + 1, 16 ) );
        for ( ApprovalLogDTO approvalLogDTO : dTOset ) {
            set.add( dTOtoDoman( approvalLogDTO ) );
        }

        return set;
    }
}
