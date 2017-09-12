package com.stephen;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ProducerTest {

    public static void main(String[] args) {
        ConnectionFactory factory = new ActiveMQConnectionFactory();
        Session session = null;
        Connection connection = null;

        try {
            connection = factory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("zhangshirui");
            MessageProducer mp = session.createProducer(destination);
            mp.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage tm = session.createTextMessage("This is a test sentence");
            mp.send(tm);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            ClosableUtils.closeSession(session);
            ClosableUtils.closeConnection(connection);
        }
    }
}
