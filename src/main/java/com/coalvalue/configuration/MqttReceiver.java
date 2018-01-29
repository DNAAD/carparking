package com.coalvalue.configuration;

import com.alibaba.fastjson.JSON;
import com.coalvalue.domain.Distributor;
import com.coalvalue.domain.entity.Inventory;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.domain.entity.TransportOperation;
import com.coalvalue.enumType.DataSynchronizationTypeEnum;
import com.coalvalue.notification.NotificationData;
import com.coalvalue.service.*;
import com.iflytek.cloud.speech.*;
import com.iflytek.util.DebugLog;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MqttReceiver implements MqttCallback {
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    // 合成的文本内容
    private static String mText = "语音技术实现了人机语音交互，使人与机器之间沟通变得像人与人沟通一样简单。";
    private static Map<String, String[]> mVoiceMap = new LinkedHashMap<String, String[]>();
    private static Map<String, String> mParamMap = new HashMap<String, String>();

    private class DefaultValue{
        public static final String ENG_TYPE = SpeechConstant.TYPE_CLOUD;
        public static final String VOICE = "小燕";
        public static final String BG_SOUND = "0";
        public static final String SPEED = "50";
        public static final String PITCH = "50";
        public static final String VOLUME = "50";
        public static final String SAVE = "0";
    }
    static SpeechSynthesizer mTts;

    @Autowired
    MqttAsyncClient mqttClient;

  //  @Autowired
  //  MqttAsyncClient mqttClient;

    @Autowired
    TransportOperationService transportOperationService;

    @Autowired
    EventBus eventBus;


    @Autowired
    DistributorService distributorService;
    @Autowired
    InventoryService inventoryService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    AsynchronousDataSynchronizationService asynchronousDataSynchronizationService;




public MqttReceiver() {
}
   MqttClient client;
   static  {
        StringBuffer param = new StringBuffer();
        param.append( "appid=" + com.iflytek.util.Version.getAppid() );
		param.append( ","+SpeechConstant.LIB_NAME_32+"=myMscName" );
        SpeechUtility.createUtility(param.toString());
       if (SpeechSynthesizer.getSynthesizer() == null)
           SpeechSynthesizer.createSynthesizer();


       mTts = SpeechSynthesizer.createSynthesizer();

       initParamMap();

       initVoiceMap();
    }

/*public static void main(String[] args) {
    new MqttReceiver().doDemo();
}

public void doDemo() {
    try {
        client = new MqttClient("tcp://192.168.30.43:1883", "Sending");
        client.connect();
        client.setCallback(this);
        //client.subscribe("foo");
        MqttMessage message = new MqttMessage();
        message.setPayload("A single message from my computer fff"
                .getBytes());
        client.publish("foo", message);
    } catch (MqttException e) {
        e.printStackTrace();
    }
}*/




   // @Override
    public void connectComplete(boolean reconnect, java.lang.String serverURI){
        System.out.println("Connection " + reconnect);
       /* if(reconnect) {
            try {
                this.client.subscribe(topic, 1);
            } catch (MqttException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
    }




    private Object       connLock = new Object();
    private boolean      connLockNotified = false;
    private boolean      userConnect = true; // Is connect being initialted by the user?
    public void connectionLost() throws MqttException {
        boolean reconnected = false;

        synchronized( connLock ) {
            while( !reconnected && !connLockNotified ) {
                try {
                    mqttClient.connect();
                    reconnected = true;
                } catch( MqttException mqe ) {
                    // Some sort of WMQTT error has occurred - retry.
                    if ( userConnect ) {
                        // If the connect is initiated by the user feed the execption back to the API
                        throw mqe;
                    }
                    // An else block could display an error Alert panel on the device.
                }

                try {
                    connLock.wait( 2000 );
                } catch( InterruptedException ie ) {
                }
            }
            connLockNotified = false;
        }
/*
        if ( reconnected ) {
            try {
                String requestTopic[] = { REQ_TOPIC };
                mqttClient.subscribe( requestTopic, subQoS );

            } catch ( MqttException e ) {
            //    disconnectClient();
              //  destroyClient();
                throw e;
            }
        }*/

    }

    @Override
public void connectionLost(Throwable cause) {
    // TODO Auto-generated method stub
    System.out.println("Connection lost -=========== attempting reconnect.");
/*    try {
        mqttClient.connect();
    } catch (MqttException e) {
        e.printStackTrace();
    }*/
    /*    try {
            connectionLost();
        } catch (MqttException e) {
            e.printStackTrace();
            // We tried our best to reconnect
        }*/
}

@Override
public void messageArrived(String topic, MqttMessage message)
        throws Exception {

    try{
        cachedThreadPool.execute(new Runnable() {

            @Override
            public void run() {
                System.out.println("====处理收到信息=");
                String s =  new String(message.getPayload());

                Map map = JSON.parseObject(s, Map.class);

                String type = (String)map.get("type");
                asynchronousDataSynchronizationService.synchroniz(type,map);

                if(DataSynchronizationTypeEnum.Transport.getText().equals(type)){



                    String plateNumber =(String)map.get("plateNumber");
                    String traderName =(String)map.get("traderName");
                    String productCoalType =(String)map.get("productCoalType");

                    String inventoryNo =(String)map.get("inventoryNo");

                    String productGranularity =(String)map.get("productGranularity");
                    System.out.print("兴建提煤单");




                    String companyNo =(String)map.get("companyNo");

                    Distributor distributor = distributorService.getDistributor(companyNo,traderName);
                    ReportDeliveryOrder deliveryOrder = transportOperationService.createDeliveryOrder(distributor,map);


                    Inventory inventory = inventoryService.getInventory(inventoryNo,productCoalType,productGranularity);

                    NotificationData notificationData = new NotificationData();
                    //notificationData.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_EVENT);
                    notificationData.setObject(deliveryOrder);

                    notificationData.setInventory(inventory);
                    eventBus.notify("notificationConsumer", Event.wrap(notificationData));


                    setting();

                    mTts.startSpeaking( plateNumber, mSynListener );
                }else{

                    String msg =(String)map.get("content");
                    setting();

                    mTts.startSpeaking( msg, mSynListener );
                }




            }
        });


    }catch (Exception e){
        e.printStackTrace();
    }




 System.out.println("0000000000-000000000000000----------------------0"+message);
}




    public void messageVoicePlay(String topic)
            {

        setting();

        mTts.startSpeaking( topic, mSynListener );


        System.out.println("0000000000-000000000000000----------------------0"+topic);
    }

    @Override
public void deliveryComplete(IMqttDeliveryToken token) {
    // TODO Auto-generated method stub

}




    private static SynthesizerListener mSynListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
        }

        @Override
        public void onBufferProgress(int progress, int beginPos, int endPos,
                                     String info) {
            DebugLog.Log("--onBufferProgress--progress:" + progress
                    + ",beginPos:" + beginPos + ",endPos:" + endPos);
        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int progress, int beginPos, int endPos) {
            DebugLog.Log("onSpeakProgress enter progress:" + progress
                    + ",beginPos:" + beginPos + ",endPos:" + endPos);

            //   updateText( mText.substring( beginPos, endPos+1 ) );

            DebugLog.Log("onSpeakProgress leave");
        }

        @Override
        public void onCompleted(SpeechError error) {
            DebugLog.Log("onCompleted enter");

            String text = mText;
            if (null != error){
                DebugLog.Log("onCompleted Code：" + error.getErrorCode());
                text = error.getErrorDescription(true);
            }

            //   updateText( text );

            DebugLog.Log("onCompleted leave");
        }


        @Override
        public void onEvent(int eventType, int arg1, int arg2, int arg3, Object obj1, Object obj2) {
            if( SpeechEvent.EVENT_TTS_BUFFER == eventType ){
                DebugLog.Log("onEvent: type=" + eventType
                        + ", arg1=" + arg1
                        + ", arg2=" + arg2
                        + ", arg3=" + arg3
                        + ", obj2=" + (String) obj2);
                ArrayList<?> bufs = null;
                if( obj1 instanceof ArrayList<?> ){
                    bufs = (ArrayList<?>) obj1;
                }else{
                    DebugLog.Log("onEvent error obj1 is not ArrayList !");
                }//end of if-else instance of ArrayList

                if( null != bufs ){
                    for( final Object obj : bufs ){
                        if( obj instanceof byte[] ){
                            final byte[] buf = (byte[]) obj;
                            DebugLog.Log("onEvent buf length: " + buf.length);
                        }else{
                            DebugLog.Log("onEvent error element is not byte[] !");
                        }
                    }//end of for
                }//end of if bufs not null
            }//end of if tts buffer event
        }
    };
    private static void initParamMap(){
       mParamMap.put( SpeechConstant.ENGINE_TYPE, DefaultValue.ENG_TYPE );
       mParamMap.put( SpeechConstant.VOICE_NAME, DefaultValue.VOICE );
        mParamMap.put( SpeechConstant.BACKGROUND_SOUND, DefaultValue.BG_SOUND );
        mParamMap.put( SpeechConstant.SPEED, DefaultValue.SPEED );
        mParamMap.put( SpeechConstant.PITCH, DefaultValue.PITCH );
        mParamMap.put( SpeechConstant.VOLUME, DefaultValue.VOLUME );
        mParamMap.put( SpeechConstant.TTS_AUDIO_PATH, null );
    }



    void setting(){
        final String engType = this.mParamMap.get(SpeechConstant.ENGINE_TYPE);
        String voiceName = null;

        for( Map.Entry<String, String> entry : this.mParamMap.entrySet() ){
            String value = entry.getValue();
            System.out.println("-------------------------------- value {}"+ value + "  "+ entry.toString());
            if( SpeechConstant.VOICE_NAME.equals(entry.getKey()) ){
                String[] names = this.mVoiceMap.get( entry.getValue() );
                voiceName = value = SpeechConstant.TYPE_CLOUD.equals(engType) ? names[0] : names[1];
            }

            mTts.setParameter( entry.getKey(), value );
        }

        //本地合成时设置资源，并启动引擎
        if( SpeechConstant.TYPE_LOCAL.equals(engType) ){
            //启动合成引擎
            mTts.setParameter( ResourceUtil.ENGINE_START, SpeechConstant.ENG_TTS );
            //设置资源路径
            String curPath = System.getProperty("user.dir");
            DebugLog.Log("Current path=" + curPath);
            String resPath = ResourceUtil.generateResourcePath(curPath + "/tts/common.jet")
                    + ";" + ResourceUtil.generateResourcePath(curPath + "/tts/" + voiceName + ".jet");
            System.out.println( "resPath="+resPath );
            mTts.setParameter( ResourceUtil.TTS_RES_PATH, resPath );
        }// end of if is TYPE_LOCAL

        //启用合成音频流事件，不需要时，不用设置此参数
        mTts.setParameter( SpeechConstant.TTS_BUFFER_EVENT, "1" );
    }// end of function setting
    //初始化发音人镜像表，云端对应的本地
    //为了查找本地资源方便，请把资源文件置为发音人参数+.jet，如“xiaoyan.jet”
    static void  initVoiceMap(){
        mVoiceMap.clear();
        String[] names = null;

        names = new String[2];
        names[0] = names[1] = "xiaoyan";
        mVoiceMap.put( "小燕", names );	//小燕

        names = new String[2];
        names[0] = names[1] = "xiaoyu";
        mVoiceMap.put( "小宇", names );	//小宇

        names = new String[2];
        names[0] = "vixf"; names[1] = "xiaofeng";
        mVoiceMap.put( "小峰", names );	//小峰

        names = new String[2];
        names[0] = "vixm"; names[1] = "xiaomei";
        mVoiceMap.put( "小梅", names );	//小梅

        names = new String[2];
        names[0] = "vixr"; names[1] = "xiaorong";
        mVoiceMap.put( "小蓉", names );	//小蓉

        names = new String[2];
        names[0] = names[1] = "catherine";
        mVoiceMap.put( "凯瑟琳", names );	//凯瑟琳
    }

}