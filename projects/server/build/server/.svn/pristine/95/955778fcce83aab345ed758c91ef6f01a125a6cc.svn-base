package ae.dt.deliveryorder.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ae.dt.common.constants.Constants;
import ae.dt.common.controller.BaseController;
import ae.dt.common.dto.ResponseDto;
import ae.dt.common.exception.BusinessException;
import ae.dt.deliveryorder.dto.BOL;
import ae.dt.deliveryorder.dto.FileProcessDTO;
import ae.dt.deliveryorder.dto.InvoiceUploadDTO;
import ae.dt.deliveryorder.facade.FileProcessingFacade;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class FileProcessingController extends BaseController {

    @Autowired
    FileProcessingFacade fileProcessingFacade;

    @PostMapping(path = "/file/api/public/fileProcessing")
    public ResponseDto fileProcessing(@RequestBody List<BOL> bolList, HttpServletRequest httpServletRequest)  {
        log.debug("FileProcessing Started !!!!!!!!!!!!!!!!!!!!!!!!");
        fileProcessingFacade.fileProcessing(bolList);
        log.debug("FileProcessing Ended !!!!!!!!!!!!!!!!!!!!!!!!");
        return  new ResponseDto(Constants.REST_STATUS_SUCCESS,null,null,null);
    }
    @PostMapping(path = "/file/api/public/invoicePathUpdate")
    public void invoicePathUpdate(@RequestBody InvoiceUploadDTO invoiceUploadDTO, HttpServletRequest httpServletRequest)  {
        log.debug("invoicePathUpdate Started !!!!!!!!!!!!!!!!!!!!!!!!");
        String invoiceupdateStatus=fileProcessingFacade.invoicePathUpdate(invoiceUploadDTO);
        if(!StringUtils.isEmpty(invoiceupdateStatus)) {
            throw new BusinessException(invoiceupdateStatus);
        }
    }
    
    @PostMapping(path = "/file/api/public/sendErrorMessageToShippingAgent")
    public ResponseDto sendErrorMessageToShippingAgent(@RequestBody FileProcessDTO fileProcessDTO , HttpServletRequest httpServletRequest)  {
        log.debug("Mail send to admin Started !!!!!!!!!!!!!!!!!!!!!!!!");
        fileProcessingFacade.sendErrorMessageToShippingAgent(fileProcessDTO);
        log.debug("Mail send to admin Ended !!!!!!!!!!!!!!!!!!!!!!!!");
        return  new ResponseDto(Constants.REST_STATUS_SUCCESS,null,null,null);
    }
    
}
