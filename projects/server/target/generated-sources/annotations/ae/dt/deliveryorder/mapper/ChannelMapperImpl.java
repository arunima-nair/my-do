package ae.dt.deliveryorder.mapper;

import ae.dt.deliveryorder.domain.BolChannel;
import ae.dt.deliveryorder.domain.ChannelType;
import ae.dt.deliveryorder.dto.BolChannelDTO;
import ae.dt.deliveryorder.dto.ChannelTypeDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-02-27T11:35:25+0400",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_201 (Oracle Corporation)"
)
@Component
public class ChannelMapperImpl implements ChannelMapper {

    @Override
    public BolChannel channelDTOtoDoman(BolChannelDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BolChannel bolChannel = new BolChannel();

        if ( dto.getIsActive() != null ) {
            bolChannel.setIsActive( dto.getIsActive() );
        }
        bolChannel.setCreatedBy( dto.getCreatedBy() );
        bolChannel.setCreatedDate( dto.getCreatedDate() );
        bolChannel.setIsValid( dto.getIsValid() );
        bolChannel.setModifiedBy( dto.getModifiedBy() );
        bolChannel.setModifiedDate( dto.getModifiedDate() );
        bolChannel.setChannelId( dto.getChannelId() );
        bolChannel.setReference( dto.getReference() );
        bolChannel.setChannelType( channelTypeDTOToChannelType( dto.getChannelType() ) );

        return bolChannel;
    }

    @Override
    public BolChannelDTO channelDomaintoDTO(BolChannel destination) {
        if ( destination == null ) {
            return null;
        }

        BolChannelDTO bolChannelDTO = new BolChannelDTO();

        bolChannelDTO.setChannelId( destination.getChannelId() );
        bolChannelDTO.setReference( destination.getReference() );
        bolChannelDTO.setChannelType( channelTypeToChannelTypeDTO( destination.getChannelType() ) );
        bolChannelDTO.setCreatedBy( destination.getCreatedBy() );
        bolChannelDTO.setCreatedDate( destination.getCreatedDate() );
        bolChannelDTO.setIsValid( destination.getIsValid() );
        bolChannelDTO.setModifiedBy( destination.getModifiedBy() );
        bolChannelDTO.setModifiedDate( destination.getModifiedDate() );
        bolChannelDTO.setIsActive( destination.getIsActive() );

        return bolChannelDTO;
    }

    protected ChannelType channelTypeDTOToChannelType(ChannelTypeDTO channelTypeDTO) {
        if ( channelTypeDTO == null ) {
            return null;
        }

        ChannelType channelType = new ChannelType();

        channelType.setChannelTypeId( channelTypeDTO.getChannelTypeId() );
        channelType.setDescription( channelTypeDTO.getDescription() );
        channelType.setValue( channelTypeDTO.getValue() );

        return channelType;
    }

    protected ChannelTypeDTO channelTypeToChannelTypeDTO(ChannelType channelType) {
        if ( channelType == null ) {
            return null;
        }

        ChannelTypeDTO channelTypeDTO = new ChannelTypeDTO();

        channelTypeDTO.setChannelTypeId( channelType.getChannelTypeId() );
        channelTypeDTO.setDescription( channelType.getDescription() );
        channelTypeDTO.setValue( channelType.getValue() );

        return channelTypeDTO;
    }
}
