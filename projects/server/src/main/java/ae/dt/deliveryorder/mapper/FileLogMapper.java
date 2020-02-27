package ae.dt.deliveryorder.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.dto.FileLogDTO;

@Mapper(componentModel = "spring",uses = FileLogDetailMapper.class)
public interface FileLogMapper {

	FileLogMapper INSTANCE = Mappers.getMapper(FileLogMapper.class);
	
	FileLog fileLogDTOtoEntity(FileLogDTO dto);
	FileLogDTO fileLogEntityToDTO(FileLog entity);
	
	List<FileLogDTO> fileLogListEntityToDTO(List<FileLog> entity);
	List<FileLogDTO> fileLogPageEntityToDTO(Page<FileLog> entity);
}
