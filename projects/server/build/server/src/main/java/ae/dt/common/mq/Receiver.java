package ae.dt.common.mq;

import org.springframework.stereotype.Component;

/**
 * Created by Kamala.Devi on 4/3/2019.
 */
@Component
public class Receiver {

    public String receiveMessage(String message){
        //System.out.println(" MEssage Received "+message);
        return "success";
    }
}
