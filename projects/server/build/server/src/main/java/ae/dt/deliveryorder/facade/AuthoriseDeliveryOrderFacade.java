package ae.dt.deliveryorder.facade;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import ae.dt.common.dto.ResponseRosoomDto;
import ae.dt.common.dto.UserDTO;
import ae.dt.deliveryorder.domain.DoAuthRequest;
import ae.dt.deliveryorder.dto.DoAuthRequestDTO;

public interface AuthoriseDeliveryOrderFacade {

	DoAuthRequestDTO saveAuthorizeDOrequest(@Valid String bolNo, DoAuthRequestDTO doAuthRequestDTO, UserDTO userDTO,HttpServletRequest httpServletRequest);

	ResponseRosoomDto getResponseStatus(DoAuthRequestDTO doAuthRequestDTORes, DoAuthRequestDTO doAuthRequestDTO,UserDTO userDTO, HttpServletRequest httpServletRequest);

	DoAuthRequest getDoAuthRequest(String doRefNo);

}
