package org.project.service;

interface JMSProducer {

    void sendMessage(final String message);

    String receiveMessage();

}

