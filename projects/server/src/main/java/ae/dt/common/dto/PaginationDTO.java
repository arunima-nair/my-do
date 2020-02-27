package ae.dt.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Kamala.Devi on 2/19/2019.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationDTO<T> {

    List<T> elements;
    Integer pageNumber;
    Long total;
    Integer pageSize;
}