package ae.dt.common.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.SimpleRoutingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kamala.Devi on 4/3/2019.
 */

public class AMQPConfig {
    //    private static final String host="jvdtpmb1";
    //private static final String host="pvdtumb1";
//private static final String host="mb.dubaitrade.ae";
    @Value("${mq.host}")
    private String host;
    @Value("${mq.virtual.host}")
    private String virtualHost;
    @Value("${mq.username}")
    private String userName;
    @Value("${mq.password}")
    private String password;

    @Value("${mq.queue.name}")
    private String queueName;


    @Bean
    @Primary
    public ConnectionFactory routedConnectionFactory() {
        SimpleRoutingConnectionFactory rcf = new SimpleRoutingConnectionFactory();
        Map<Object, ConnectionFactory> map = new HashMap<>();
        map.put("support", supportConnectionFactory());
        map.put("transaction", transConnectionFactory());
        rcf.setTargetConnectionFactories(map);
        return rcf;
    }

    @Bean
    public ConnectionFactory supportConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory transConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public ConnectionFactory readConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(host);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplateTransJson(ConnectionFactory transConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(transConnectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }


    @Bean
    public RabbitTemplate rabbitTemplateSupportJson(ConnectionFactory transConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(transConnectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplateSupport(ConnectionFactory transConnectionFactory) {
        SimpleRoutingConnectionFactory simpleRoutingConnectionFactory =(SimpleRoutingConnectionFactory) transConnectionFactory;
        RabbitTemplate rabbitTemplate = new RabbitTemplate(simpleRoutingConnectionFactory.getTargetConnectionFactory("support"));
        return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplateTrans(ConnectionFactory transConnectionFactory) {
        SimpleRoutingConnectionFactory simpleRoutingConnectionFactory =(SimpleRoutingConnectionFactory) transConnectionFactory;
        RabbitTemplate rabbitTemplate = new RabbitTemplate(simpleRoutingConnectionFactory.getTargetConnectionFactory("transaction"));
        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter){
        SimpleRoutingConnectionFactory simpleRoutingConnectionFactory =(SimpleRoutingConnectionFactory) connectionFactory;
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(simpleRoutingConnectionFactory.getTargetConnectionFactory("transaction"));
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}