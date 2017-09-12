package com.stephen;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class ConsumerTest implements MessageListener {

    Connection connection;
    Session session;

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getId());
        ConsumerTest ct = new ConsumerTest();
        ct.receive();
    }

    public void receive() {

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory();
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("zhangshirui");
            MessageConsumer mc = session.createConsumer(destination);
            mc.setMessageListener(this);
            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        ClosableUtils.closeConnection(connection);
        ClosableUtils.closeSession(session);
    }

    // 不是同一个线程
    @Override
    public void onMessage(Message message) {
        System.out.println(Thread.currentThread().getId());
        if (message instanceof TextMessage) {
            TextMessage tm = (TextMessage) message;
            try {
                System.out.println(tm.getText());
            } catch (JMSException e) {
                e.printStackTrace();
                close();
            }
        }
        close();
    }
}
