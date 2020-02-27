package ae.dt.deliveryorder.service;

import ae.dt.deliveryorder.domain.FileLog;
import ae.dt.deliveryorder.domain.Bol;
import ae.dt.deliveryorder.dto.BOL;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.FileProcessDTO;
import ae.dt.deliveryorder.dto.Invoice;
import ae.dt.deliveryorder.dto.InvoiceUploadDTO;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface FileProcessingService {


    public String fileProcessing(BOL bol, Invoice invoice) ;
    public void saveFileLogDetails(BOL bol, Invoice invoice, FileLog fileLog, String error);
    public FileLog saveFileLog(BOL bol);

    public String  invoicePathUpdate (InvoiceUploadDTO invoiceUploadDTO);

    public void saveBLDetails(BOL bol, List<BolInvoiceDTO> invoiceDTOList) ;

    public String processInvoice(String uploadDoc, String agentCode, String fileName) throws IOException;
    
    public FileLog saveErrorMessageToFileLog(FileProcessDTO fileProcessDTO);
    
	public void sendEmail(BOL bol);


}
