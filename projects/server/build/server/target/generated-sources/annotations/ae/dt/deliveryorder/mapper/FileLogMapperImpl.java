package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.dto.FileLogDTO;
import ae.dt.deliveryorder.dto.FileLogDetailsDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-01-29T19:43:09+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class FileLogMapperImpl implements FileLogMapper {

    @Autowired
    private FileLogDetailMapper fileLogDetailMapper;

    @Override
    public FileLog fileLogDTOtoEntity(FileLogDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FileLog fileLog = new FileLog();

        fileLog.setIsActive( dto.getIsActive() );
        fileLog.setCreatedBy( dto.getCreatedBy() );
        fileLog.setCreatedDate( dto.getCreatedDate() );
        fileLog.setIsValid( dto.getIsValid() );
        fileLog.setModifiedBy( dto.getModifiedBy() );
        fileLog.setModifiedDate( dto.getModifiedDate() );
        fileLog.setVersion( dto.getVersion() );
        fileLog.setFileLogId( dto.getFileLogId() );
        fileLog.setReferenceNumber( dto.getReferenceNumber() );
        fileLog.setFileName( dto.getFileName() );
        fileLog.setFilePath( dto.getFilePath() );
        fileLog.setUploadType( dto.getUploadType() );
        fileLog.setStatus( dto.getStatus() );
        fileLog.setShippingAgentCode( dto.getShippingAgentCode() );
        fileLog.setFileLogDetails( fileLogDetailsDTOSetToFileLogDetailSet( dto.getFileLogDetails() ) );

        return fileLog;
    }

    @Override
    public FileLogDTO fileLogEntityToDTO(FileLog entity) {
        if ( entity == null ) {
            return null;
        }

        FileLogDTO fileLogDTO = new FileLogDTO();

        fileLogDTO.setFileLogId( entity.getFileLogId() );
        fileLogDTO.setReferenceNumber( entity.getReferenceNumber() );
        fileLogDTO.setFileName( entity.getFileName() );
        fileLogDTO.setFilePath( entity.getFilePath() );
        fileLogDTO.setUploadType( entity.getUploadType() );
        fileLogDTO.setStatus( entity.getStatus() );
        fileLogDTO.setShippingAgentCode( entity.getShippingAgentCode() );
        fileLogDTO.setFileLogDetails( fileLogDetailSetToFileLogDetailsDTOSet( entity.getFileLogDetails() ) );
        fileLogDTO.setVersion( entity.getVersion() );
        fileLogDTO.setCreatedBy( entity.getCreatedBy() );
        if ( entity.getCreatedDate() != null ) {
            fileLogDTO.setCreatedDate( new java.sql.Date( entity.getCreatedDate().getTime() ) );
        }
        fileLogDTO.setIsValid( entity.getIsValid() );
        fileLogDTO.setModifiedBy( entity.getModifiedBy() );
        if ( entity.getModifiedDate() != null ) {
            fileLogDTO.setModifiedDate( new java.sql.Date( entity.getModifiedDate().getTime() ) );
        }
        fileLogDTO.setIsActive( entity.getIsActive() );

        return fileLogDTO;
    }

    @Override
    public List<FileLogDTO> fileLogListEntityToDTO(List<FileLog> entity) {
        if ( entity == null ) {
            return null;
        }

        List<FileLogDTO> list = new ArrayList<FileLogDTO>( entity.size() );
        for ( FileLog fileLog : entity ) {
            list.add( fileLogEntityToDTO( fileLog ) );
        }

        return list;
    }

    @Override
    public List<FileLogDTO> fileLogPageEntityToDTO(Page<FileLog> entity) {
        if ( entity == null ) {
            return null;
        }

        List<FileLogDTO> list = new ArrayList<FileLogDTO>();
        for ( FileLog fileLog : entity ) {
            list.add( fileLogEntityToDTO( fileLog ) );
        }

        return list;
    }

    protected Set<FileLogDetail> fileLogDetailsDTOSetToFileLogDetailSet(Set<FileLogDetailsDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<FileLogDetail> set1 = new HashSet<FileLogDetail>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( FileLogDetailsDTO fileLogDetailsDTO : set ) {
            set1.add( fileLogDetailMapper.fileLogDTOtoEntity( fileLogDetailsDTO ) );
        }

        return set1;
    }

    protected Set<FileLogDetailsDTO> fileLogDetailSetToFileLogDetailsDTOSet(Set<FileLogDetail> set) {
        if ( set == null ) {
            return null;
        }

        Set<FileLogDetailsDTO> set1 = new HashSet<FileLogDetailsDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( FileLogDetail fileLogDetail : set ) {
            set1.add( fileLogDetailMapper.fileLogEntityToDTO( fileLogDetail ) );
        }

        return set1;
    }
}
