package com.ostdlabs.bank_account.jms.config;

import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import com.ostdlabs.bank_account.jms.service.JmsListener;
import com.ostdlabs.bank_account.jms.service.JmsSender;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;

/** JMS config. */
@Configuration
@ComponentScan(basePackageClasses={JmsSender.class})
@PropertySource("classpath:jms.properties")
public class JmsSpringConfig {

    /** For taking mq server url and queue name from file jms.properties. */
    @Inject
    private Environment env;

    @Inject
    JmsListener listener;

    @Bean
    public ConnectionFactory amqConnectionFactory() {
        return new ActiveMQConnectionFactory(env.getProperty("mq.url"));
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(amqConnectionFactory());
    }

    @Bean
    public Queue destination() {
        return new ActiveMQQueue(env.getProperty("queue.name"));
    }

    @Bean
    public JmsOperations jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setDefaultDestination(destination());
        jmsTemplate.setConnectionFactory(connectionFactory());
        return jmsTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(listener);
    }

    /** For real time messagies handling from queue. */
    @Bean
    public DefaultMessageListenerContainer messageListenerContainer() {
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setMessageListener(messageListenerAdapter());
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setDestinationName(env.getProperty("queue.name"));
        return messageListenerContainer;
    }
}
