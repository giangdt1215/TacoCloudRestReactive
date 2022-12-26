package com.dtg.tacocloud.messaging;

import com.dtg.tacocloud.model.TacoOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService{

    private JmsTemplate jms;
    private Destination orderQueue;

    @Autowired
    public JmsOrderMessagingService(JmsTemplate jms, Destination orderQueue){
        this.jms = jms;
        this.orderQueue = orderQueue;
    }

    @Override
    public void sendOrder(TacoOrder order) {
//        jms.send(new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                return session.createObjectMessage(order);
//            }
//        });
        //Using convertAndSend with messagePostProcessor
        jms.convertAndSend(orderQueue, order, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
