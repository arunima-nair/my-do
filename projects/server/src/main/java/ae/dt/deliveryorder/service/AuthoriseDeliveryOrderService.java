package ae.dt.deliveryorder.service;

import javax.servlet.http.HttpServletRequest;

import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;
import ae.dt.deliveryorder.dto.PaymentDTO;

public interface AuthoriseDeliveryOrderService {
		
	public DoAuthRequestDTO saveAuthorizeDOrequest(String bolNo, DoAuthRequestDTO doAuthRequestDTO, UserDTO userDTO,HttpServletRequest httpServletRequest);
	
	public Long getTransaction() ;
	
	public DoAuthRequest updateAfterRosoomPayment(PaymentDTO paymentDTO, UserDTO userDTO);


}
