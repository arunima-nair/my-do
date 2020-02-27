package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.ChannelType;
import ae.dt.deliveryorder.dto.ChannelTypeDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class ChannelTypeMapperImpl implements ChannelTypeMapper {

    @Override
    public ChannelType channelTypeDTOtoDoman(ChannelTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ChannelType channelType = new ChannelType();

        channelType.setChannelTypeId( dto.getChannelTypeId() );
        channelType.setDescription( dto.getDescription() );
        channelType.setValue( dto.getValue() );

        return channelType;
    }

    @Override
    public ChannelTypeDTO channelTypeDomaintoDTO(ChannelType destination) {
        if ( destination == null ) {
            return null;
        }

        ChannelTypeDTO channelTypeDTO = new ChannelTypeDTO();

        channelTypeDTO.setChannelTypeId( destination.getChannelTypeId() );
        channelTypeDTO.setDescription( destination.getDescription() );
        channelTypeDTO.setValue( destination.getValue() );

        return channelTypeDTO;
    }
}
