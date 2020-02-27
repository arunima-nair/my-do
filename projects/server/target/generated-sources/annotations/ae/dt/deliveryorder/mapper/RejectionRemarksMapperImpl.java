package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.RejectionRemark;
import ae.dt.deliveryorder.dto.RejectionRemarksDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class RejectionRemarksMapperImpl implements RejectionRemarksMapper {

    @Override
    public RejectionRemark dTOtoDoman(RejectionRemarksDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RejectionRemark rejectionRemark = new RejectionRemark();

        rejectionRemark.setRejectionRemarksId( dto.getRejectionRemarksId() );
        rejectionRemark.setRemarks( dto.getRemarks() );

        return rejectionRemark;
    }

    @Override
    public RejectionRemarksDTO domaintoDTO(RejectionRemark destination) {
        if ( destination == null ) {
            return null;
        }

        RejectionRemarksDTO rejectionRemarksDTO = new RejectionRemarksDTO();

        rejectionRemarksDTO.setRejectionRemarksId( destination.getRejectionRemarksId() );
        rejectionRemarksDTO.setRemarks( destination.getRemarks() );

        return rejectionRemarksDTO;
    }
}
