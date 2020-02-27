package ae.dt.deliveryorder.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import ae.dt.deliveryorder.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ae.dt.common.domain.DTAgentView;
import ae.dt.common.dto.DataDto;
import ae.dt.common.dto.ResponseDto;
import ae.dt.common.dto.SelectDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.dto.BoLDTO;
import ae.dt.deliveryorder.dto.BoLDetailDTO;
import ae.dt.deliveryorder.dto.BolInvoiceDTO;
import ae.dt.deliveryorder.dto.FileDTO;
import ae.dt.deliveryorder.dto.InvoiceTypeDTO;
import ae.dt.deliveryorder.dto.RequestBolInvoiceDTO;
import ae.dt.deliveryorder.dto.SearchDTO;
import ae.dt.deliveryorder.dto.VersionDTO;

/**
 * Created by Kamala.Devi on 1/28/2019.
 */

public interface BillOfLaddingService {

	public String getBolStatus(String bol_no);

	public String saveInvoiceDetails(BolInvoice bolInvoice);

	public List<Bol> getBolDetails(String bolno);

	public BoLDTO getBolDetailObject(String bolNo);

	public Page<Bol> getBolDetails(SearchDTO searchDTO, Pageable pageable, UserDTO userDTO, String sortorder, String sortCol);

	public String saveRequestBoLInvoice(String bol,String agentCode, UserDTO userDTO, String type, String agentType);

	public BoLDTO findBolObjByBolnumber(String bolNumber);

	public BolInvoiceDTO findByInvoiceNumber(String invNumber);


	public List<BoLDTO> getBLdetails(String bolNo, String agentType);

	public Set<DTAgentView> getAgentDetails(String searchParam, List<String> subIds);
	
	public DTAgentView getRequestingAgentDetail(String searchParam, List<String> subIds);

	public String alertnotify(String bolNos, String emails, UserDTO userDTO);

	public BolInvoice getInvoiceDetails(String invId);

	public BolInvoice getBolInvoicebyBolNumber(String bolno, String invNo);

	public List getShippingAgentSearchAttribute(String agentCode);

	public ShippingAgent getShippingAgent(String agentCode);

	public Date getInvoiceExpiryDate(String invoiceNumber, String bolNumber);

	public List getSAInitiatorPartialPayment(String agentCode, String impType);

	public void saveBoL(Bol bol);

	public List<InvoiceTypeDTO> getInvoiceTypes();

	public List<BolInvoice> getInvoicesUploaded(String bolNo);

	public String uploadBolFile(String uploadDoc, String fileName, HttpServletRequest httpServletRequest) throws IOException;

	public DTAgentView getAgentDetailByAgentCodeAndBusinessGroupId(String agentCode, String businessSubId);

	public List<BoLDTO> getBLdetails(String bolNo);

	public Boolean checkInvoice(String invoiceNumber, String bolNumber);

	public List<BoLDTO> getBLdetailsNew(String bolNo, String agentType);

	public BoLDTO saveBolVersion(String bolNo, UserDTO userDTO);

	public Long getBolVersion(String bolNo);

	public String getSAinvoiceDelimitter(UserDTO userDTO);

	public Boolean deleteBol(String bolNo, UserDTO userDTO);

	public Page<RequestBolInvoice> searchReqBLInvoicedetails(SearchDTO searchDTO, Pageable pageable, UserDTO userDTO,
			String sortorder, String sortCol);

	public String getShippingAgentEmailId(String agentCode);

	public Bol findBolByBolNumber(String bolNumber);
}
