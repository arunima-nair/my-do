package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.ReturnRemark;
import ae.dt.deliveryorder.dto.ReturnRemarksDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:09+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class ReturnRemarksMapperImpl implements ReturnRemarksMapper {

    @Override
    public ReturnRemark dTOtoDoman(ReturnRemarksDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ReturnRemark returnRemark = new ReturnRemark();

        returnRemark.setReturnRemarksId( dto.getReturnRemarksId() );
        returnRemark.setRemarks( dto.getRemarks() );

        return returnRemark;
    }

    @Override
    public ReturnRemarksDTO domaintoDTO(ReturnRemark destination) {
        if ( destination == null ) {
            return null;
        }

        ReturnRemarksDTO returnRemarksDTO = new ReturnRemarksDTO();

        returnRemarksDTO.setReturnRemarksId( destination.getReturnRemarksId() );
        returnRemarksDTO.setRemarks( destination.getRemarks() );

        return returnRemarksDTO;
    }
}
