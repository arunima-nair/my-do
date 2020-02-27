package ae.dt.deliveryorder.facade;

import ae.dt.deliveryorder.dto.BOL;
import ae.dt.deliveryorder.dto.FileProcessDTO;
import ae.dt.deliveryorder.dto.InvoiceUploadDTO;

import java.text.ParseException;
import java.util.List;

public interface FileProcessingFacade {

    public void fileProcessing(List<BOL> bolList) ;

    public String  invoicePathUpdate (InvoiceUploadDTO invoiceUploadDTO);
    
    public void sendErrorMessageToShippingAgent(FileProcessDTO fileProcessDTO);

}
