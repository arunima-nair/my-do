package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.BolDetail;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:08+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class BolDetailsMapperImpl implements BolDetailsMapper {

    @Override
    public BolDetail boldetailDTOtoDoman(BoLDetailDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BolDetail bolDetail = new BolDetail();

        if ( dto.getIsActive() != null ) {
            bolDetail.setIsActive( dto.getIsActive() );
        }
        bolDetail.setCreatedBy( dto.getCreatedBy() );
        bolDetail.setCreatedDate( dto.getCreatedDate() );
        bolDetail.setIsValid( dto.getIsValid() );
        bolDetail.setModifiedBy( dto.getModifiedBy() );
        bolDetail.setModifiedDate( dto.getModifiedDate() );
        bolDetail.setBolDetailsId( dto.getBolDetailsId() );
        bolDetail.setChannelId( dto.getChannelId() );
        bolDetail.setImporterCode( dto.getImporterCode() );
        bolDetail.setShippingAgentCode( dto.getShippingAgentCode() );
        bolDetail.setShippingAgentName( dto.getShippingAgentName() );
        bolDetail.setVesselEta( dto.getVesselEta() );
        bolDetail.setVesselName( dto.getVesselName() );
        bolDetail.setVoyageNumber( dto.getVoyageNumber() );
        bolDetail.setContainerCount( dto.getContainerCount() );
        bolDetail.setConsigneeName( dto.getConsigneeName() );
        bolDetail.setVesselAta( dto.getVesselAta() );

        return bolDetail;
    }

    @Override
    public BoLDetailDTO boldetailDomaintoDTO(BolDetail destination) {
        if ( destination == null ) {
            return null;
        }

        BoLDetailDTO boLDetailDTO = new BoLDetailDTO();

        boLDetailDTO.setImporterCode( destination.getImporterCode() );
        boLDetailDTO.setShippingAgentCode( destination.getShippingAgentCode() );
        boLDetailDTO.setShippingAgentName( destination.getShippingAgentName() );
        boLDetailDTO.setVesselEta( destination.getVesselEta() );
        boLDetailDTO.setVesselName( destination.getVesselName() );
        boLDetailDTO.setVoyageNumber( destination.getVoyageNumber() );
        boLDetailDTO.setChannelId( destination.getChannelId() );
        boLDetailDTO.setCreatedBy( destination.getCreatedBy() );
        boLDetailDTO.setVesselAta( destination.getVesselAta() );
        boLDetailDTO.setConsigneeName( destination.getConsigneeName() );
        boLDetailDTO.setContainerCount( destination.getContainerCount() );
        boLDetailDTO.setBolDetailsId( destination.getBolDetailsId() );
        boLDetailDTO.setCreatedDate( destination.getCreatedDate() );
        boLDetailDTO.setIsValid( destination.getIsValid() );
        boLDetailDTO.setModifiedBy( destination.getModifiedBy() );
        boLDetailDTO.setModifiedDate( destination.getModifiedDate() );
        boLDetailDTO.setIsActive( destination.getIsActive() );

        return boLDetailDTO;
    }
}
