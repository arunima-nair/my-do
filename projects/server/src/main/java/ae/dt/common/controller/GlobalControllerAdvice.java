package ae.dt.common.controller;


import ae.dt.common.dto.ErrorDto;
import ae.dt.common.dto.DataDto;
import ae.dt.common.dto.ResponseDto;
import ae.dt.common.exception.ApplicationException;
import ae.dt.common.exception.BusinessException;
import ae.dt.common.constants.Constants;
import ae.dt.common.util.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalControllerAdvice {

    private static Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ApplicationException.class)
    public @ResponseBody
    ResponseDto handleException(ApplicationException e){
        logger.error("Global Application Exception",e);
        ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR,null,e.getMessage(), ErrorCodes.INV_REQ_CODE);
        return errResp;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BusinessException.class)
    public @ResponseBody
    ResponseDto handleException(BusinessException e){
        logger.error("Global Business Exception",e);
        List<ErrorDto> dataItems = new ArrayList<>();
        if (e.getErrorDtoList() != null && e.getErrorDtoList().size() > 0) {
            dataItems = e.getErrorDtoList().stream().map(a -> new ErrorDto(a.getField(),a.getError()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        if (!e.getField().isEmpty() && !e.getMessage().isEmpty()) {
            dataItems.add(new ErrorDto(e.getField(),e.getMessage()));
        }
        DataDto<ErrorDto> data = new DataDto<>(dataItems);
        ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_FAIL,data,null,e.getErrorCode());
        return errResp;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = Exception.class)
    public @ResponseBody
    ResponseDto handleException(Exception e){
        logger.error("Global Exception",e);
        ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_ERROR,null,ErrorCodes.APP_EXP,ErrorCodes.APP_EXP_CODE);
        return errResp;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public @ResponseBody
    ResponseDto handleException(MethodArgumentNotValidException e) {
        logger.error("Global Validation Exception",e);
        List<ErrorDto> dataItems= e.getBindingResult().getFieldErrors().stream()
                .map(a -> new ErrorDto(a.getField(),a.getDefaultMessage()))
                .collect(Collectors.toCollection(ArrayList::new));
        //DataDto<ErrorDto> data = DataDto.<ErrorDto>builder().dataItems(dataItems).build();
        DataDto<ErrorDto> data = new DataDto<>(dataItems);
        ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_FAIL,data,null,ErrorCodes.INV_REQ_CODE);
        return errResp;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class})
    public @ResponseBody
    ResponseDto handleException(BindException e){
        logger.error("Global Validation Exception",e);
        List<ErrorDto> dataItems= e.getBindingResult().getFieldErrors().stream()
                .map(a -> new ErrorDto(a.getField(),a.getDefaultMessage()))
                .collect(Collectors.toCollection(ArrayList::new));
        //DataDto<ErrorDto> data = DataDto.<ErrorDto>builder().dataItems(dataItems).build();
        DataDto<ErrorDto> data = new DataDto<>(dataItems);
        ResponseDto errResp = new ResponseDto(Constants.REST_STATUS_FAIL,data,null,ErrorCodes.INV_REQ_CODE);
        return errResp;
    }

}

