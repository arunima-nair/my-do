package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.SAInitiatorPayment;
import ae.dt.deliveryorder.dto.SAInitiatorPaymentDTO;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class SAInitiatorPaymentMapperImpl implements SAInitiatorPaymentMapper {

    @Autowired
    private ShippingAgentMapper shippingAgentMapper;
    @Autowired
    private InitiatorMapper initiatorMapper;

    @Override
    public SAInitiatorPayment saInitiatorPaymentDTOtoDoman(SAInitiatorPaymentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SAInitiatorPayment sAInitiatorPayment = new SAInitiatorPayment();

        if ( dto.getIsActive() != null ) {
            sAInitiatorPayment.setIsActive( dto.getIsActive() );
        }
        sAInitiatorPayment.setCreatedDate( dto.getCreatedDate() );
        sAInitiatorPayment.setIsValid( dto.getIsValid() );
        sAInitiatorPayment.setModifiedBy( dto.getModifiedBy() );
        sAInitiatorPayment.setModifiedDate( dto.getModifiedDate() );
        sAInitiatorPayment.setSaInitiatorPaymentId( dto.getSaInitiatorPaymentId() );
        sAInitiatorPayment.setPaymentAllowed( dto.getPaymentAllowed() );
        sAInitiatorPayment.setShippingAgent( shippingAgentMapper.shippingAgentDTOtoDoman( dto.getShippingAgent() ) );
        sAInitiatorPayment.setInitiator( initiatorMapper.initiatorDTOtoDoman( dto.getInitiator() ) );

        return sAInitiatorPayment;
    }

    @Override
    public SAInitiatorPaymentDTO saInitiatorPaymentDomaintoDTO(SAInitiatorPayment destination) {
        if ( destination == null ) {
            return null;
        }

        SAInitiatorPaymentDTO sAInitiatorPaymentDTO = new SAInitiatorPaymentDTO();

        sAInitiatorPaymentDTO.setSaInitiatorPaymentId( destination.getSaInitiatorPaymentId() );
        sAInitiatorPaymentDTO.setShippingAgent( shippingAgentMapper.shippingAgentDomaintoDTO( destination.getShippingAgent() ) );
        sAInitiatorPaymentDTO.setInitiator( initiatorMapper.InitiatorDomaintoDTO( destination.getInitiator() ) );
        sAInitiatorPaymentDTO.setPaymentAllowed( destination.getPaymentAllowed() );
        sAInitiatorPaymentDTO.setCreatedDate( destination.getCreatedDate() );
        sAInitiatorPaymentDTO.setIsValid( destination.getIsValid() );
        sAInitiatorPaymentDTO.setModifiedBy( destination.getModifiedBy() );
        sAInitiatorPaymentDTO.setModifiedDate( destination.getModifiedDate() );
        sAInitiatorPaymentDTO.setIsActive( destination.getIsActive() );

        return sAInitiatorPaymentDTO;
    }
}
