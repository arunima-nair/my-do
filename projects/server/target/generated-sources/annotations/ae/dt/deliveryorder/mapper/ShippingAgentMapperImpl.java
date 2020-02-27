package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.Initiator;
import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;
import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.domain.ShippingAgentAttributes;
import ae.dt.deliveryorder.dto.InitiatorDTO;
import ae.dt.deliveryorder.dto.SAInitiatorInvoiceTypeDTO;
import ae.dt.deliveryorder.dto.ShippingAgentAttributesDTO;
import ae.dt.deliveryorder.dto.ShippingAgentDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class ShippingAgentMapperImpl implements ShippingAgentMapper {

    @Override
    public ShippingAgent shippingAgentDTOtoDoman(ShippingAgentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ShippingAgent shippingAgent = new ShippingAgent();

        if ( dto.getIsActive() != null ) {
            shippingAgent.setIsActive( dto.getIsActive() );
        }
        shippingAgent.setCreatedDate( dto.getCreatedDate() );
        shippingAgent.setIsValid( dto.getIsValid() );
        shippingAgent.setModifiedBy( dto.getModifiedBy() );
        shippingAgent.setModifiedDate( dto.getModifiedDate() );
        shippingAgent.setShippingAgentId( dto.getShippingAgentId() );
        shippingAgent.setShippingAgentCode( dto.getShippingAgentCode() );
        shippingAgent.setShippingAgentName( dto.getShippingAgentName() );
        shippingAgent.setShippingAgentAttributes( shippingAgentAttributesDTOSetToShippingAgentAttributesSet( dto.getShippingAgentAttributes() ) );
        shippingAgent.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( dto.getSaInitiatorInvoiceType() ) );

        return shippingAgent;
    }

    @Override
    public ShippingAgentDTO shippingAgentDomaintoDTO(ShippingAgent destination) {
        if ( destination == null ) {
            return null;
        }

        ShippingAgentDTO shippingAgentDTO = new ShippingAgentDTO();

        shippingAgentDTO.setShippingAgentId( destination.getShippingAgentId() );
        shippingAgentDTO.setShippingAgentCode( destination.getShippingAgentCode() );
        shippingAgentDTO.setShippingAgentName( destination.getShippingAgentName() );
        shippingAgentDTO.setShippingAgentAttributes( shippingAgentAttributesSetToShippingAgentAttributesDTOSet( destination.getShippingAgentAttributes() ) );
        shippingAgentDTO.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet( destination.getSaInitiatorInvoiceType() ) );
        shippingAgentDTO.setCreatedDate( destination.getCreatedDate() );
        shippingAgentDTO.setIsValid( destination.getIsValid() );
        shippingAgentDTO.setModifiedBy( destination.getModifiedBy() );
        shippingAgentDTO.setModifiedDate( destination.getModifiedDate() );
        shippingAgentDTO.setIsActive( destination.getIsActive() );

        return shippingAgentDTO;
    }

    protected ShippingAgentAttributes shippingAgentAttributesDTOToShippingAgentAttributes(ShippingAgentAttributesDTO shippingAgentAttributesDTO) {
        if ( shippingAgentAttributesDTO == null ) {
            return null;
        }

        ShippingAgentAttributes shippingAgentAttributes = new ShippingAgentAttributes();

        if ( shippingAgentAttributesDTO.getIsActive() != null ) {
            shippingAgentAttributes.setIsActive( shippingAgentAttributesDTO.getIsActive() );
        }
        shippingAgentAttributes.setCreatedDate( shippingAgentAttributesDTO.getCreatedDate() );
        shippingAgentAttributes.setIsValid( shippingAgentAttributesDTO.getIsValid() );
        shippingAgentAttributes.setModifiedBy( shippingAgentAttributesDTO.getModifiedBy() );
        shippingAgentAttributes.setModifiedDate( shippingAgentAttributesDTO.getModifiedDate() );
        shippingAgentAttributes.setShippingAgentAttributesId( shippingAgentAttributesDTO.getShippingAgentAttributesId() );
        shippingAgentAttributes.setAttributeName( shippingAgentAttributesDTO.getAttributeName() );
        shippingAgentAttributes.setAttributeValue( shippingAgentAttributesDTO.getAttributeValue() );
        shippingAgentAttributes.setAttributeParam( shippingAgentAttributesDTO.getAttributeParam() );
        shippingAgentAttributes.setShippingAgent( shippingAgentDTOtoDoman( shippingAgentAttributesDTO.getShippingAgent() ) );

        return shippingAgentAttributes;
    }

    protected Set<ShippingAgentAttributes> shippingAgentAttributesDTOSetToShippingAgentAttributesSet(Set<ShippingAgentAttributesDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<ShippingAgentAttributes> set1 = new HashSet<ShippingAgentAttributes>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ShippingAgentAttributesDTO shippingAgentAttributesDTO : set ) {
            set1.add( shippingAgentAttributesDTOToShippingAgentAttributes( shippingAgentAttributesDTO ) );
        }

        return set1;
    }

    protected Set<SAInitiatorInvoiceType> sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet(Set<SAInitiatorInvoiceTypeDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<SAInitiatorInvoiceType> set1 = new HashSet<SAInitiatorInvoiceType>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO : set ) {
            set1.add( sAInitiatorInvoiceTypeDTOToSAInitiatorInvoiceType( sAInitiatorInvoiceTypeDTO ) );
        }

        return set1;
    }

    protected Initiator initiatorDTOToInitiator(InitiatorDTO initiatorDTO) {
        if ( initiatorDTO == null ) {
            return null;
        }

        Initiator initiator = new Initiator();

        if ( initiatorDTO.getIsActive() != null ) {
            initiator.setIsActive( initiatorDTO.getIsActive() );
        }
        initiator.setCreatedDate( initiatorDTO.getCreatedDate() );
        initiator.setIsValid( initiatorDTO.getIsValid() );
        initiator.setModifiedBy( initiatorDTO.getModifiedBy() );
        initiator.setModifiedDate( initiatorDTO.getModifiedDate() );
        initiator.setInitiatorId( initiatorDTO.getInitiatorId() );
        initiator.setInitiatorName( initiatorDTO.getInitiatorName() );
        initiator.setInitiatorType( initiatorDTO.getInitiatorType() );
        initiator.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( initiatorDTO.getSaInitiatorInvoiceType() ) );

        return initiator;
    }

    protected SAInitiatorInvoiceType sAInitiatorInvoiceTypeDTOToSAInitiatorInvoiceType(SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO) {
        if ( sAInitiatorInvoiceTypeDTO == null ) {
            return null;
        }

        SAInitiatorInvoiceType sAInitiatorInvoiceType = new SAInitiatorInvoiceType();

        if ( sAInitiatorInvoiceTypeDTO.getIsActive() != null ) {
            sAInitiatorInvoiceType.setIsActive( sAInitiatorInvoiceTypeDTO.getIsActive() );
        }
        sAInitiatorInvoiceType.setCreatedDate( sAInitiatorInvoiceTypeDTO.getCreatedDate() );
        sAInitiatorInvoiceType.setIsValid( sAInitiatorInvoiceTypeDTO.getIsValid() );
        sAInitiatorInvoiceType.setModifiedBy( sAInitiatorInvoiceTypeDTO.getModifiedBy() );
        sAInitiatorInvoiceType.setModifiedDate( sAInitiatorInvoiceTypeDTO.getModifiedDate() );
        sAInitiatorInvoiceType.setSaInitiatorInvoiceTypeId( sAInitiatorInvoiceTypeDTO.getSaInitiatorInvoiceTypeId() );
        sAInitiatorInvoiceType.setShippingAgent( shippingAgentDTOtoDoman( sAInitiatorInvoiceTypeDTO.getShippingAgent() ) );
        sAInitiatorInvoiceType.setInvoiceType( sAInitiatorInvoiceTypeDTO.getInvoiceType() );
        sAInitiatorInvoiceType.setInitiator( initiatorDTOToInitiator( sAInitiatorInvoiceTypeDTO.getInitiator() ) );

        return sAInitiatorInvoiceType;
    }

    protected ShippingAgentAttributesDTO shippingAgentAttributesToShippingAgentAttributesDTO(ShippingAgentAttributes shippingAgentAttributes) {
        if ( shippingAgentAttributes == null ) {
            return null;
        }

        ShippingAgentAttributesDTO shippingAgentAttributesDTO = new ShippingAgentAttributesDTO();

        shippingAgentAttributesDTO.setShippingAgentAttributesId( shippingAgentAttributes.getShippingAgentAttributesId() );
        shippingAgentAttributesDTO.setAttributeName( shippingAgentAttributes.getAttributeName() );
        shippingAgentAttributesDTO.setAttributeValue( shippingAgentAttributes.getAttributeValue() );
        shippingAgentAttributesDTO.setAttributeParam( shippingAgentAttributes.getAttributeParam() );
        shippingAgentAttributesDTO.setShippingAgent( shippingAgentDomaintoDTO( shippingAgentAttributes.getShippingAgent() ) );
        shippingAgentAttributesDTO.setCreatedDate( shippingAgentAttributes.getCreatedDate() );
        shippingAgentAttributesDTO.setIsValid( shippingAgentAttributes.getIsValid() );
        shippingAgentAttributesDTO.setModifiedBy( shippingAgentAttributes.getModifiedBy() );
        shippingAgentAttributesDTO.setModifiedDate( shippingAgentAttributes.getModifiedDate() );
        shippingAgentAttributesDTO.setIsActive( shippingAgentAttributes.getIsActive() );

        return shippingAgentAttributesDTO;
    }

    protected Set<ShippingAgentAttributesDTO> shippingAgentAttributesSetToShippingAgentAttributesDTOSet(Set<ShippingAgentAttributes> set) {
        if ( set == null ) {
            return null;
        }

        Set<ShippingAgentAttributesDTO> set1 = new HashSet<ShippingAgentAttributesDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ShippingAgentAttributes shippingAgentAttributes : set ) {
            set1.add( shippingAgentAttributesToShippingAgentAttributesDTO( shippingAgentAttributes ) );
        }

        return set1;
    }

    protected Set<SAInitiatorInvoiceTypeDTO> sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet(Set<SAInitiatorInvoiceType> set) {
        if ( set == null ) {
            return null;
        }

        Set<SAInitiatorInvoiceTypeDTO> set1 = new HashSet<SAInitiatorInvoiceTypeDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SAInitiatorInvoiceType sAInitiatorInvoiceType : set ) {
            set1.add( sAInitiatorInvoiceTypeToSAInitiatorInvoiceTypeDTO( sAInitiatorInvoiceType ) );
        }

        return set1;
    }

    protected InitiatorDTO initiatorToInitiatorDTO(Initiator initiator) {
        if ( initiator == null ) {
            return null;
        }

        InitiatorDTO initiatorDTO = new InitiatorDTO();

        initiatorDTO.setInitiatorId( initiator.getInitiatorId() );
        initiatorDTO.setInitiatorName( initiator.getInitiatorName() );
        initiatorDTO.setInitiatorType( initiator.getInitiatorType() );
        initiatorDTO.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet( initiator.getSaInitiatorInvoiceType() ) );
        initiatorDTO.setCreatedDate( initiator.getCreatedDate() );
        initiatorDTO.setIsValid( initiator.getIsValid() );
        initiatorDTO.setModifiedBy( initiator.getModifiedBy() );
        initiatorDTO.setModifiedDate( initiator.getModifiedDate() );
        initiatorDTO.setIsActive( initiator.getIsActive() );

        return initiatorDTO;
    }

    protected SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeToSAInitiatorInvoiceTypeDTO(SAInitiatorInvoiceType sAInitiatorInvoiceType) {
        if ( sAInitiatorInvoiceType == null ) {
            return null;
        }

        SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO = new SAInitiatorInvoiceTypeDTO();

        sAInitiatorInvoiceTypeDTO.setSaInitiatorInvoiceTypeId( sAInitiatorInvoiceType.getSaInitiatorInvoiceTypeId() );
        sAInitiatorInvoiceTypeDTO.setShippingAgent( shippingAgentDomaintoDTO( sAInitiatorInvoiceType.getShippingAgent() ) );
        sAInitiatorInvoiceTypeDTO.setInvoiceType( sAInitiatorInvoiceType.getInvoiceType() );
        sAInitiatorInvoiceTypeDTO.setInitiator( initiatorToInitiatorDTO( sAInitiatorInvoiceType.getInitiator() ) );
        sAInitiatorInvoiceTypeDTO.setCreatedDate( sAInitiatorInvoiceType.getCreatedDate() );
        sAInitiatorInvoiceTypeDTO.setIsValid( sAInitiatorInvoiceType.getIsValid() );
        sAInitiatorInvoiceTypeDTO.setModifiedBy( sAInitiatorInvoiceType.getModifiedBy() );
        sAInitiatorInvoiceTypeDTO.setModifiedDate( sAInitiatorInvoiceType.getModifiedDate() );
        sAInitiatorInvoiceTypeDTO.setIsActive( sAInitiatorInvoiceType.getIsActive() );

        return sAInitiatorInvoiceTypeDTO;
    }
}
