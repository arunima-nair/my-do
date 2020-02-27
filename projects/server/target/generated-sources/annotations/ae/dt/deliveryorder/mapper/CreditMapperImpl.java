package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.Credit;
import ae.dt.deliveryorder.dto.CreditDTO;
import java.math.BigDecimal;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class CreditMapperImpl implements CreditMapper {

    @Autowired
    private CollectionsMapper collectionsMapper;

    @Override
    public Credit creditdTOtoDoman(CreditDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Credit credit = new Credit();

        credit.setCreatedBy( dto.getCreatedBy() );
        if ( dto.getVersion() != null ) {
            credit.setVersion( dto.getVersion().longValue() );
        }
        credit.setCashId( dto.getCashId() );
        credit.setPaymentAmount( dto.getPaymentAmount() );
        credit.setReference( dto.getReference() );
        credit.setCollection( collectionsMapper.collectiondTOtoDoman( dto.getCollection() ) );

        return credit;
    }

    @Override
    public CreditDTO creditdomaintoDTO(Credit destination) {
        if ( destination == null ) {
            return null;
        }

        CreditDTO creditDTO = new CreditDTO();

        creditDTO.setCashId( destination.getCashId() );
        creditDTO.setPaymentAmount( destination.getPaymentAmount() );
        creditDTO.setReference( destination.getReference() );
        creditDTO.setVersion( BigDecimal.valueOf( destination.getVersion() ) );
        creditDTO.setCreatedBy( destination.getCreatedBy() );

        return creditDTO;
    }
}
