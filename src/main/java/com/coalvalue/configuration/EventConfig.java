package com.coalvalue.configuration;

import com.coalvalue.task.InitTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.ExtendedState;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import reactor.bus.EventBus;

import java.util.Map;

@WithStateMachine
public class EventConfig {

    //private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EventBus eventBus;
    @Autowired
    private InitTasks initTasks;
/*

    @OnTransition(target = "UNPAID")
    public void create() {
       // logger.info("订单创建，待支付");

    }

    @OnTransition(source = "UNPAID", target = "WAITING_FOR_RECEIVE")
    public void pay() {
        //logger.info("用户完成支付，待收货");
    }

    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "DONE")
    public void receive() {
       // logger.info("用户已收货，订单完成");
    }
*/



    @OnTransition(source = "UNCONNECTED", target = "CONNECTED")

    public void connect(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("识别身份///////////////////");
        System.out.println("连接事件, 未连接 -> 已连接");
        System.out.println("///////////////////");
    }

    @OnTransition(source = "CONNECTED", target = "IDENTITING")
    public void identity(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("///////////////////");
        System.out.println("注册事件, 已连接 -> 识别中");
        System.out.println("///////////////////");


    }
    @OnTransition(source = "IDENTITING", target = "IDENTITED")
    public void identitySuccess(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("///////////////////");
        System.out.println("注册事件, 识别中 -> 已经识别");
        System.out.println("///////////////////");



    }


    @OnTransition(source = "IDENTITED", target = "REGISTERING")
    public void register_click(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("///////////////////");
        System.out.println("注册事件, 已识别 -> 注册中");
        System.out.println("///////////////////");
      //  initTasks.register();

    }

    @OnTransition(source = "REGISTERING", target = "REGISTERED")
    public void registerSuccess(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("///////////////////");
        System.out.println("注册成功事件, 注册中 -> 已注册");
        System.out.println("///////////////////");
        Map map = (Map)headers.get("map");



    }


    @OnTransition(source = "REGISTERED", target = "SYNCING")
    public void syncing(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("///////////////////");
        System.out.println("同步时间, 已注册 -> 同步中");
        System.out.println("///////////////////");


    }


    @OnTransition(source = "SYNCING", target = "SYNCED")
    public void synced(@EventHeaders Map<String, Object> headers, ExtendedState extendedState) {
        System.out.println("///////////////////");
        System.out.println("同步完成事件, 同步中 -> 已同步");
        System.out.println("///////////////////");

    }

    @OnTransition(source = "REGISTERED", target = "UNCONNECTED")
    public void unRegister() {
        System.out.println("///////////////////");
        System.out.println("注销事件, 已注册 -> 未连接");
        System.out.println("///////////////////");
    }






}

/*
作者：程序猿DD
链接：https://www.jianshu.com/p/326bd3ac2bf2
來源：简书
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。*/
