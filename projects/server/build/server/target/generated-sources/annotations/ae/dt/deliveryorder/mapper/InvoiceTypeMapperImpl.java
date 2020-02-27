package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.BolInvoice;
import ae.dt.deliveryorder.domain.InvoiceType;
import ae.dt.deliveryorder.domain.SAInitiatorInvoiceType;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.InvoiceTypeDTO;
import ae.dt.deliveryorder.dto.SAInitiatorInvoiceTypeDTO;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:09+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class InvoiceTypeMapperImpl implements InvoiceTypeMapper {

    @Autowired
    private SAInitiatorInvoiceTypeMapper sAInitiatorInvoiceTypeMapper;
    @Autowired
    private BolInvoiceMapper bolInvoiceMapper;

    @Override
    public InvoiceType invoiceTypeDTOtoDoman(InvoiceTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        InvoiceType invoiceType = new InvoiceType();

        if ( dto.getIsActive() != null ) {
            invoiceType.setIsActive( dto.getIsActive() );
        }
        invoiceType.setCreatedDate( dto.getCreatedDate() );
        invoiceType.setIsValid( dto.getIsValid() );
        invoiceType.setModifiedBy( dto.getModifiedBy() );
        invoiceType.setModifiedDate( dto.getModifiedDate() );
        invoiceType.setInvoiceTypeId( dto.getInvoiceTypeId() );
        invoiceType.setInvoiceTypeName( dto.getInvoiceTypeName() );
        invoiceType.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet( dto.getSaInitiatorInvoiceType() ) );
        invoiceType.setBolInvoice( bolInvoiceDTOSetToBolInvoiceSet( dto.getBolInvoice() ) );

        return invoiceType;
    }

    @Override
    public InvoiceTypeDTO invoiceTypeDomaintoDTO(InvoiceType destination) {
        if ( destination == null ) {
            return null;
        }

        InvoiceTypeDTO invoiceTypeDTO = new InvoiceTypeDTO();

        invoiceTypeDTO.setInvoiceTypeId( destination.getInvoiceTypeId() );
        invoiceTypeDTO.setInvoiceTypeName( destination.getInvoiceTypeName() );
        invoiceTypeDTO.setSaInitiatorInvoiceType( sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet( destination.getSaInitiatorInvoiceType() ) );
        invoiceTypeDTO.setBolInvoice( bolInvoiceSetToBolInvoiceDTOSet( destination.getBolInvoice() ) );
        invoiceTypeDTO.setCreatedDate( destination.getCreatedDate() );
        invoiceTypeDTO.setIsValid( destination.getIsValid() );
        invoiceTypeDTO.setModifiedBy( destination.getModifiedBy() );
        invoiceTypeDTO.setModifiedDate( destination.getModifiedDate() );
        invoiceTypeDTO.setIsActive( destination.getIsActive() );

        return invoiceTypeDTO;
    }

    protected Set<SAInitiatorInvoiceType> sAInitiatorInvoiceTypeDTOSetToSAInitiatorInvoiceTypeSet(Set<SAInitiatorInvoiceTypeDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<SAInitiatorInvoiceType> set1 = new HashSet<SAInitiatorInvoiceType>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SAInitiatorInvoiceTypeDTO sAInitiatorInvoiceTypeDTO : set ) {
            set1.add( sAInitiatorInvoiceTypeMapper.importerInvoiceTypeDTOtoDoman( sAInitiatorInvoiceTypeDTO ) );
        }

        return set1;
    }

    protected Set<BolInvoice> bolInvoiceDTOSetToBolInvoiceSet(Set<BolInvoiceDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolInvoice> set1 = new HashSet<BolInvoice>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolInvoiceDTO bolInvoiceDTO : set ) {
            set1.add( bolInvoiceMapper.bolinvoiceDTOtoDoman( bolInvoiceDTO ) );
        }

        return set1;
    }

    protected Set<SAInitiatorInvoiceTypeDTO> sAInitiatorInvoiceTypeSetToSAInitiatorInvoiceTypeDTOSet(Set<SAInitiatorInvoiceType> set) {
        if ( set == null ) {
            return null;
        }

        Set<SAInitiatorInvoiceTypeDTO> set1 = new HashSet<SAInitiatorInvoiceTypeDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( SAInitiatorInvoiceType sAInitiatorInvoiceType : set ) {
            set1.add( sAInitiatorInvoiceTypeMapper.importerInvoiceTypeTypeDomaintoDTO( sAInitiatorInvoiceType ) );
        }

        return set1;
    }

    protected Set<BolInvoiceDTO> bolInvoiceSetToBolInvoiceDTOSet(Set<BolInvoice> set) {
        if ( set == null ) {
            return null;
        }

        Set<BolInvoiceDTO> set1 = new HashSet<BolInvoiceDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( BolInvoice bolInvoice : set ) {
            set1.add( bolInvoiceMapper.bolinvoiceDomaintoDTO( bolInvoice ) );
        }

        return set1;
    }
}
