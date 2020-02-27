package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.SARosoom;
import ae.dt.deliveryorder.dto.SARosoomDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:06+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class SARosoomMapperImpl implements SARosoomMapper {

    @Override
    public SARosoom saRosoomDTOtoDoman(SARosoomDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SARosoom sARosoom = new SARosoom();

        if ( dto.getIsActive() != null ) {
            sARosoom.setIsActive( dto.getIsActive() );
        }
        sARosoom.setCreatedDate( dto.getCreatedDate() );
        sARosoom.setIsValid( dto.getIsValid() );
        sARosoom.setModifiedBy( dto.getModifiedBy() );
        sARosoom.setModifiedDate( dto.getModifiedDate() );
        sARosoom.setSaRosoomId( dto.getSaRosoomId() );
        sARosoom.setCurrency( dto.getCurrency() );
        sARosoom.setServiceOwnerId( dto.getServiceOwnerId() );
        sARosoom.setRosoomLicenseKey( dto.getRosoomLicenseKey() );
        sARosoom.setRosoomServiceId( dto.getRosoomServiceId() );
        sARosoom.setKeyStoreLoc( dto.getKeyStoreLoc() );
        sARosoom.setKeyStorePassword( dto.getKeyStorePassword() );
        sARosoom.setKeyPassword( dto.getKeyPassword() );
        sARosoom.setKeyAlias( dto.getKeyAlias() );

        return sARosoom;
    }

    @Override
    public SARosoomDTO saRosoomDomaintoDTO(SARosoom destination) {
        if ( destination == null ) {
            return null;
        }

        SARosoomDTO sARosoomDTO = new SARosoomDTO();

        sARosoomDTO.setSaRosoomId( destination.getSaRosoomId() );
        sARosoomDTO.setCurrency( destination.getCurrency() );
        sARosoomDTO.setServiceOwnerId( destination.getServiceOwnerId() );
        sARosoomDTO.setRosoomLicenseKey( destination.getRosoomLicenseKey() );
        sARosoomDTO.setRosoomServiceId( destination.getRosoomServiceId() );
        sARosoomDTO.setCreatedDate( destination.getCreatedDate() );
        sARosoomDTO.setIsValid( destination.getIsValid() );
        sARosoomDTO.setModifiedBy( destination.getModifiedBy() );
        sARosoomDTO.setModifiedDate( destination.getModifiedDate() );
        sARosoomDTO.setIsActive( destination.getIsActive() );
        sARosoomDTO.setKeyStoreLoc( destination.getKeyStoreLoc() );
        sARosoomDTO.setKeyStorePassword( destination.getKeyStorePassword() );
        sARosoomDTO.setKeyPassword( destination.getKeyPassword() );
        sARosoomDTO.setKeyAlias( destination.getKeyAlias() );

        return sARosoomDTO;
    }
}
