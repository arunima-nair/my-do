package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.CollectionDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:08+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class BolInvoiceMapperImpl implements BolInvoiceMapper {

    @Autowired
    private PaymentLogMapper paymentLogMapper;
    @Autowired
    private CollectionsMapper collectionsMapper;

    @Override
    public BolInvoice bolinvoiceDTOtoDoman(BolInvoiceDTO invdto) {
        if ( invdto == null ) {
            return null;
        }

        BolInvoice bolInvoice = new BolInvoice();

        if ( invdto.getIsActive() != null ) {
            bolInvoice.setIsActive( invdto.getIsActive() );
        }
        bolInvoice.setCreatedBy( invdto.getCreatedBy() );
        bolInvoice.setCreatedDate( invdto.getCreatedDate() );
        bolInvoice.setIsValid( invdto.getIsValid() );
        bolInvoice.setModifiedBy( invdto.getModifiedBy() );
        bolInvoice.setModifiedDate( invdto.getModifiedDate() );
        bolInvoice.setBolInvoiceId( invdto.getBolInvoiceId() );
        bolInvoice.setInvoiceDate( invdto.getInvoiceDate() );
        bolInvoice.setInvoiceNumber( invdto.getInvoiceNumber() );
        bolInvoice.setInvoiceValue( invdto.getInvoiceValue() );
        bolInvoice.setPath( invdto.getPath() );
        bolInvoice.setCurrency( invdto.getCurrency() );
        bolInvoice.setInvoiceValidityDate( invdto.getInvoiceValidityDate() );
        bolInvoice.setPaymentLogs( paymentLogMapper.paylogDTOtoDomanset( invdto.getPaymentLogs() ) );
        bolInvoice.setCollections( collectionDTOSetToCollectionSet( invdto.getCollections() ) );
        bolInvoice.setStatus( invdto.getStatus() );

        return bolInvoice;
    }

    @Override
    public BolInvoiceDTO bolinvoiceDomaintoDTO(BolInvoice destination) {
        if ( destination == null ) {
            return null;
        }

        BolInvoiceDTO bolInvoiceDTO = new BolInvoiceDTO();

        bolInvoiceDTO.setBolInvoiceId( destination.getBolInvoiceId() );
        bolInvoiceDTO.setInvoiceDate( destination.getInvoiceDate() );
        bolInvoiceDTO.setInvoiceNumber( destination.getInvoiceNumber() );
        bolInvoiceDTO.setInvoiceValue( destination.getInvoiceValue() );
        bolInvoiceDTO.setCreatedBy( destination.getCreatedBy() );
        bolInvoiceDTO.setPath( destination.getPath() );
        bolInvoiceDTO.setInvoiceValidityDate( destination.getInvoiceValidityDate() );
        bolInvoiceDTO.setPaymentLogs( paymentLogMapper.paylogDomaintoDTOset( destination.getPaymentLogs() ) );
        bolInvoiceDTO.setCollections( collectionSetToCollectionDTOSet( destination.getCollections() ) );
        bolInvoiceDTO.setCurrency( destination.getCurrency() );
        bolInvoiceDTO.setCreatedDate( destination.getCreatedDate() );
        bolInvoiceDTO.setIsValid( destination.getIsValid() );
        bolInvoiceDTO.setModifiedBy( destination.getModifiedBy() );
        bolInvoiceDTO.setModifiedDate( destination.getModifiedDate() );
        bolInvoiceDTO.setIsActive( destination.getIsActive() );
        bolInvoiceDTO.setStatus( destination.getStatus() );

        return bolInvoiceDTO;
    }

    protected Set<Collection> collectionDTOSetToCollectionSet(Set<CollectionDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Collection> set1 = new HashSet<Collection>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( CollectionDTO collectionDTO : set ) {
            set1.add( collectionsMapper.collectiondTOtoDoman( collectionDTO ) );
        }

        return set1;
    }

    protected Set<CollectionDTO> collectionSetToCollectionDTOSet(Set<Collection> set) {
        if ( set == null ) {
            return null;
        }

        Set<CollectionDTO> set1 = new HashSet<CollectionDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Collection collection : set ) {
            set1.add( collectionsMapper.collectiondomaintoDTO( collection ) );
        }

        return set1;
    }
}
