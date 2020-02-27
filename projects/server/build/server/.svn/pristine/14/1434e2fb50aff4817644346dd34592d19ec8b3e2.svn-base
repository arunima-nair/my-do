package ae.dt.deliveryorder.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ae.dt.deliveryorder.domain.BolChannel;
import ae.dt.deliveryorder.dto.BolChannelDTO;

@Mapper 
public interface ChannelMapper {
	//ChannelMapper INSTANCE = Mappers.getMapper( ChannelMapper.class );
	@Mapping(target = "bol", ignore = true)
	BolChannel channelDTOtoDoman(BolChannelDTO dto);
	 @Mapping(target = "bol", ignore = true)
	BolChannelDTO channelDomaintoDTO(BolChannel destination);
	/*@Mappings({ @Mapping(target = "bolChannels", ignore = true) })
	BoLDTO bolDomaintoDTO(Bol parent);

	@Mappings({ @Mapping(target = "bolChannels", ignore = true) })
	Bol bolDTOtoDoman(BoLDTO parentDTO);*/
	// Set<BolChannel> bolchannelDTOtoDoman(Set<BolChannelDTO> bolchannelDTOset);
	  
	 // Bol bolDTOtoDoman(BoLDTO dto);
/*	default Set<BoLDTO> domainSettoDTOSet(Set<Bol> bolSet) {
	    Set<BoLDTO> dtoSet = new HashSet<>();
	    for (Bol bol: bolSet) {
	        dtoSet.add(BoLMapper.INSTANCE.bolDomaintoDTO(bol));
	    }
	    return dtoSet;
	}*/
}
