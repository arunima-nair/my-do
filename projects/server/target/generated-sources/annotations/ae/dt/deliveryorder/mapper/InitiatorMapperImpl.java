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
    date = "2020-02-27T11:35:26+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class InitiatorMapperImpl implements InitiatorMapper {

    @Override
    public Initiator initiatorDTOtoDoman(InitiatorDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Initiator initiator = new Initiator();

        if ( dto.getIsActive() != null ) {
            initiator.setIsActive( dto.getIsActive() );
        }
        initiator.setCreatedDate( dto.getCreatedDate() );
        initiator.setIsValid( dto.getIsValid() );
        initiator.setModifiedBy( dto.getModifiedBy() );
        initiator.setModifiedDate( dto.getModifiedDate() );
        initiator.setInitiatorId( dto.getInitiatorId() );
        initiator.setInitiatorName( dto.getInitiatorName() );
        initiator.setInitiatorType( dto.getInitiatorType() );
        initiator.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( dto.getSaInitiatorInvoiceType() ) );

        return initiator;
    }

    @Override
    public InitiatorDTO InitiatorDomaintoDTO(Initiator destination) {
        if ( destination == null ) {
            return null;
        }

        InitiatorDTO initiatorDTO = new InitiatorDTO();

        initiatorDTO.setInitiatorId( destination.getInitiatorId() );
        initiatorDTO.setInitiatorName( destination.getInitiatorName() );
        initiatorDTO.setInitiatorType( destination.getInitiatorType() );
        initiatorDTO.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet( destination.getSaInitiatorInvoiceType() ) );
        initiatorDTO.setCreatedDate( destination.getCreatedDate() );
        initiatorDTO.setIsValid( destination.getIsValid() );
        initiatorDTO.setModifiedBy( destination.getModifiedBy() );
        initiatorDTO.setModifiedDate( destination.getModifiedDate() );
        initiatorDTO.setIsActive( destination.getIsActive() );

        return initiatorDTO;
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
        shippingAgentAttributes.setShippingAgent( shippingAgentDTOToShippingAgent( shippingAgentAttributesDTO.getShippingAgent() ) );

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

    protected ShippingAgent shippingAgentDTOToShippingAgent(ShippingAgentDTO shippingAgentDTO) {
        if ( shippingAgentDTO == null ) {
            return null;
        }

        ShippingAgent shippingAgent = new ShippingAgent();

        if ( shippingAgentDTO.getIsActive() != null ) {
            shippingAgent.setIsActive( shippingAgentDTO.getIsActive() );
        }
        shippingAgent.setCreatedDate( shippingAgentDTO.getCreatedDate() );
        shippingAgent.setIsValid( shippingAgentDTO.getIsValid() );
        shippingAgent.setModifiedBy( shippingAgentDTO.getModifiedBy() );
        shippingAgent.setModifiedDate( shippingAgentDTO.getModifiedDate() );
        shippingAgent.setShippingAgentId( shippingAgentDTO.getShippingAgentId() );
        shippingAgent.setShippingAgentCode( shippingAgentDTO.getShippingAgentCode() );
        shippingAgent.setShippingAgentName( shippingAgentDTO.getShippingAgentName() );
        shippingAgent.setShippingAgentAttributes( shippingAgentAttributesDTOSetToShippingAgentAttributesSet( shippingAgentDTO.getShippingAgentAttributes() ) );
        shippingAgent.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( shippingAgentDTO.getSaInitiatorInvoiceType() ) );

        return shippingAgent;
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
        sAInitiatorInvoiceType.setShippingAgent( shippingAgentDTOToShippingAgent( sAInitiatorInvoiceTypeDTO.getShippingAgent() ) );
        sAInitiatorInvoiceType.setInvoiceType( sAInitiatorInvoiceTypeDTO.getInvoiceType() );
        sAInitiatorInvoiceType.setInitiator( initiatorDTOtoDoman( sAInitiatorInvoiceTypeDTO.getInitiator() ) );

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
        shippingAgentAttributesDTO.setShippingAgent( shippingAgentToShippingAgentDTO( shippingAgentAttributes.getShippingAgent() ) );
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

    protected ShippingAgentDTO shippingAgentToShippingAgentDTO(ShippingAgent shippingAgent) {
        if ( shippingAgent == null ) {
            return null;
        }

        ShippingAgentDTO shippingAgentDTO = new ShippingAgentDTO();

        shippingAgentDTO.setShippingAgentId( shippingAgent.getShippingAgentId() );
        shippingAgentDTO.setShippingAgentCode( shippingAgent.getShippingAgentCode() );
        shippingAgentDTO.setShippingAgentName( shippingAgent.getShippingAgentName() );
        shippingAgentDTO.setShippingAgentAttributes( shippingAgentAttributesSetToShippingAgentAttributesDTOSet( shippingAgent.getShippingAgentAttributes() ) );
        shippingAgentDTO.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet( shippingAgent.getSaInitiatorInvoiceType() ) );
        shippingAgentDTO.setCreatedDate( shippingAgent.getCreatedDate() );
        shippingAgentDTO.setIsValid( shippingAgent.getIsValid() );
        shippingAgentDTO.setModifiedBy( shippingAgent.getModifiedBy() );
        shippingAgentDTO.setModifiedDate( shippingAgent.getModifiedDate() );
        shippingAgentDTO.setIsActive( shippingAgent.getIsActive() );

        return shippingAgentDTO;
    }

    protected SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeToSAInitiatorInvoiceTypeDTO(SAInitiatorInvoiceType sAInitiatorInvoiceType) {
        if ( sAInitiatorInvoiceType == null ) {
            return null;
        }

        SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO = new SAInitiatorInvoiceTypeDTO();

        sAInitiatorInvoiceTypeDTO.setSaInitiatorInvoiceTypeId( sAInitiatorInvoiceType.getSaInitiatorInvoiceTypeId() );
        sAInitiatorInvoiceTypeDTO.setShippingAgent( shippingAgentToShippingAgentDTO( sAInitiatorInvoiceType.getShippingAgent() ) );
        sAInitiatorInvoiceTypeDTO.setInvoiceType( sAInitiatorInvoiceType.getInvoiceType() );
        sAInitiatorInvoiceTypeDTO.setInitiator( InitiatorDomaintoDTO( sAInitiatorInvoiceType.getInitiator() ) );
        sAInitiatorInvoiceTypeDTO.setCreatedDate( sAInitiatorInvoiceType.getCreatedDate() );
        sAInitiatorInvoiceTypeDTO.setIsValid( sAInitiatorInvoiceType.getIsValid() );
        sAInitiatorInvoiceTypeDTO.setModifiedBy( sAInitiatorInvoiceType.getModifiedBy() );
        sAInitiatorInvoiceTypeDTO.setModifiedDate( sAInitiatorInvoiceType.getModifiedDate() );
        sAInitiatorInvoiceTypeDTO.setIsActive( sAInitiatorInvoiceType.getIsActive() );

        return sAInitiatorInvoiceTypeDTO;
    }
}
