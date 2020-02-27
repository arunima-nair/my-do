package ae.dt.common.util;

import ae.dt.common.dto.UserDTO;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
//       // HttpServletRequest request = ServletActionContext.getRequest();
//
//        UserDTO userDto = (UserDTO)attributes.getAttribute("userName", ServletRequestAttributes.SCOPE_REQUEST);
//        if (userDto == null) {
//
//            return Optional.of("SYSTEM");
//        }
//        return Optional.ofNullable(userDto.getUserName());
        return Optional.ofNullable("SYSTEM");
    }
}
