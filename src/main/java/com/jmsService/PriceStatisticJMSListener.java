package com.jmsService;


import com.coalvalue.enumType.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Component
public class PriceStatisticJMSListener extends JmsBaseService implements MessageListener  {




    public JmsTemplate getJmsTemplate() {
        return getJmsTemplate();
    }
        public void onMessage(Message message) {
            try {


                MapMessage mapMessage = (MapMessage) message;//jmsTemplate.receive(destination);

                EventEnum event = EventEnum.fromString(mapMessage.getString("event"));
                PerformanceStatisticFunctionEnum function = PerformanceStatisticFunctionEnum.fromString(mapMessage.getString("function"));

                logger.debug("event is {}, function is {} ", event.getDisplayText(), function.getDisplayText());
                if(event.equals(EventEnum.SOLR_CREATE_PRODUCT)){
                //    instance_price_change_rank(mapMessage);

                }
                if(event.equals(EventEnum.CAPACITY_APPLY_CREATE)){
                  //  add_path(mapMessage);

                }

                message.acknowledge();
            }
            catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        }



/*    public Employee receiveMessage() throws JMSException {
        Map map = (Map) getJmsTemplate().receiveAndConvert();
        return new Employee((String) map.get("name"), (Integer) map.get("age"));
    }*/



}