package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
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
public class PaymentLogMapperImpl implements PaymentLogMapper {

    @Override
    public PaymentLog paylogdTOtoDoman(PaymentLogDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentLog paymentLog = new PaymentLog();

        paymentLog.setPaymentLogId( dto.getPaymentLogId() );
        paymentLog.setAmount( dto.getAmount() );
        paymentLog.setStatus( dto.getStatus() );
        paymentLog.setTransactionId( dto.getTransactionId() );
        paymentLog.setPayStatus( dto.getPayStatus() );
        paymentLog.setCreatedBy( dto.getCreatedBy() );
        paymentLog.setPaidBy( dto.getPaidBy() );
        paymentLog.setInitiatorId( dto.getInitiatorId() );
        paymentLog.setInitiatedBy( dto.getInitiatedBy() );

        return paymentLog;
    }

    @Override
    public PaymentLogDTO paylogdomaintoDTO(PaymentLog destination) {
        if ( destination == null ) {
            return null;
        }

        PaymentLogDTO paymentLogDTO = new PaymentLogDTO();

        paymentLogDTO.setPaymentLogId( destination.getPaymentLogId() );
        paymentLogDTO.setAmount( destination.getAmount() );
        paymentLogDTO.setStatus( destination.getStatus() );
        paymentLogDTO.setTransactionId( destination.getTransactionId() );
        paymentLogDTO.setCreatedBy( destination.getCreatedBy() );
        paymentLogDTO.setPaidBy( destination.getPaidBy() );
        paymentLogDTO.setPayStatus( destination.getPayStatus() );
        paymentLogDTO.setInitiatorId( destination.getInitiatorId() );
        paymentLogDTO.setInitiatedBy( destination.getInitiatedBy() );

        return paymentLogDTO;
    }

    @Override
    public Set<PaymentLogDTO> paylogDomaintoDTOset(Set<PaymentLog> paylogDTOset) {
        if ( paylogDTOset == null ) {
            return null;
        }

        Set<PaymentLogDTO> set = new HashSet<PaymentLogDTO>( Math.max( (int) ( paylogDTOset.size() / .75f ) + 1, 16 ) );
        for ( PaymentLog paymentLog : paylogDTOset ) {
            set.add( paylogdomaintoDTO( paymentLog ) );
        }

        return set;
    }

    @Override
    public Set<PaymentLog> paylogDTOtoDomanset(Set<PaymentLogDTO> paylogDTOset) {
        if ( paylogDTOset == null ) {
            return null;
        }

        Set<PaymentLog> set = new HashSet<PaymentLog>( Math.max( (int) ( paylogDTOset.size() / .75f ) + 1, 16 ) );
        for ( PaymentLogDTO paymentLogDTO : paylogDTOset ) {
            set.add( paylogdTOtoDoman( paymentLogDTO ) );
        }

        return set;
    }
}
