package ae.dt.common.util;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class MailTemplateResolver {
private String head;
private String id;
private String imageURL;
private String content;
private String invoice;
private String toAddress;
private String fromAddress;
private String ccAddress;
}
