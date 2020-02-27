package ae.dt.common.dto;

import lombok.Data;

@Data
public class PageDto {

    private int size =10;
    private int totalPages;
    private long totalElements;
    private int pageNumber;

}
