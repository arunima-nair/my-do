package ae.dt.deliveryorder.facade;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ae.dt.common.constants.Constants;
import ae.dt.common.dto.ResponseRosoomDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.EncryptionUtil;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.PaymentLogDTO;
import ae.dt.deliveryorder.repository.DoAuthRequestRepository;
import ae.dt.deliveryorder.service.AuthoriseDeliveryOrderService;
import ae.dt.deliveryorder.service.DeliveryOrderService;

@Service("authoriseDeliveryOrderFacade")
public class AuthoriseDeliveryOrderFacadeImpl implements AuthoriseDeliveryOrderFacade {

	@Autowired
	DeliveryOrderService deliveryOrderService;
	@Autowired
	AuthoriseDeliveryOrderService authoriseDeliveryOrderService;
	@Autowired
	DoAuthRequestRepository doAuthRequestRepository;
	@Override
	public DoAuthRequestDTO saveAuthorizeDOrequest(String bolNo, DoAuthRequestDTO doAuthRequestDTO, UserDTO userDTO,
			HttpServletRequest httpServletRequest) {

		List<String> payInvoiceDetails = doAuthRequestDTO.getPayingInvoice();
		List<String> payInvoiceList = new ArrayList();
		Double payingAmt = 0D;
		boolean isReturn = false;
		doAuthRequestDTO.setIsOnline(false);
		doAuthRequestDTO.setIsPayImporter(false);
		if (payInvoiceDetails != null) {
			for (String invList : payInvoiceDetails) {
				String[] parts = invList.split("\\+");
				if (parts.length > 0) {
					payInvoiceList.add(EncryptionUtil.decrypt(parts[0]));
				}
			}
			doAuthRequestDTO.setPayInvoiceList(payInvoiceList);
			payingAmt = deliveryOrderService.getPayingAmountByInvoiceNumber(payInvoiceList, bolNo);
			doAuthRequestDTO.setPayingAmt(payingAmt);
		} else {
			doAuthRequestDTO.setPayingAmt(payingAmt);
		}
		if (doAuthRequestDTO.getIsPay() && doAuthRequestDTO.getChoosePay()) {
			doAuthRequestDTO.setIsOnline(true);
		} else if (doAuthRequestDTO.getIsPay() && !doAuthRequestDTO.getChoosePay()) {
			doAuthRequestDTO.setIsPayImporter(true);
		}

		if (doAuthRequestDTO.getIsOnline() || doAuthRequestDTO.getIsCredit() || doAuthRequestDTO.getIsPayProof())
			doAuthRequestDTO.setStatus(Constants.DO_STATUS.DO_INITIATED.value);
		else if (doAuthRequestDTO.getIsPayImporter()) {
			// doAuthRequestDTO.setStatus(Constants.DO_STATUS.PAYMENT_PENDING_WITH_IMPORTER.value);
		}

		if (doAuthRequestDTO.getPayingAmt() <= 0) {
			doAuthRequestDTO.setStatus(Constants.DO_STATUS.PENDING_FOR_APPROVAL.value);
			doAuthRequestDTO.setIsOnline(false);
			doAuthRequestDTO.setIsPayImporter(false);
			doAuthRequestDTO.setIsCredit(false);
			doAuthRequestDTO.setIsPayProof(false);
		}

		if (doAuthRequestDTO.getStatus() != null)
			if (doAuthRequestDTO.getStatus().equalsIgnoreCase(Constants.DO_STATUS.RETURNED.value)) {
				isReturn = true;
			}

		DoAuthRequestDTO doAuthRequestDTORes = authoriseDeliveryOrderService.saveAuthorizeDOrequest(bolNo,
				doAuthRequestDTO, userDTO, httpServletRequest);
		doAuthRequestDTORes.setPayingAmt(payingAmt);

		sendMailForRequestDO(doAuthRequestDTORes, userDTO,doAuthRequestDTO.getIsPayProof());

		return doAuthRequestDTORes;
	}

	@Override
	public ResponseRosoomDto getResponseStatus(DoAuthRequestDTO doAuthRequestDTORes, DoAuthRequestDTO doAuthRequestDTO,
			UserDTO userDTO, HttpServletRequest httpServletRequest) {
		ResponseRosoomDto responseRosoomDto = new ResponseRosoomDto();
		if (doAuthRequestDTORes.getIsAmend()) {
			String soTransactionId = null;
			if (doAuthRequestDTORes.getPaymentLogs() != null)
				for (PaymentLogDTO pay : doAuthRequestDTORes.getPaymentLogs()) {
					soTransactionId = pay.getTransactionId();
				}

			responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
					.message("DO AMENDED SUCCESSFULLY.").code(soTransactionId).build();

		} else {
			responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
					.message("REQUEST COMPLETED SUCCESSFULLY WITH DO REFERENCE NUMBER: "
							+ doAuthRequestDTORes.getDoRefNo())
					.code(null).build();
		}

		if (doAuthRequestDTO.getIsCredit() || doAuthRequestDTO.getIsPayProof()) {
			String status = null;
			if (doAuthRequestDTO.getIsCredit()) {
				// status = creditPaymentProcess(doAuthRequestDTORes, doAuthRequestDTO,
				// httpServletRequest);
				status = "Payment through Credit Succesfully updated. DO Reference No. "
						+ doAuthRequestDTORes.getDoRefNo();
				if (doAuthRequestDTORes.getIsPartialPayment())
					status = "Payment through Credit Succesfully updated and it is partial payment.DO request will processed only after complete payment.  DO Reference No."
							+ doAuthRequestDTORes.getDoRefNo();

				if (doAuthRequestDTORes.getStatus() != "FAILED")
					responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS)
							.data(null).message(status).code(null).build();
				else
					responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL).data(null)
							.message("Payment through Credit Failed.").code(null).build();
			} else {
				status = "Upload Payment Proof Succesfully updated. DO Reference No. "
						+ doAuthRequestDTORes.getDoRefNo();
				if (doAuthRequestDTORes.getIsPartialPayment())
					status = "Upload Payment Proof Succesfully updated and it is partial payment.DO request will processed only after complete payment.  DO Reference No."
							+ doAuthRequestDTORes.getDoRefNo();

			}
			if (doAuthRequestDTORes.getStatus() != "FAILED")
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
						.message(status).code(null).build();
			else {
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_FAIL).data(null)
						.message("Upload Payment Proof Failed.").code(null).build();
			}
		} else if (doAuthRequestDTO.getIsPayImporter()) {
			if (doAuthRequestDTORes.getStatus() != "FAILED")
				responseRosoomDto = new ResponseRosoomDto().builder().status(Constants.REST_STATUS_SUCCESS).data(null)
						.message("REQUEST COMPLETED SUCCESSFULLY WITH DO REFERENCE NUMBER: "
								+ doAuthRequestDTORes.getDoRefNo())
						.code(null).build();
		}

		return responseRosoomDto;
	}

	public void sendMailForRequestDO(DoAuthRequestDTO doAuthRequestDTORes, UserDTO userDTO, Boolean isPayProof) {
		deliveryOrderService.sendMailForRequestDO(doAuthRequestDTORes, userDTO,isPayProof);

	}

	@Override
	public DoAuthRequest getDoAuthRequest(String doRefNo) {
		return doAuthRequestRepository.findDoAuthRequestByDoRefNo(doRefNo);
	}

}
