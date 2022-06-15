package org.project;

import org.junit.jupiter.api.Test;
import org.project.service.JMSProducerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

@SpringBootTest
public class TestJMSProducer {
    @Autowired
    private JMSProducerImpl jmsProducer;

    @Test
    public void testSendMessage() {
        String value = "test";
        jmsProducer.sendMessage("test");
        String result = jmsProducer.receiveMessage();
        Assert.assertEquals(value, result);
    }
}
