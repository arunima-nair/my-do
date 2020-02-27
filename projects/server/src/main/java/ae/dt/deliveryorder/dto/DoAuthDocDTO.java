package ae.dt.deliveryorder.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoAuthDocDTO {
	@JsonIgnore
	private Long doAuthDocsId;
	
	private String encrDoAuthDocsId;

	private String documentPath;
	
	private String encrDocumentPath;

	private DocumentDTO documentBean;
	 @JsonIgnore
	private DoAuthRequestDTO doAuthRequest;
	
	private String createdBy;
	
	

}
