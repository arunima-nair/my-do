package ae.dt.common.dto;

import java.util.List;

import ae.dt.common.dto.SelectDto.SelectDtoBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PartyDetailsDTO {
	private String label;
	private String value;
	private String businessSubId;
	private String eMailId;
	private List<String> shippingAgentList;
}
