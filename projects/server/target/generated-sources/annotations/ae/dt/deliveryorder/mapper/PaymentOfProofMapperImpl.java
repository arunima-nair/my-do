package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.dto.PaymentOfProofDTO;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class PaymentOfProofMapperImpl implements PaymentOfProofMapper {

    @Autowired
    private CollectionsMapper collectionsMapper;

    @Override
    public PaymentOfProof paymentOfProofdTOtoDoman(PaymentOfProofDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PaymentOfProof paymentOfProof = new PaymentOfProof();

        paymentOfProof.setCreatedBy( dto.getCreatedBy() );
        paymentOfProof.setPaymentOfProofId( dto.getPaymentOfProofId() );
        paymentOfProof.setPaymentAmount( dto.getPaymentAmount() );
        paymentOfProof.setReference( dto.getReference() );
        paymentOfProof.setRefNumber( dto.getRefNumber() );
        paymentOfProof.setCollection( collectionsMapper.collectiondTOtoDoman( dto.getCollection() ) );

        return paymentOfProof;
    }

    @Override
    public PaymentOfProofDTO paymentOfProofdomaintoDTO(PaymentOfProof destination) {
        if ( destination == null ) {
            return null;
        }

        PaymentOfProofDTO paymentOfProofDTO = new PaymentOfProofDTO();

        paymentOfProofDTO.setPaymentOfProofId( destination.getPaymentOfProofId() );
        paymentOfProofDTO.setPaymentAmount( destination.getPaymentAmount() );
        paymentOfProofDTO.setReference( destination.getReference() );
        paymentOfProofDTO.setRefNumber( destination.getRefNumber() );
        paymentOfProofDTO.setCreatedBy( destination.getCreatedBy() );

        return paymentOfProofDTO;
    }
}
