package com.project.advertstand.mq;

import com.project.advertstand.WebSocketPush.WebSocketPush;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Slf4j
@Named
@ApplicationScoped
public class Consumer implements Serializable {

    private static final String QUEUE_NAME = "advertising.queue";

    @Inject
    private WebSocketPush webSocket;

    private Connection connection;
    private Channel channel;

    public void consume() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        log.info("Receive message");

        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, StandardCharsets.UTF_8);
                log.info("!!!!!!!!!!!!!!! Received " + message);
                System.out.println(message);
                if (message.contains("update")) {
                    System.out.println(message);
                    webSocket.sendMessageToSocket(message);
                    log.info("webSocket " + message);
                }
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public void stop() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

}
