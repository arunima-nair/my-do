package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;
import ae.dt.deliveryorder.dto.SAInitiatorInvoiceTypeDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:26+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class SAInitiatorInvoiceTypeMapperImpl implements SAInitiatorInvoiceTypeMapper {

    @Override
    public SAInitiatorInvoiceType importerInvoiceTypeDTOtoDoman(SAInitiatorInvoiceTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SAInitiatorInvoiceType sAInitiatorInvoiceType = new SAInitiatorInvoiceType();

        if ( dto.getIsActive() != null ) {
            sAInitiatorInvoiceType.setIsActive( dto.getIsActive() );
        }
        sAInitiatorInvoiceType.setCreatedDate( dto.getCreatedDate() );
        sAInitiatorInvoiceType.setIsValid( dto.getIsValid() );
        sAInitiatorInvoiceType.setModifiedBy( dto.getModifiedBy() );
        sAInitiatorInvoiceType.setModifiedDate( dto.getModifiedDate() );
        sAInitiatorInvoiceType.setSaInitiatorInvoiceTypeId( dto.getSaInitiatorInvoiceTypeId() );

        return sAInitiatorInvoiceType;
    }

    @Override
    public SAInitiatorInvoiceTypeDTO importerInvoiceTypeTypeDomaintoDTO(SAInitiatorInvoiceType destination) {
        if ( destination == null ) {
            return null;
        }

        SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO = new SAInitiatorInvoiceTypeDTO();

        sAInitiatorInvoiceTypeDTO.setSaInitiatorInvoiceTypeId( destination.getSaInitiatorInvoiceTypeId() );
        sAInitiatorInvoiceTypeDTO.setCreatedDate( destination.getCreatedDate() );
        sAInitiatorInvoiceTypeDTO.setIsValid( destination.getIsValid() );
        sAInitiatorInvoiceTypeDTO.setModifiedBy( destination.getModifiedBy() );
        sAInitiatorInvoiceTypeDTO.setModifiedDate( destination.getModifiedDate() );
        sAInitiatorInvoiceTypeDTO.setIsActive( destination.getIsActive() );

        return sAInitiatorInvoiceTypeDTO;
    }
}
