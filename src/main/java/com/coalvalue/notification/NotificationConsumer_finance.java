package com.coalvalue.notification;


import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Distributor;
import com.coalvalue.domain.entity.Employee;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.repository.EmployeeRepository;
import com.coalvalue.service.BehaviouralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.bus.Event;
import reactor.fn.Consumer;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class NotificationConsumer_finance implements Consumer<Event<NotificationData>> {
 



    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private BehaviouralService behaviouralService;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    public static String topic__COALPIT_DELIVERY_workbench = "/topic/COALPIT_DELIVERY_workbench/1";
    public static String topic__COALPIT_DELIVERY_report = "/topic/COALPIT_DELIVERY_report/1";
    @Override
    public void accept(Event<NotificationData> notificationDataEvent) {


        NotificationData notificationData = notificationDataEvent.getData();



        if(notificationDataEvent.getKey().equals(ReactorEventConfig.notificationConsumer_finance_Insufficient_account_balance_event)){

            ReportDeliveryOrder deliveryOrder_from =(ReportDeliveryOrder) notificationData.getObject();


            Distributor distributor = notificationData.getDistributor();


       //     List<Employee> employeeList = employeeRepository.findByCompanyId(distributor.getId());

            Map map = new HashMap();
            Map content = new HashMap();

            map.put("id", 34);

            map.put("type", "canvassing_add");

            content.put("distributor",deliveryOrder_from.getCompanyName());
            content.put("plateNumber",deliveryOrder_from.getLicense());
            content.put("productName",deliveryOrder_from.getProductName());

            map.put("content", content);




            System.out.println("behavioural---- " + ":" +ReactorEventConfig.notificationConsumer_finance_Insufficient_account_balance_event);
        }


    }
}