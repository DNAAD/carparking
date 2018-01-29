package com;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coalvalue.configuration.MqttReceiver;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.enumType.PlateDirectionEnum;
import com.coalvalue.service.PlateRecognitionService;
import com.sun.jna.Pointer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;



public class LPRMain {

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


    static LPR lpr = LPR.INSTANCE;


    public LPRMain(PlateRecognitionService plateRecognitionService){
    //    this.mqttReceiver = mqttReceiver;
        this.plateRecognitionService = plateRecognitionService;

        System.out.println("开始存储车牌号："+plateRecognitionService);
        try{
            cachedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    boolean b = InitClient();

                    while(!b){
                        b = InitClient();
                    }

                    // plateRecognitionService.createPlateRecognition();
                }
            });

            System.out.println("已经生产线程 存储车牌号："+plateRecognitionService);

        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private PlateRecognitionService plateRecognitionService;



    MqttReceiver mqttReceiver =new MqttReceiver();
    VZLPRC_PLATE_INFO_CALLBACK PlateCallBack = new VZLPRC_PLATE_INFO_CALLBACK(plateRecognitionService);
    public static void main(String[] args) throws Exception {
      //  LPRMain lprtest = new LPRMain();
    }

    //@PostConstruct
    public static void plate() {

        System.out.println("START----------------- plate");
      //  LPRMain lprtest = new LPRMain();
      //  InitClient();
        System.out.println("END----------------- plate");

    }
    private boolean InitClient(){

        try{
            lpr.VzLPRClient_Setup();
            String ip = "192.168.30.214";
            String admin = "admin";
            String password = "admin";



            int handle = lpr.VzLPRClient_Open(ip, 80, admin, password);
            if(handle == 0){
                System.out.println("打开失败");

                return false;
            }


            else
                System.out.println("打开成功");

            System.out.print("====开始存储车牌号 线程=========================================");


            int callbackindex = lpr.VzLPRClient_SetPlateInfoCallBack(handle, PlateCallBack, Pointer.NULL, 1);
            // System.out.println(callbackindex);


            AddWlistPlate( handle );

            Scanner input = new Scanner(System.in);

            int end = input.nextInt();
            System.out.println(end);






        }
        catch(Exception e)
        {
            System.out.println("Error:"+e);
        }
        return true;
    }

    // ��Ӱ�����
    private int AddWlistPlate( int handle )
    {
        int ret = 0;

        try
        {
            LPR.VZ_LPR_WLIST_VEHICLE.ByReference wlistVehicle = new LPR.VZ_LPR_WLIST_VEHICLE.ByReference();

            wlistVehicle.strPlateID[0] = '1';
            wlistVehicle.strPlateID[1] = '2';
            wlistVehicle.strPlateID[2] = '3';
            wlistVehicle.strPlateID[3] = '4';
            wlistVehicle.strPlateID[4] = '5';

            wlistVehicle.uCustomerID	= -1;
            wlistVehicle.bEnable		= 1;


            LPR.VZ_TM.ByReference struTMOverdule = new LPR.VZ_TM.ByReference();
            struTMOverdule.nYear	= 2015;
            struTMOverdule.nMonth	= 12;
            struTMOverdule.nMDay	= 30;
            struTMOverdule.nHour	= 12;
            struTMOverdule.nMin		= 40;
            struTMOverdule.nSec		= 50;

            wlistVehicle.pStruTMOverdule	= struTMOverdule;
            wlistVehicle.bUsingTimeSeg	= 0;
            wlistVehicle.bAlarm			= 1;

            wlistVehicle.strCode[0] = '1';
            wlistVehicle.strCode[1] = '2';
            wlistVehicle.strCode[2] = '3';
            wlistVehicle.strCode[3] = '4';
            wlistVehicle.strCode[4] = '5';

            LPR.VZ_LPR_WLIST_ROW.ByReference wlistRow	= new LPR.VZ_LPR_WLIST_ROW.ByReference();
            wlistRow.pVehicle = wlistVehicle;
            wlistRow.pCustomer = null;
            LPR.VZ_LPR_WLIST_IMPORT_RESULT.ByReference importResult = new LPR.VZ_LPR_WLIST_IMPORT_RESULT.ByReference();
            ret = lpr.VzLPRClient_WhiteListImportRows(handle, 1, wlistRow, importResult);
        }
        catch(Exception e)
        {
            System.out.println("Error:"+e);
        }

        return ret;
    }

    public static String deCode_new_GB2312(byte[] str) {
        try {
            String stirng = new String(str, "GB2312");
            return java.net.URLDecoder.decode(stirng, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String deCode_WITHOUT_docode(byte[] str) {

        String stirng = null;
        try {
            stirng = new String(str,"GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stirng;

    }
    public static String deCode(byte[] str) {
        try {
            String stirng = new String(str);
            return java.net.URLDecoder.decode(stirng, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
    public class VZLPRC_PLATE_INFO_CALLBACK implements LPR.VZLPRC_PLATE_INFO_CALLBACK
    {
        PlateRecognitionService prs = null;
        public VZLPRC_PLATE_INFO_CALLBACK(PlateRecognitionService plateRecognitionService) {
            System.out.println("======================= dakai fuzhi fuzhi a a a ");
            this.prs = plateRecognitionService;
        //    System.out.println(this.prs.toString());
        }

        public void invoke(int handle,Pointer pUserData,LPR.TH_PlateResult_Pointer.ByReference pResult,int uNumPlates,
                           int eResultType,LPR.VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgFull,LPR.VZ_LPRC_IMAGE_INFO_Pointer.ByReference pImgPlateClip)
        {
            int type = LPR.VZ_LPRC_RESULT_TYPE.VZ_LPRC_RESULT_REALTIME.ordinal();
            if(eResultType != type)
            {
                //String license = new String(pResult.license,"GB2312");
              //  String license = new String(pResult.license);
                String licenseres = deCode_WITHOUT_docode(pResult.license).trim();
                String licenseres_SHOW = deCode_new_GB2312(pResult.license).trim();
              //  System.out.println(license);
             //   System.out.println(licenseres);
  /*              try {
                    byte[] license_byte = pResult.license;
                    String stirng = new String(license_byte,"GB2312");
                    System.out.println(stirng);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
*/
                String path_ = "E:/" + pResult.struBDTime.bdt_year + pResult.struBDTime.bdt_mon
                        + pResult.struBDTime.bdt_mday + pResult.struBDTime.bdt_hour
                        + pResult.struBDTime.bdt_min + pResult.struBDTime.bdt_sec
                        + licenseres_SHOW + ".jpg";
                int SaveRet_ = lpr.VzLPRClient_ImageSaveToJpeg(pImgFull,path_, 100);


             System.out.println("开始存储车牌号："+plateRecognitionService);
                try{
                    cachedThreadPool.execute(new Runnable() {

                        @Override
                        public void run() {
                            System.out.print("====开始存储车牌号 线程=========================================");
                            PlateRecognition plateRecognition = plateRecognitionService.createPlateRecognition(pResult,path_);
                            plateRecognitionService.analyis(plateRecognition);


                        }
                    });

                    System.out.println("已经生产线程 存储车牌号："+plateRecognitionService);

                }catch (Exception e){
                    e.printStackTrace();
                }

               // this.prs.createPlateRecognition(pResult);

                String path = "E:/" + pResult.struBDTime.bdt_year + pResult.struBDTime.bdt_mon
                        + pResult.struBDTime.bdt_mday + pResult.struBDTime.bdt_hour
                        + pResult.struBDTime.bdt_min + pResult.struBDTime.bdt_sec
                        + licenseres_SHOW + ".jpg";

                String path_SHOW = "E:/" + pResult.struBDTime.bdt_year + pResult.struBDTime.bdt_mon
                        + pResult.struBDTime.bdt_mday + pResult.struBDTime.bdt_hour
                        + pResult.struBDTime.bdt_min + pResult.struBDTime.bdt_sec
                        + licenseres_SHOW + ".jpg";


                String gStr= null;
                String gb = null;
                try {
                    gStr = new String(path.getBytes("UTF-8"), "GB2312");


                gb = new String("e:\\我的爱人.jpg".getBytes("8859_1"),"GB2312");

                  //  b = utf8_value.getBytes("8859_1"); //中间用ISO-8859-1过渡

                 //   String name = new String(b, "GB2312"); //转换成GB2312字符
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
              //  gb="e:\\我的爱人.jpg";
                System.out.print(gb);




                try {
                    gStr = new String(path.getBytes("UTF-8"), "GB2312");
                    gb = new String("e:\\我的爱人.jpg".getBytes("8859_1"),"GB2312");
                    //  b = utf8_value.getBytes("8859_1"); //中间用ISO-8859-1过渡
                    //   String name = new String(b, "GB2312"); //转换成GB2312字符
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.print(gb);
                int SaveRet = lpr.VzLPRClient_ImageSaveToJpeg(pImgFull,gb, 100);




                mqttReceiver.messageVoicePlay(licenseres_SHOW);
                System.out.println("返回信息："+SaveRet);
                System.out.println("返回信息："+path_SHOW);

            }

            type = LPR.VZ_LPRC_RESULT_TYPE.VZ_LPRC_RESULT_VLOOP_TRIGGER.ordinal();
            if(false && eResultType != type)
            {
                //String license = new String(pResult.license,"GB2312");
                //  String license = new String(pResult.license);
                String licenseres = deCode(pResult.license).trim();
                //  System.out.println(license);
                //   System.out.println(licenseres);
  /*              try {
                    byte[] license_byte = pResult.license;
                    String stirng = new String(license_byte,"GB2312");
                    System.out.println(stirng);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
*/
                String path = "E:/" + pResult.struBDTime.bdt_year + pResult.struBDTime.bdt_mon
                        + pResult.struBDTime.bdt_mday + pResult.struBDTime.bdt_hour
                        + pResult.struBDTime.bdt_min + pResult.struBDTime.bdt_sec
                        + licenseres + ".jpg";
                int SaveRet = lpr.VzLPRClient_ImageSaveToJpeg(pImgFull, path, 100);

                System.out.println("虚拟线圈触发的识别结果："+SaveRet);
                System.out.println("虚拟线圈触发识别结果："+path);

            }

            type = LPR.VZ_LPRC_RESULT_TYPE.VZ_LPRC_RESULT_STABLE.ordinal();
            if(false && eResultType != type)
            {
                //String license = new String(pResult.license,"GB2312");
                //  String license = new String(pResult.license);
                String licenseres = deCode(pResult.license).trim();
                //  System.out.println(license);
                //   System.out.println(licenseres);
  /*              try {
                    byte[] license_byte = pResult.license;
                    String stirng = new String(license_byte,"GB2312");
                    System.out.println(stirng);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
*/
                String path = "E:/" + pResult.struBDTime.bdt_year + pResult.struBDTime.bdt_mon
                        + pResult.struBDTime.bdt_mday + pResult.struBDTime.bdt_hour
                        + pResult.struBDTime.bdt_min + pResult.struBDTime.bdt_sec
                        + licenseres + ".jpg";
                int SaveRet = lpr.VzLPRClient_ImageSaveToJpeg(pImgFull, path, 100);

                System.out.println("稳定识别结果："+SaveRet);
                System.out.println("稳定识别结果："+path);

            }
        }
    }

}
