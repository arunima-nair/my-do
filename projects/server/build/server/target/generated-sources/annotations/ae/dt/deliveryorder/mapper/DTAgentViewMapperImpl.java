package ae.dt.deliveryorder.mapper;

import ae.dt.common.domain.DTAgentView;
import ae.dt.common.dto.DTAgentViewDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:09+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class DTAgentViewMapperImpl implements DTAgentViewMapper {

    @Override
    public DTAgentView dTOtoDoman(DTAgentViewDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DTAgentView dTAgentView = new DTAgentView();

        dTAgentView.setGcId( dto.getGcId() );
        dTAgentView.setCompanyName( dto.getCompanyName() );
        dTAgentView.setBusinessUnitId( dto.getBusinessUnitId() );
        dTAgentView.setBusinessUnitName( dto.getBusinessUnitName() );
        dTAgentView.setBusinessGroupId( dto.getBusinessGroupId() );
        dTAgentView.setAgentCode( dto.getAgentCode() );
        dTAgentView.setAgentName( dto.getAgentName() );
        dTAgentView.setBusinessGroupDesc( dto.getBusinessGroupDesc() );
        dTAgentView.setBusinessSubId( dto.getBusinessSubId() );

        return dTAgentView;
    }

    @Override
    public DTAgentViewDTO domaintoDTO(DTAgentView destination) {
        if ( destination == null ) {
            return null;
        }

        DTAgentViewDTO dTAgentViewDTO = new DTAgentViewDTO();

        dTAgentViewDTO.setGcId( destination.getGcId() );
        dTAgentViewDTO.setCompanyName( destination.getCompanyName() );
        dTAgentViewDTO.setBusinessUnitId( destination.getBusinessUnitId() );
        dTAgentViewDTO.setBusinessUnitName( destination.getBusinessUnitName() );
        dTAgentViewDTO.setBusinessGroupId( destination.getBusinessGroupId() );
        dTAgentViewDTO.setAgentCode( destination.getAgentCode() );
        dTAgentViewDTO.setAgentName( destination.getAgentName() );
        dTAgentViewDTO.setBusinessGroupDesc( destination.getBusinessGroupDesc() );
        dTAgentViewDTO.setBusinessSubId( destination.getBusinessSubId() );

        return dTAgentViewDTO;
    }
}
