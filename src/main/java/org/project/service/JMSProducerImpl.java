package org.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Service
public class JMSProducerImpl implements JMSProducer {
    private JmsTemplate jmsTemplate;
    private String queueName;

    @Autowired
    public JMSProducerImpl(JmsTemplate jmsTemplate,
                           @Value("${activeMQ.queue.name}") String queueName){
        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }

    public void sendMessage(final String message) {
        jmsTemplate.send(queueName, session -> {
            TextMessage message1 = session.createTextMessage();
            message1.setText(message);
            return message1;
        });
    }

    public String receiveMessage() {
        Message message = jmsTemplate.receive(queueName);
        TextMessage textMessage = (TextMessage) message;
        try {
            return textMessage.getText();
        } catch (JMSException e) {
            return null;
        }
    }

}
