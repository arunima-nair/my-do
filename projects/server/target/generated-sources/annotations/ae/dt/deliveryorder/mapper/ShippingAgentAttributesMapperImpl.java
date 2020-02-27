package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.ShippingAgentAttributes;
import ae.dt.deliveryorder.dto.ShippingAgentAttributesDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:26+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class ShippingAgentAttributesMapperImpl implements ShippingAgentAttributesMapper {

    @Override
    public ShippingAgentAttributes shippingAgentAttributesDTOtoDoman(ShippingAgentAttributesDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ShippingAgentAttributes shippingAgentAttributes = new ShippingAgentAttributes();

        if ( dto.getIsActive() != null ) {
            shippingAgentAttributes.setIsActive( dto.getIsActive() );
        }
        shippingAgentAttributes.setCreatedDate( dto.getCreatedDate() );
        shippingAgentAttributes.setIsValid( dto.getIsValid() );
        shippingAgentAttributes.setModifiedBy( dto.getModifiedBy() );
        shippingAgentAttributes.setModifiedDate( dto.getModifiedDate() );
        shippingAgentAttributes.setShippingAgentAttributesId( dto.getShippingAgentAttributesId() );
        shippingAgentAttributes.setAttributeName( dto.getAttributeName() );
        shippingAgentAttributes.setAttributeValue( dto.getAttributeValue() );
        shippingAgentAttributes.setAttributeParam( dto.getAttributeParam() );

        return shippingAgentAttributes;
    }

    @Override
    public ShippingAgentAttributesDTO shippingAgentAttributesDomaintoDTO(ShippingAgentAttributes destination) {
        if ( destination == null ) {
            return null;
        }

        ShippingAgentAttributesDTO shippingAgentAttributesDTO = new ShippingAgentAttributesDTO();

        shippingAgentAttributesDTO.setShippingAgentAttributesId( destination.getShippingAgentAttributesId() );
        shippingAgentAttributesDTO.setAttributeName( destination.getAttributeName() );
        shippingAgentAttributesDTO.setAttributeValue( destination.getAttributeValue() );
        shippingAgentAttributesDTO.setAttributeParam( destination.getAttributeParam() );
        shippingAgentAttributesDTO.setCreatedDate( destination.getCreatedDate() );
        shippingAgentAttributesDTO.setIsValid( destination.getIsValid() );
        shippingAgentAttributesDTO.setModifiedBy( destination.getModifiedBy() );
        shippingAgentAttributesDTO.setModifiedDate( destination.getModifiedDate() );
        shippingAgentAttributesDTO.setIsActive( destination.getIsActive() );

        return shippingAgentAttributesDTO;
    }
}
