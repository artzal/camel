package org.project.service;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChangeBody implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, String> body = exchange.getIn().getBody(HashMap.class);
        body.put("test2", "test2");
        exchange.getIn().setBody(body);
    }

}