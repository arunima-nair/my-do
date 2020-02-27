package ae.dt.common.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ae.dt.common.dto.UserDTO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BaseController {

    protected UserDTO fetchLoggedinUser(){
        ServletRequestAttributes attr = getRequestContextAttributes();

        return (UserDTO) attr.getRequest().getAttribute("userName");

    }

    private ServletRequestAttributes getRequestContextAttributes(){
        return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    }
    
    protected String downloadFile(String path, HttpServletResponse response) throws IOException {
		InputStream is;
		if (path != null) {
			File file = new File(path.toString());
			is = new FileInputStream(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
			OutputStream os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
			os.flush();
			os.close();
			is.close();
		} else {
			return "File Not Found.";
		}

		return "SUCCESS";
    }
}
