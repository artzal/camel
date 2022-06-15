package org.project.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.project.service.JMSProducerImpl;
import org.project.service.ChangeBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRouter extends RouteBuilder {
    private final String directRest = "direct:rest";
    private final String directActiveMQ = "direct:activeMQ";

    private JMSProducerImpl jmsProducer;
    private ChangeBody changeBody;

    @Autowired
    public CamelRouter(JMSProducerImpl jmsProducer, ChangeBody changeBody) {
        this.jmsProducer = jmsProducer;
        this.changeBody = changeBody;
    }

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto);

        rest().path("/rest")
                .post()
                .to(directRest);

        from(directRest)
                .log("step1 : ${body}")
                .to(directActiveMQ)
                .process(changeBody)
                .to(directActiveMQ)
                .log("step2 : ${body}")
                .transform()
                .constant(null);

        from(directActiveMQ)
                .process(exchange -> jmsProducer.sendMessage(exchange.getIn().getBody().toString()));

    }
}
