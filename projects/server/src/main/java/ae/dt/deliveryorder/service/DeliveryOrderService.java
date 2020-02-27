package ae.dt.deliveryorder.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ae.dt.common.dto.ResponseRosoomDto;
import ae.dt.common.dto.SelectDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.domain.Collection;
import ae.dt.deliveryorder.domain.DTAdminEmailView;
import ae.dt.deliveryorder.domain.DTUserView;
import ae.dt.deliveryorder.domain.DoAuthDoc;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.domain.PaymentLog;
import ae.dt.deliveryorder.domain.PaymentOfProof;
import ae.dt.deliveryorder.domain.ShippingAgent;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.DocumentDTO;
import ae.dt.deliveryorder.dto.FileProcessDTO;
import ae.dt.deliveryorder.dto.PaymentDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
import ae.dt.deliveryorder.dto.RejectionRemarksDTO;
import ae.dt.deliveryorder.dto.ReturnRemarksDTO;
import ae.dt.deliveryorder.dto.SARosoomDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import net.sf.jasperreports.engine.JRException;

/**
 * Created by Arunima on 2/10/2019.
 */

	/**
	 * @author ARUNIMA NAIR
	 *
	 */
public interface DeliveryOrderService {

	public Page<DoAuthRequest> getDOrequestDetails(SearchDTO searchDTO, Pageable pageable, UserDTO userDTO, String sortorder, String sortCol);

	public DoAuthRequest updateAfterPayment(PaymentDTO paymentDTO, String custRefNo, String statusParam,String agentCode);

	public String approveDO(DoAuthRequest req);

	public DoAuthRequest updateCollectionRosoom(PaymentDTO paymentDTO, String custRefNo, String statusParam ,String agentCode);
	
	public String updateLog(String id, String remarkId, String otherComments, UserDTO userDTO);

	public DoAuthRequest getDoAuthrequest(Long doAuthRequestId);

	public List<ReturnRemarksDTO> getReturnRemarks();

	public List<RejectionRemarksDTO> getRejectionRemarks();

	public String getAuthDocumentPathById(Long authDocId) ;

	public String getDODocumentPathById(Long authReqId);

	public byte[] generateConsigneeReport(SearchDTO dto, UserDTO userDTO, HttpServletResponse response) throws JRException, FileNotFoundException, IOException,Exception;

	public List<PaymentLog> fetchRosoomPendingPayments();

	public String savePayProof(DoAuthRequestDTO savedDoAuthRequestDTO, DoAuthRequestDTO dataDTO, String doc, HttpServletRequest httpServletRequest) throws IOException;

	public List<SelectDto> getPaymentOption(String agentCode, String userDTO);

	public String getAdminEmailByAgentCode(String agentCode, List<String> subIds);

	public byte[] recieptWithTransactionID(String transactionId, UserDTO userDTO, HttpServletResponse response) throws JRException;

	public Long getDoauthReqVersion(Long id);

	public PaymentOfProof findByPayProofId(Long id);

	public SARosoomDTO getRosoomDetailByShippingAgentId(Long shippingAgentId,String currency);

	public ShippingAgent getShippingAgentFromPaymentLog(Long doAuthRequestId);

	public Collection getCollection(String invoiceNo);

	public String getDocumentPath(String collectionId);

	public Long getShippingAgentPaymentLog(PaymentLog paymentLog);

	public String findDistinctByAgentCode(String agentCode);

	public DoAuthRequestDTO updatePaymentLogForPaymentPendingWithImporter(Long doAuthRequestId, List<String> selectedInvoice, UserDTO userDto);

	public DoAuthRequestDTO getApproverViewedStatus(Long doAuthReqId, UserDTO userDTO);

	public List<DTUserView> getAdminEmailByAgentCodeReqParty(String agentCode, List<String> subIds, String userName);

	public Double getPayingAmountByInvoiceNumber(List<String> payInvoiceList, String bolNo);
	
	public List<DoAuthRequestDTO> getDODetailByAuthRequestId(Long authRequestId);

	void sendMailForRequestDO(DoAuthRequestDTO savedDoAuthRequestDTO, UserDTO userDTO, Boolean isPayProof);

	public String getAgentNameByAgentCode(String agentCode, List<String> subIds);

	public DTAdminEmailView getAdminDetailsByAgentCode(String agentCode, List<String> subIds);

	public void updateApproverViewedStatus(DoAuthRequest doAuthReq);

}
