package hello;

import com.coalvalue.statemachine.Events;
import com.coalvalue.statemachine.States;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class StatesFirstApplicationTests {

    @Autowired
    private StateMachine<States, Events> stateMachine;

  //  @Test
    public void initTest() {
        Assertions.assertThat(stateMachine.getState().getId())
                  .isEqualTo(States.BACKLOG);

        Assertions.assertThat(stateMachine).isNotNull();
    }

  //  @Test
    public void testGreenFlow() {
        // Arrange
        // Act
        stateMachine.sendEvent(Events.START_FEATURE);



        Message<Events> message = MessageBuilder
                .withPayload(Events.START_FEATURE)
                .setHeader("ORDER_ENTITY_KEY", "order")
                .build();





        stateMachine.sendEvent(message);


        stateMachine.sendEvent(Events.FINISH_FEATURE);
        stateMachine.sendEvent(Events.QA_TEAM_APPROVE);
        // Asserts
        Assertions.assertThat(stateMachine.getState().getId())
                .isEqualTo(States.DONE);
    }
}