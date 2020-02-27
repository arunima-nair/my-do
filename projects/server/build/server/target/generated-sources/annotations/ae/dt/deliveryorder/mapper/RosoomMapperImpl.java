package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.Rosoom;
import ae.dt.deliveryorder.dto.RosoomDTO;
import java.math.BigDecimal;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:08+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class RosoomMapperImpl implements RosoomMapper {

    @Override
    public Rosoom rosoomdTOtoDoman(RosoomDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Rosoom rosoom = new Rosoom();

        rosoom.setRosoomId( dto.getRosoomId() );
        rosoom.setFiDate( dto.getFiDate() );
        rosoom.setFiInstrument( dto.getFiInstrument() );
        rosoom.setFiTransactionId( dto.getFiTransactionId() );
        rosoom.setGatewayTransactionId( dto.getGatewayTransactionId() );
        if ( dto.getPaymentAmount() != null ) {
            rosoom.setPaymentAmount( dto.getPaymentAmount().doubleValue() );
        }
        rosoom.setRemarks( dto.getRemarks() );

        return rosoom;
    }

    @Override
    public RosoomDTO rosoomdomaintoDTO(Rosoom destination) {
        if ( destination == null ) {
            return null;
        }

        RosoomDTO rosoomDTO = new RosoomDTO();

        rosoomDTO.setRosoomId( destination.getRosoomId() );
        rosoomDTO.setFiDate( destination.getFiDate() );
        rosoomDTO.setFiInstrument( destination.getFiInstrument() );
        rosoomDTO.setFiTransactionId( destination.getFiTransactionId() );
        rosoomDTO.setGatewayTransactionId( destination.getGatewayTransactionId() );
        if ( destination.getPaymentAmount() != null ) {
            rosoomDTO.setPaymentAmount( BigDecimal.valueOf( destination.getPaymentAmount() ) );
        }
        rosoomDTO.setRemarks( destination.getRemarks() );

        return rosoomDTO;
    }
}
