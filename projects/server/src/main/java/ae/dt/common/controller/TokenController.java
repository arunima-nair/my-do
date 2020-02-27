package ae.dt.common.controller;


import ae.dt.common.dto.TokenDTO;
import ae.dt.common.dto.UserDTO;
import ae.dt.common.util.ApplicationPropertyLoader;
import ae.dt.common.util.JWTTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class TokenController {

    private static Logger logger = LoggerFactory.getLogger(TokenController.class);
    @Autowired
    JWTTokenUtil jwtTokenUtil;

    @Autowired
    ApplicationPropertyLoader applicationPropertyLoader;
    @Autowired
    private Environment env;
    


    @RequestMapping("/app/api/public/getToken")
    public TokenDTO getToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        logger.info("getToken Starts:::");
        UserDTO userDTO ;
        if (StringUtils.equalsIgnoreCase(env.getActiveProfiles()[0], "local")) {
            logger.info("Inside local");
           String  userName = "pradeep10";
         //  String  userName = "kamil";
         //  String userName = "sherin84";
            //String userName = "leadpack1";
           // String userName= "mayur";
           // String userName= "ccusr1";
            userDTO=new UserDTO();
            userDTO.setUserName(userName);
        } else {
            logger.info("Inside env");
            String userName=httpServletRequest.getHeader("OAM_REMOTE_USER");
            logger.info("Inside env ::"+userName);
            userDTO=new UserDTO();
            userDTO.setUserName(userName);
        }

        UserDTO sessionUserDTO = (UserDTO) httpServletRequest.getSession().getAttribute("userDTO");
        if(sessionUserDTO !=null && StringUtils.equalsIgnoreCase(userDTO.getUserName(),sessionUserDTO.getUserName())) {
            logger.info("Inside sessionUserDTO::user Name: " + sessionUserDTO.getUserName() + ",AgentCode :" + sessionUserDTO.getAgentCode() +",AgentType:" +sessionUserDTO.getAgentType());
            return generateToken(sessionUserDTO);
        }else{
            logger.info("Inside user DTO ::user Name: " + userDTO.getUserName() + ",AgentCode :" + userDTO.getAgentCode() +",AgentType:" +userDTO.getAgentType());
            return new TokenDTO();
        }
    }

    private TokenDTO generateToken(UserDTO userDTO) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(jwtTokenUtil.generateToken(userDTO));
        tokenDTO.setAgentType(userDTO.getAgentType());
        tokenDTO.setAgentCode(userDTO.getAgentCode());
        tokenDTO.setUserType(userDTO.getUserType());

        return tokenDTO;
    }

    @RequestMapping("/app/api/secure/refreshToken")
    public TokenDTO refreshToken(String oldToken, HttpServletRequest httpServletRequest){
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(httpServletRequest.getRemoteUser());
        return generateToken(userDTO);
    }

    @RequestMapping("/app/api/secure/getAgentDetails")
    public UserDTO getAgentDetails(HttpServletRequest httpServletRequest){
        UserDTO userDTO=(UserDTO) httpServletRequest.getAttribute("userDTO");
        logger.info("Inside env::user Name: " + userDTO.getUserName() + ",AgentCode :" + userDTO.getAgentCode() +",AgentType:" +userDTO.getAgentType());
        return userDTO;
    }

}
