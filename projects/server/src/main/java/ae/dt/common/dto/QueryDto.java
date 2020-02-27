package ae.dt.common.dto;

import ae.dt.common.validators.OnDraftRead;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class QueryDto {

    @NotNull(groups = {OnDraftRead.class})
    @NotEmpty(groups = OnDraftRead.class)
    private String from;

    @NotNull(groups = {OnDraftRead.class})
    @NotEmpty(groups = OnDraftRead.class)
    private String to;

//    @NotNull(groups = {OnDraftRead.class})
//    @NotEmpty(groups = OnDraftRead.class)
//    @Valid
//    private PermitType permitType;

    private String referenceNo;

    @NotNull(groups = {OnDraftRead.class})
    @NotEmpty(groups = OnDraftRead.class)
    @Valid
    private PageDto page;
}
