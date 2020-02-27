package com.dt.deliveryorder.exception;

import com.dt.deliveryorder.dto.ErrorDTO;
import org.apache.camel.Exchange;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static com.dt.deliveryorder.util.Utils.getFileSystemPathSeperator;
import static com.dt.deliveryorder.util.Utils.getTodayDate;

public class CamelExceptionUtils {
    private static  Logger logger = LoggerFactory.getLogger(CamelExceptionUtils.class);

    public static void handleException(Exchange exchange){

        String exceptionBody =  ExceptionUtils.getStackTrace(exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setFilePath(exchange.getContext().getGlobalOption("FILE_ERROR_PATH")+
                getTodayDate()+
                getFileSystemPathSeperator()+(String)exchange.getIn().getHeader("newFileName"));
        errorDTO.setError(exceptionBody);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(exchange.getContext().getGlobalOption("FILE_ERROR_POST_URL"), errorDTO, ErrorDTO.class);
        } catch (Exception ex){
            logger.error("",ex);
            serializeObject(errorDTO);
        }
        logger.info("--------------END OF ERROR TRANSFORMATION-------- ");
    }

    private static void serializeObject(ErrorDTO errorDTO){
        try ( FileOutputStream fileOut = new FileOutputStream(errorDTO.getFilePath() + "_error.txt");
              ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(errorDTO);
        } catch (Exception ex){
            logger.error("",ex);
        }
    }
}
