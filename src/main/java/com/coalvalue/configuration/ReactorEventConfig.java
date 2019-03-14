package com.coalvalue.configuration;


import com.coalvalue.notification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import reactor.Environment;
import reactor.bus.EventBus;

import java.util.concurrent.CountDownLatch;

import static reactor.bus.selector.Selectors.$;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ReactorEventConfig implements CommandLineRunner {

    private static final int NUMBER_OF_QUOTES = 10;

    @Bean
    Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal();
    }


    @Lazy
    @Autowired
    private NotificationConsumer_quotation notificationConsumer_quotation;


    @Lazy
    @Autowired
    private NotificationConsumer notificationConsumer;
    @Lazy
    @Autowired
    private NotificationConsumer_sync notificationConsumer_sync;
    @Lazy
    @Autowired
    private NotificationConsumer_finance notificationConsumer_finance;
    @Lazy
    @Autowired
    private NotificationConsumer_PlateRecognition notificationConsumer_PlateRecognition;

    @Lazy
    @Autowired
    private NotificationConsumer_reconciliation notificationConsumer_reconciliation;

    @Lazy
    @Autowired
    private NotificationConsumer_register notificationConsumer_register;


    @Lazy
    @Autowired
    private NotificationConsumer_configuration notificationConsumer_configuration;


    @Lazy
    @Autowired
    private NotificationConsumer_image notificationConsumer_image;

    @Lazy
    @Autowired
    private NotificationConsumer_qrcode notificationConsumer_qrcode;
    @Lazy
    @Autowired
    private NotificationConsumer_loadometer notificationConsumer_loadometer;
    @Lazy
    @Autowired
    private NotificationConsumer_status notificationConsumer_status;

/*

    @Autowired
    private NotificationWeixinEventConsumer notificationWeixinClickConsumer;
*/

    @Bean
    EventBus createEventBus(Environment env) {

        return EventBus.create(env, Environment.THREAD_POOL);
    }




    public static   String notificationConsumer_create_delivery_order_event = "notificationConsumer_create_delivery_order_event";


    // TODO 出矿车辆 上榜
    public static   String notificationConsumer_create_instance_transport_out_on_weight_event = "notificationConsumer_create_instance_transport_on_weight_event";
    // TODO 入库车辆 上榜
    public static   String notificationConsumer_delivery_order_in_on_weight_event = "notificationConsumer_delivery_order_in_on_weight_event";

    // TODO 提煤单自动验证成功
    public static   String notificationConsumer_qrcode_delivery_order_verify_sucess_event = "notificationConsumer_qrcode_delivery_order_verify_sucess_event";


    // TODO 提煤单自动验证成功
    public static   String notificationConsumer_truck_verify_sucess_event = "notificationConsumer_qrcode_delivery_order_verify_sucess_event";


    public static   String notificationConsumer_report_event = "notificationConsumer_report_event";



    public static   String notificationConsumer_input_tareweight_event = "notificationConsumer_input_tareweight_event";
    public static   String notificationConsumer_input_netweight_event = "notificationConsumer_input_netweight_event";



    public static   String notificationConsumer_finance_event = "notificationConsumer_finance_event";
    public static   String notificationConsumer_finance_Insufficient_account_balance_event = "notificationConsumer_finance_Insufficient_account_balance_event";

    public static   String notificationConsumer_account_open_account = "notificationConsumer_account_open_account";

    public static   String notificationConsumer_plate_recognition_event = "notificationConsumer_plate_recognition_event";

    public static   String notificationConsumer_reconciliation_event = "notificationConsumer_reconciliation_event";






    public static   String notificationConsumer_register_event = "notificationConsumer_register_event";

    public static   String notificationConsumer_configuration_event = "notificationConsumer_configuration_event";
    public static   String notificationConsumer_sync_event = "notificationConsumer_sync_event";
    public static   String notificationConsumer_sync_compare_than_sync_event = "notificationConsumer_sync_compare_than_sync_event";


    public static   String notificationConsumer_sync_response_event = "notificationConsumer_sync_response_event";

    public static   String notificationConsumer_syncImmediately_event = "notificationConsumer_syncImmediately_event";
    public static   String notificationConsumer_image_create_event = "notificationConsumer_image_create_event";
    public static   String notificationConsumer_qrcode_create_event = "notificationConsumer_qrcode_create_event";


    public static   String notificationConsumer_follow_event = "notificationConsumer_follow_event";

    public static   String notificationConsumer_delivery_order_authorize_complete_event = "notificationConsumer_delivery_order_authorize_complete_event";


    public static   String notificationConsumer_change_price_group_quotation_pre_event = "notificationConsumer_change_price_group_quotation_pre_event";
    public static   String notificationConsumer_status_online_event = "notificationConsumer_status_online_event";
    public static   String notificationConsumer_status_offline_event = "notificationConsumer_status_offline_event";
    @Bean
	public CountDownLatch latch() {
		return new CountDownLatch(NUMBER_OF_QUOTES);
	}

	@Override
	public void run(String... args) throws Exception {
		//eventBus.on($("quotes"), receiver);
        EventBus eventBus = createEventBus(env());
        eventBus.on($(notificationConsumer_create_delivery_order_event), notificationConsumer);
        eventBus.on($(notificationConsumer_report_event), notificationConsumer);

        eventBus.on($(notificationConsumer_input_netweight_event), notificationConsumer);
        eventBus.on($(notificationConsumer_input_tareweight_event), notificationConsumer);



        eventBus.on($(notificationConsumer_follow_event), notificationConsumer);


        ;
        eventBus.on($(notificationConsumer_finance_event), notificationConsumer_finance);
        eventBus.on($(notificationConsumer_finance_Insufficient_account_balance_event), notificationConsumer_finance);

        eventBus.on($(notificationConsumer_plate_recognition_event), notificationConsumer_PlateRecognition);
        eventBus.on($(notificationConsumer_delivery_order_in_on_weight_event), notificationConsumer_loadometer);
        eventBus.on($(notificationConsumer_create_instance_transport_out_on_weight_event), notificationConsumer_loadometer);

        eventBus.on($(notificationConsumer_reconciliation_event), notificationConsumer_reconciliation);

        eventBus.on($(notificationConsumer_register_event), notificationConsumer_register);

        eventBus.on($(notificationConsumer_configuration_event), notificationConsumer_configuration);

        eventBus.on($(notificationConsumer_image_create_event), notificationConsumer_image);


        eventBus.on($(notificationConsumer_qrcode_create_event), notificationConsumer_qrcode);
        eventBus.on($(notificationConsumer_delivery_order_authorize_complete_event), notificationConsumer);


        eventBus.on($(notificationConsumer_sync_event), notificationConsumer_sync);
        eventBus.on($(notificationConsumer_sync_response_event), notificationConsumer_sync);
        eventBus.on($(notificationConsumer_sync_compare_than_sync_event), notificationConsumer_sync);
        eventBus.on($(notificationConsumer_syncImmediately_event), notificationConsumer_sync);

        eventBus.on($(notificationConsumer_change_price_group_quotation_pre_event), notificationConsumer_quotation);



        eventBus.on($(notificationConsumer_status_online_event), notificationConsumer_status);
        eventBus.on($(notificationConsumer_status_offline_event), notificationConsumer_status);


/*
        eventBus.on($("notificationWeixinClickConsumer"), notificationWeixinClickConsumer);
        eventBus.on($("notificationWeixinTextConsumer"), notificationWeixinTextConsumer);
*/

        //publisher.publishQuotes(NUMBER_OF_QUOTES);
	}



}