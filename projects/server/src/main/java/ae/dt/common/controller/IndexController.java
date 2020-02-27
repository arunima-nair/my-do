package ae.dt.common.controller;

import ae.dt.common.dto.UserDTO;
import ae.dt.common.facade.IndexFacade;
import ae.dt.common.interceptor.WebInterceptor;
import ae.dt.deliveryorder.domain.DTUserView;
import ae.dt.deliveryorder.exception.UnAuthorizedResourceAccessException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller

public class IndexController {
	Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private Environment env;

	@Autowired
	IndexFacade indexFacade;

	@RequestMapping("/app/home")
	public String home(HttpServletRequest httpServletRequest) {
		setUserDTO(httpServletRequest);
		String routerpath = "redirect:/" + httpServletRequest.getParameter("path");
		return routerpath;

	}

	private void setUserDTO(HttpServletRequest httpServletRequest) {
		String userName = "", agentType = "", agentCode = "", userType = "";
		if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
			logger.info("Inside local");
			// userName = "sherin84";
			// agentType = "A";
			// agentCode = "03549";
//userName = "kamil";	 agentType = "I";	 agentCode = "12690";			 userType="I";
		//	userName = "mayur";	 agentType = "C";	 agentCode = "C0034";			 userType="I";
		//	userName = "mayur";	 agentType = "F";	 agentCode = "T1837";			 userType="I";
			// userName = "ccusr1"; userType = "IN";
			
			
			 userName = "pradeep10"; agentType = "A"; agentCode = "A180"; userType = "A";
			 
			/*
			 * userName = "leadpack1"; agentType = "FF"; agentCode = "02397"; userType="I";
			 */
		} else {
			userName = httpServletRequest.getParameter("dtloginid");
			agentType = httpServletRequest.getParameter("agent_type");
			agentCode = httpServletRequest.getParameter("agent_code");
			userType = httpServletRequest.getParameter("userType");
			logger.info("Inside env::user Name: " + userName + ",AgentCode :" + agentCode + ",AgentType:" + agentType);
		}
		if (userType.equalsIgnoreCase("I") || userType.equalsIgnoreCase("A")) {
			List<DTUserView>dtUserViewList=indexFacade.getDTUserList(agentType,agentCode,userName);
				if(dtUserViewList.isEmpty()) {
				 throw new UnAuthorizedResourceAccessException("Not allowed to access this Resource");
			}
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(userName);
		userDTO.setAgentType(agentType);
		userDTO.setAgentCode(agentCode);
		userDTO.setUserType(userType);

		httpServletRequest.setAttribute("userDTO", userDTO);
		httpServletRequest.getSession().setAttribute("userDTO", userDTO);
	}

}
