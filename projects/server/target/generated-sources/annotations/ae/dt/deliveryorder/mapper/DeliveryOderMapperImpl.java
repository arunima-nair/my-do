package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.DeliveryOrder;
import ae.dt.deliveryorder.dto.DeliveryOrderDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:26+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class DeliveryOderMapperImpl implements DeliveryOderMapper {

    @Override
    public DeliveryOrder dTOtoDoman(DeliveryOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DeliveryOrder deliveryOrder = new DeliveryOrder();

        deliveryOrder.setCreatedBy( dto.getCreatedBy() );
        deliveryOrder.setDeliveryOrderId( dto.getDeliveryOrderId() );
        deliveryOrder.setDocumentPath( dto.getDocumentPath() );

        return deliveryOrder;
    }

    @Override
    public DeliveryOrderDTO domaintoDTO(DeliveryOrder destination) {
        if ( destination == null ) {
            return null;
        }

        DeliveryOrderDTO deliveryOrderDTO = new DeliveryOrderDTO();

        deliveryOrderDTO.setDeliveryOrderId( destination.getDeliveryOrderId() );
        deliveryOrderDTO.setDocumentPath( destination.getDocumentPath() );
        deliveryOrderDTO.setCreatedBy( destination.getCreatedBy() );

        return deliveryOrderDTO;
    }
}
