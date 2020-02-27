package ae.dt.common.interceptor;

import ae.dt.common.dto.UserDTO;
import ae.dt.common.facade.IndexFacade;
import ae.dt.common.util.JWTTokenUtil;
import ae.dt.deliveryorder.domain.DTUserView;
import ae.dt.deliveryorder.exception.UnAuthorizedResourceAccessException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.List;

import static ae.dt.common.util.JWTTokenUtil.HEADER_STRING;
import static ae.dt.common.util.JWTTokenUtil.TOKEN_PREFIX;

/**
 * Created by Kamala.Devi on 3/17/2019.
 */
@Slf4j
@Component
public class WebInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(WebInterceptor.class);
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    private Environment env;
    
    @Autowired
	IndexFacade indexFacade;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.debug("Inside WebInterceptor");
        if (StringUtils.contains(httpServletRequest.getRequestURI(), "/app/api/secure") ) {
            logger.info("Inside secure block");
            String requestHeader = httpServletRequest.getHeader(HEADER_STRING);
            String userName = "";
            if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
                logger.info("Inside local");

         //  userName = "kamil";
                //userName = "ccusr1";
                //userName="sherin84";
              userName = "pradeep10";
            } else {
                userName = httpServletRequest.getHeader("OAM_REMOTE_USER");
                logger.info("Inside env::user Name: " + userName + ",AgentCode :" );
                /* Todo validation for agent type*/
            }
        
            logger.debug("Inside AppInterceptor::user Name: " + userName + ",AgentCode :" );
            logger.debug("Inside AppInterceptor :::" + requestHeader);
        
            String authToken = null;
            Claims claims = null;
            UserDTO sessionUserDTO = (UserDTO) httpServletRequest.getSession().getAttribute("userDTO");
            if (requestHeader != null && requestHeader.startsWith(TOKEN_PREFIX)) {
                authToken = requestHeader.replace(TOKEN_PREFIX, "");
                try {

                    if (!jwtTokenUtil.validateToken(authToken, userName)) {
                        return sendUnAuthorizedResponse(httpServletResponse);
                    }
                    claims = jwtTokenUtil.getAllClaimsFromToken(authToken);
                } catch (Exception ex) {
                    return sendUnAuthorizedResponse(httpServletResponse);
                }

            }

                logger.info("claims user name :::" + claims.get("userName").toString()+",:sessionUserName:"+sessionUserDTO.getUserName());
            if(StringUtils.equalsIgnoreCase(claims.get("userName").toString(),sessionUserDTO.getUserName())) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserName(String.valueOf(claims.get("userName")));
                logger.info("CLAIMS!!!!!!!!!!!!!!!!!!!!!!!!!"+claims.get("userName"));
                logger.info("CLAIMS!!!!!!!!!!!!!!!!!!!!!!!!!"+claims.get("agentType"));
                logger.info("CLAIMS!!!!!!!!!!!!!!!!!!!!!!!!!"+claims.get("agentCode"));
                if(claims.get("agentType")!=null)
                userDTO.setAgentType(String.valueOf(claims.get("agentType")));
                else
                 userDTO.setAgentType("");
                if(claims.get("agentCode")!=null)
                userDTO.setAgentCode(String.valueOf(claims.get("agentCode")));
                else
                	userDTO.setAgentCode("");	
                userDTO.setUserType(String.valueOf(claims.get("userType")));
                httpServletRequest.setAttribute("userName", userDTO);
                httpServletRequest.setAttribute("userDTO", userDTO);
        		if (userDTO.getAgentType().equalsIgnoreCase("I") || userDTO.getAgentType().equalsIgnoreCase("A")) {
        			List<DTUserView>dtUserViewList=indexFacade.getDTUserList(userDTO.getAgentType(),userDTO.getAgentCode(),userName);
        				if(dtUserViewList.isEmpty()) {
        				 throw new UnAuthorizedResourceAccessException("Not allowed to access this Resource");
        			}
        		}
            }else{
                return sendUnAuthorizedResponse(httpServletResponse);
            }

            return true;

        }
        if (StringUtils.contains(httpServletRequest.getRequestURI(), "/app/api/public/responseFromRosoomPaymentApp")) {
            logger.info("Inside public block");
            String requestHeader = httpServletRequest.getHeader(HEADER_STRING);
            String userName = "";
            if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
                logger.info("Inside local");
               // userName = "kamil";
              //  userName = "ccusr1";
              //  userName="sherin84";
              userName = "pradeep10";
            } else {
                userName = httpServletRequest.getHeader("OAM_REMOTE_USER");
                logger.info("Inside env::user Name: " + userName   );
                /* Todo validation for agent type*/
            }
        
            logger.debug("Inside Web Interceptor::user Name: " + userName );
            logger.debug("Inside Web Interceptor :::" + requestHeader);
            UserDTO sessionUserDTO = (UserDTO) httpServletRequest.getSession().getAttribute("userDTO");
            logger.info("SessionUserName:"+sessionUserDTO.getUserName());
            if(StringUtils.equalsIgnoreCase(userName,sessionUserDTO.getUserName())) {
                httpServletRequest.setAttribute("userName", sessionUserDTO);
                httpServletRequest.setAttribute("userDTO", sessionUserDTO);
            }else{
                return sendUnAuthorizedResponse(httpServletResponse);
            }
            return true;
        }
        if (StringUtils.contains(httpServletRequest.getRequestURI(), "/app/api/file")){
            logger.info("File....................");
            logger.info("OAM_REMOTE_USER................."+httpServletRequest.getHeader("OAM_REMOTE_USER"));
            logger.info("dtloginid................"+httpServletRequest.getParameter("dtloginid"));
            String userName = "";
            if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
              // userName = "kamil";
                // userName = "ccusr1";
                // userName="sherin84";
                   userName = "pradeep10";
            } else {
                userName = httpServletRequest.getHeader("OAM_REMOTE_USER");
                logger.info("Inside env::user Name: " + userName + ",AgentCode :" );
                /* Todo validation for agent type*/
            }

            logger.debug("Inside AppInterceptor::user Name: " + userName  );
            UserDTO sessionUserDTO = (UserDTO) httpServletRequest.getSession().getAttribute("userDTO");
            logger.info("SessionUserName:"+sessionUserDTO.getUserName());
            if(StringUtils.equalsIgnoreCase(userName,sessionUserDTO.getUserName())) {
                httpServletRequest.setAttribute("userName", sessionUserDTO);
                httpServletRequest.setAttribute("userDTO", sessionUserDTO);
            }

        }

        return true;
    }


    private boolean sendUnAuthorizedResponse(HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //System.out.println(" Post Handle ");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //System.out.println(" after Completion ");
    }
}