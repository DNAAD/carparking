package com.coalvalue.configuration;

import com.coalvalue.configuration.state.RegEventEnum;
import com.coalvalue.configuration.state.RegStatusEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;
import java.util.HashSet;

@Configuration
@EnableStateMachine
public class SimpleStateMachineConfiguration 
  extends StateMachineConfigurerAdapter<RegStatusEnum, RegEventEnum> {
 
    @Override
    public void configure(StateMachineStateConfigurer<RegStatusEnum, RegEventEnum> states)
      throws Exception {
  
        states
          .withStates()
          .initial(RegStatusEnum.UNCONNECTED)
        //  .end(RegStatusEnum.UNCONNECTED)
                .stateExit(RegStatusEnum.IDENTITED, initAction())
          .states(new HashSet<RegStatusEnum>(EnumSet.allOf(RegStatusEnum.class)));
      //      new HashSet<String>(Arrays.asList("S1", "S2", "S3")));



    }
 
    @Override
    public void configure(
      StateMachineTransitionConfigurer<RegStatusEnum, RegEventEnum> transitions)
      throws Exception {
  
        transitions.withExternal()
          .source(RegStatusEnum.UNCONNECTED).target(RegStatusEnum.CONNECTED).event(RegEventEnum.CONNECT)//.action(initAction())
                .and()
// 2.注册事件
// 已连接 -> 注册中
                .withExternal().source(RegStatusEnum.CONNECTED).target(RegStatusEnum.IDENTITING).event(RegEventEnum.IDENTITY)
                             .action(new Action<RegStatusEnum, RegEventEnum>() {
            @Override
            public void execute(StateContext<RegStatusEnum, RegEventEnum> context) {
              //  System.out.println("======================================IDENTITY");
               // context.getStateMachine().sendEvent("SECOND_EVENT");
            }
        }).and()
                // 2.注册事件
// 已连接 -> 注册中
                .withExternal().source(RegStatusEnum.IDENTITING).target(RegStatusEnum.IDENTITED).event(RegEventEnum.IDENTITY_SUCCESS).
                action(new Action<RegStatusEnum, RegEventEnum>() {
            @Override
            public void execute(StateContext<RegStatusEnum, RegEventEnum> context) {
                System.out.println("======================================IDENTITY_SUCCESS");
                 context.getStateMachine().sendEvent(RegEventEnum.REGISTER);
            }
        }).and()
// 2.注册事件
// 已连接 -> 注册中
                .withExternal()
                .source(RegStatusEnum.IDENTITED).target(RegStatusEnum.REGISTERING).event(RegEventEnum.REGISTER) .action(new Action<RegStatusEnum, RegEventEnum>() {
            @Override
            public void execute(StateContext<RegStatusEnum, RegEventEnum> context) {
                System.out.println("==IDENTITED ---> REGISTERING ====================================REGISTER");
                // context.getStateMachine().sendEvent("SECOND_EVENT");
                }
                }).


                and().withExternal()
          .source(RegStatusEnum.REGISTERING).target(RegStatusEnum.REGISTERED).event(RegEventEnum.REGISTER_SUCCESS).and()


                .withExternal()
                .source(RegStatusEnum.REGISTERED).target(RegStatusEnum.SYNCING).event(RegEventEnum.SYNC).and()




          .withExternal()
          .source(RegStatusEnum.CONNECTED).target(RegStatusEnum.UNCONNECTED).event(RegEventEnum.UN_REGISTER).and()
        .withExternal()
                .source(RegStatusEnum.REGISTERING).target(RegStatusEnum.UNCONNECTED).event(RegEventEnum.UN_REGISTER).and()
                .withExternal()
                .source(RegStatusEnum.REGISTERED).target(RegStatusEnum.UNCONNECTED).event(RegEventEnum.UN_REGISTER);
        //.and().withJoin();
    }


    @Bean
    public Action<RegStatusEnum, RegEventEnum> initAction() {

      //  ctx -> ctx.getStateMachine().sendEvent(RegEventEnum.REGISTER);
        return ctx -> System.out.println(ctx.getTarget().getId());
    }



    @Override
    public void configure(StateMachineConfigurationConfigurer<RegStatusEnum, RegEventEnum> config)
            throws Exception {
        config
                .withConfiguration()
                .listener(listener());
    }

    @Bean
    public StateMachineListener<RegStatusEnum, RegEventEnum> listener() {
        return new StateMachineListenerAdapter<RegStatusEnum, RegEventEnum>() {
            @Override
            public void stateChanged(State<RegStatusEnum, RegEventEnum> from, State<RegStatusEnum, RegEventEnum> to) {
                System.out.println("Order state changed to " + to.getId());
            }
            @Override
            public void transition(Transition<RegStatusEnum, RegEventEnum> transition) {
                if(transition.getTarget().getId() == RegStatusEnum.CONNECTED) {

        //            Student student = stateContext.getExtendedState().get(Student.class, Student.class);


                    System.out.println(" StateMachineListener 订单创建，待支付");
                    return;
                }
                if(transition.getTarget().getId() == RegStatusEnum.REGISTERING) {

                    //            Student student = stateContext.getExtendedState().get(Student.class, Student.class);


                    System.out.println(" StateMachineListener 订单创建，待支付REGISTERING  REGISTERING");
                    return;
                }

       /*         if(transition.getSource().getId() == RegStatusEnum.REGISTERED
                        && transition.getTarget().getId() == RegStatusEnum.CONNECTED) {
                    System.out.println("StateMachineListener 用户完成支付，待收货");
                    return;
                }

                if(transition.getSource().getId() == RegStatusEnum.UNCONNECTED
                        && transition.getTarget().getId() == RegStatusEnum.CONNECTED) {
                    System.out.println("StateMachineListener 用户已收货，订单完成");
                    return;
                }*/
            }

        };
    }

}