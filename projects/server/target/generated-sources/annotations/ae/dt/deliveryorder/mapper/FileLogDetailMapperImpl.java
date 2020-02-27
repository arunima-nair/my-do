package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.dto.FileLogDetailsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:26+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class FileLogDetailMapperImpl implements FileLogDetailMapper {

    @Override
    public FileLogDetail fileLogDTOtoEntity(FileLogDetailsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FileLogDetail fileLogDetail = new FileLogDetail();

        if ( dto.getIsActive() != null ) {
            fileLogDetail.setIsActive( Long.parseLong( dto.getIsActive() ) );
        }
        fileLogDetail.setCreatedBy( dto.getCreatedBy() );
        fileLogDetail.setCreatedDate( dto.getCreatedDate() );
        if ( dto.getIsValid() != null ) {
            fileLogDetail.setIsValid( Long.parseLong( dto.getIsValid() ) );
        }
        fileLogDetail.setFileLogDetailsId( dto.getFileLogDetailsId() );
        fileLogDetail.setIsProcessed( dto.getIsProcessed() );
        fileLogDetail.setErrorCode( dto.getErrorCode() );
        fileLogDetail.setBolNumber( dto.getBolNumber() );
        fileLogDetail.setInvoiceNumber( dto.getInvoiceNumber() );

        return fileLogDetail;
    }

    @Override
    public FileLogDetailsDTO fileLogEntityToDTO(FileLogDetail fileLogDetails) {
        if ( fileLogDetails == null ) {
            return null;
        }

        FileLogDetailsDTO fileLogDetailsDTO = new FileLogDetailsDTO();

        fileLogDetailsDTO.setFileLogDetailsId( fileLogDetails.getFileLogDetailsId() );
        fileLogDetailsDTO.setIsProcessed( fileLogDetails.getIsProcessed() );
        fileLogDetailsDTO.setErrorCode( fileLogDetails.getErrorCode() );
        fileLogDetailsDTO.setBolNumber( fileLogDetails.getBolNumber() );
        fileLogDetailsDTO.setInvoiceNumber( fileLogDetails.getInvoiceNumber() );
        fileLogDetailsDTO.setIsActive( String.valueOf( fileLogDetails.getIsActive() ) );
        if ( fileLogDetails.getIsValid() != null ) {
            fileLogDetailsDTO.setIsValid( String.valueOf( fileLogDetails.getIsValid() ) );
        }
        fileLogDetailsDTO.setCreatedBy( fileLogDetails.getCreatedBy() );
        if ( fileLogDetails.getCreatedDate() != null ) {
            fileLogDetailsDTO.setCreatedDate( new java.sql.Date( fileLogDetails.getCreatedDate().getTime() ) );
        }

        return fileLogDetailsDTO;
    }

    @Override
    public List<FileLogDetailsDTO> fileLogEntityToDTO(List<FileLogDetail> fileLogDetails) {
        if ( fileLogDetails == null ) {
            return null;
        }

        List<FileLogDetailsDTO> list = new ArrayList<FileLogDetailsDTO>( fileLogDetails.size() );
        for ( FileLogDetail fileLogDetail : fileLogDetails ) {
            list.add( fileLogEntityToDTO( fileLogDetail ) );
        }

        return list;
    }
}
