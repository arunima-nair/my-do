package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.RequestBolInvoice;
import ae.dt.deliveryorder.dto.RequestBolInvoiceDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:08+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class RequestBolInvoiceMapperImpl implements RequestBolInvoiceMapper {

    @Override
    public RequestBolInvoice requestBolInvoicedTOtoDoman(RequestBolInvoiceDTO requestBolInvoiceDTO) {
        if ( requestBolInvoiceDTO == null ) {
            return null;
        }

        RequestBolInvoice requestBolInvoice = new RequestBolInvoice();

        requestBolInvoice.setRequestBolInvoiceId( requestBolInvoiceDTO.getRequestBolInvoiceId() );
        requestBolInvoice.setCreatedBy( requestBolInvoiceDTO.getCreatedBy() );
        requestBolInvoice.setCreatedDate( requestBolInvoiceDTO.getCreatedDate() );
        requestBolInvoice.setBolNo( requestBolInvoiceDTO.getBolNo() );
        requestBolInvoice.setBolInvoiceNo( requestBolInvoiceDTO.getBolInvoiceNo() );
        requestBolInvoice.setShippingCode( requestBolInvoiceDTO.getShippingCode() );
        requestBolInvoice.setInitiatorCode( requestBolInvoiceDTO.getInitiatorCode() );
        requestBolInvoice.setShippingName( requestBolInvoiceDTO.getShippingName() );
        requestBolInvoice.setShippingType( requestBolInvoiceDTO.getShippingType() );

        return requestBolInvoice;
    }

    @Override
    public RequestBolInvoiceDTO requestBolInvoicedomaintoDTO(RequestBolInvoice destination) {
        if ( destination == null ) {
            return null;
        }

        RequestBolInvoiceDTO requestBolInvoiceDTO = new RequestBolInvoiceDTO();

        requestBolInvoiceDTO.setRequestBolInvoiceId( destination.getRequestBolInvoiceId() );
        requestBolInvoiceDTO.setCreatedBy( destination.getCreatedBy() );
        requestBolInvoiceDTO.setBolNo( destination.getBolNo() );
        requestBolInvoiceDTO.setBolInvoiceNo( destination.getBolInvoiceNo() );
        requestBolInvoiceDTO.setCreatedDate( destination.getCreatedDate() );
        requestBolInvoiceDTO.setShippingCode( destination.getShippingCode() );
        requestBolInvoiceDTO.setInitiatorCode( destination.getInitiatorCode() );
        requestBolInvoiceDTO.setShippingName( destination.getShippingName() );
        requestBolInvoiceDTO.setShippingType( destination.getShippingType() );

        return requestBolInvoiceDTO;
    }

    @Override
    public List<RequestBolInvoiceDTO> requestBolInvoicedomaintoDTO(List<RequestBolInvoice> content) {
        if ( content == null ) {
            return null;
        }

        List<RequestBolInvoiceDTO> list = new ArrayList<RequestBolInvoiceDTO>( content.size() );
        for ( RequestBolInvoice requestBolInvoice : content ) {
            list.add( requestBolInvoicedomaintoDTO( requestBolInvoice ) );
        }

        return list;
    }
}
