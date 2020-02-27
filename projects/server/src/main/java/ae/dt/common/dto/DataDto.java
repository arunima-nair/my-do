package ae.dt.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import ae.dt.deliveryorder.domain.Bol;

@Data
@AllArgsConstructor
@Builder
public class DataDto<T> {
    List<T> dataItems = new ArrayList<>();
 
}
