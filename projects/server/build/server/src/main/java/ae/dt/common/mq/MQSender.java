package ae.dt.common.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Kamala.Devi on 4/3/2019.
 */
@Component
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplateSupport;

    @Autowired
    private RabbitTemplate rabbitTemplateTrans;

    @Value("${mq.exchange}")
    private String exchange;
    @Value("${mq.routing.key}")
    private String routingKey;

     public void sendMessages(String message) throws Exception {
         //rabbitTemplateTrans.convertAndSend(exchange,routingKey, message);
     }

}
