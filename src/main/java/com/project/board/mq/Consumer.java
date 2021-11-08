package com.project.board.mq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Slf4j
@Named
@ApplicationScoped
public class Consumer implements Serializable, MessageListener {

    private static final String QUEUE_NAME = "advertising.queue";
//    @Inject
//    private WebSocketPush webSocketPush;

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
//                websocketPush.sendMessage(message);
            }
        };

        channel.basicConsume(QUEUE_NAME, true, consumer);
    }

    public void stop() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    @Override
    public void onMessage(Message message) {
        log.info(message.toString());
    }
}
