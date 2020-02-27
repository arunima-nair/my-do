package ae.dt.deliveryorder.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.FileLogDetail;
import ae.dt.deliveryorder.dto.FileLogDetailsDTO;

@Mapper(componentModel = "spring")
public interface FileLogDetailMapper {

	@Mapping(target = "fileLog", ignore = true)
	FileLogDetail fileLogDTOtoEntity(FileLogDetailsDTO dto);

	@Mapping(target = "fileLog", ignore = true)
	FileLogDetailsDTO fileLogEntityToDTO(FileLogDetail fileLogDetails);

	List<FileLogDetailsDTO> fileLogEntityToDTO(List<FileLogDetail> fileLogDetails);

}
