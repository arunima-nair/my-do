package ae.dt.common.interceptor;

import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.ApplicationPropertyLoader;
import ae.dt.common.util.JWTTokenUtil;
import ae.dt.deliveryorder.controller.BillOfLaddingController;
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

import static ae.dt.common.util.JWTTokenUtil.HEADER_STRING;
import static ae.dt.common.util.JWTTokenUtil.TOKEN_PREFIX;

@Slf4j
@Component
public class AppInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(AppInterceptor.class);


    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.debug("Inside AppInterceptor");

            String requestHeader = httpServletRequest.getHeader(HEADER_STRING);
            String userName = "",agentType="";
            if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
                logger.info("Inside local");
               userName ="mhs006";
               agentType="A";
            } else {
                userName=httpServletRequest.getHeader("OAM_REMOTE_USER");
                agentType=httpServletRequest.getParameter("agent_type");
                logger.info("Inside dev" +agentType +","+userName);
                /* Todo validation for agent type*/
            }
            logger.debug("Inside AppInterceptor" + userName);
            logger.debug("Inside AppInterceptor" + agentType);
            String authToken = null;
            Claims claims = null;
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
                UserDTO userDTO = new UserDTO();
                userDTO.setUserName(claims.get("userName").toString());
                userDTO.setAgentType(claims.get("agentType").toString());
                httpServletRequest.setAttribute("userName", userDTO);


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
