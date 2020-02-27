package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.Document;
import ae.dt.deliveryorder.dto.DocumentDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:26+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class DocumentMapperImpl implements DocumentMapper {

    @Override
    public Document dTOtoDoman(DocumentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Document document = new Document();

        document.setDocumentId( dto.getDocumentId() );
        document.setDocumentValue( dto.getDocumentValue() );

        return document;
    }

    @Override
    public DocumentDTO domaintoDTO(Document destination) {
        if ( destination == null ) {
            return null;
        }

        DocumentDTO documentDTO = new DocumentDTO();

        documentDTO.setDocumentId( destination.getDocumentId() );
        documentDTO.setDocumentValue( destination.getDocumentValue() );

        return documentDTO;
    }
}
