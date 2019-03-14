package com.coalvalue.python.plate;

import com.coalvalue.configuration.ReactorEventConfig;
import com.coalvalue.domain.entity.Equipment;
import com.coalvalue.domain.entity.InstanceTransport;
import com.coalvalue.domain.entity.PlateRecognition;
import com.coalvalue.domain.entity.ReportDeliveryOrder;
import com.coalvalue.enumType.EquipmentTypeEnum;
import com.coalvalue.enumType.PlateDirectionEnum;

import com.coalvalue.notification.NotificationData_loadometer;

import com.coalvalue.repository.PlateRecognitionRepository;
import com.coalvalue.service.DeliveryOrderService;
import com.coalvalue.service.EquipmentService;
import com.coalvalue.service.InstanceTransportService;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.Event;
import reactor.bus.EventBus;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.*;
import java.util.Base64;

import static reactor.bus.selector.Selectors.$;

/**
 * Servlet implementation class PlateServlet /devicemanagement/php/plateresult.php
 * /devicemanagement/php/receivedeviceinfo.php
 */
@WebServlet("/plateServlet") //description = "a enter for wechat", urlPatterns = { "/aaa"},loadOnStartup=1

public class PlateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    @Autowired
    PlateRecognitionRepository plateRecognitionRepository;

    @Autowired
    DeliveryOrderService deliveryOrderService;


    @Autowired
    InstanceTransportService instanceTransportService;



    @Autowired
    EventBus eventBus;
    @Autowired
    EquipmentService equipmentService;


    /**
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public PlateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        // 回复命令，控制设备开闸
        response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        out.println("{\"Response_AlarmInfoPlate\":{\"info\":\"ok\",\"content\":\"...\",\"is_pay\":\"true\"}}");
        out.flush();
        out.close();
    }

    public static String deCode(String str) {
        try {
            byte[] b = str.getBytes("gbk");//编码
            String sa = new String(b, "utf-8");//解码:用什么字符集编码就用什么字符集解码

            return sa;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static boolean SaveFile(byte[] content, String path, String imgName) {
        FileOutputStream writer = null;
        boolean result = false;
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            writer = new FileOutputStream(new File(path, imgName));
            writer.write(content);
            result = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        System.out.println("{\"Response_AlarmInfoPlate\":{\"info\":\"ok\",\"content\":\"...\",\"is_pay\":\"true\"}}");

        StringBuffer jb = new StringBuffer();
        // JSONObject jsonObject;
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        // 把接收到车牌结果保存到txt文件中
        WriteTxt("d:\\plate_result.txt", jb.toString());

        try
        {
            JsonParser parser=new JsonParser();  //创建JSON解析器

            do
            {
                JsonObject jsonObject=(JsonObject) parser.parse(jb.toString());
                if( jsonObject == null || jsonObject.isJsonNull() )
                {
                    break;
                }

                // 解析AlarmInfoPlate
                JsonObject jsonInfoPlate = jsonObject.get("AlarmInfoPlate").getAsJsonObject();
                if( jsonInfoPlate == null || jsonInfoPlate.isJsonNull() )
                {
                    break;
                }

                // 解析result
                JsonObject jsonResult = jsonInfoPlate.get("result").getAsJsonObject();

                String serialno = jsonInfoPlate.get("serialno").getAsString();

                Equipment equipment = equipmentService.createEquipment(serialno, EquipmentTypeEnum.License_Plate_Capture_Cameras);


                if( jsonResult == null || jsonResult.isJsonNull() )
                {
                    break;
                }

                // 解析PlateResult
                JsonObject jsonPlateResult = jsonResult.get("PlateResult").getAsJsonObject();
                System.out.println("存储车牌号：PlateResult"+jsonPlateResult.toString());
                if( jsonPlateResult == null || jsonPlateResult.isJsonNull() )
                {System.out.println("存储车牌号：is NULL NULL");
                    break;
                }
                Integer colorType = jsonPlateResult.get("colorType").getAsInt();
                Integer direction = jsonPlateResult.get("direction").getAsInt();
                String imagePath = jsonPlateResult.get("imagePath").getAsString();
                String license = jsonPlateResult.get("license").getAsString();
                String triggerType = jsonPlateResult.get("triggerType").getAsString();
                String type = jsonPlateResult.get("type").getAsString();

                System.out.println("存储车牌号：direction"+direction);
                System.out.println("存储车牌号：license"+license);

                String bright = jsonPlateResult.get("bright")!= null?jsonPlateResult.get("bright").getAsString(): "";

                System.out.println("存储车牌号：bright"+bright);


                System.out.println("存储车牌号：is NULL NULL");

                String carColor = jsonPlateResult.get("carColor")!= null? jsonPlateResult.get("carColor").getAsString():"";
                String colorValue = jsonPlateResult.get("colorValue")!= null? jsonPlateResult.get("colorValue").getAsString():"";


                String confidence = jsonPlateResult.get("confidence")!= null? jsonPlateResult.get("confidence").getAsString():"";

                //String license = jsonPlateResult.get("license").getAsString();
                String location = jsonPlateResult.get("location")!= null? jsonPlateResult.get("location").getAsString():"";

                String timeStamp = jsonPlateResult.get("timeStamp")!= null? jsonPlateResult.get("timeStamp").getAsString():"";


                String timeUsed = jsonPlateResult.get("timeUsed")!= null? jsonPlateResult.get("timeUsed").getAsString():"";





                System.out.println("存储车牌号：2");

                // 获取车牌号

                createPlateRecognition(equipment, license,direction,confidence,carColor);











                if( license == null || license == "" )
                {
                    break;
                }

                // String decode_license = deCode(license);
                WriteTxt("d:\\plate_num.txt", license);

                // 获取车牌图片
                String plateImageData = jsonPlateResult.get("imageFragmentFile").getAsString();
                if (plateImageData == null || plateImageData == "")
                {
                    break;
                }

                // 解码后保存文件
                byte[] plateImgBytes = Base64.getDecoder().decode(plateImageData);
                SaveFile(plateImgBytes, "d:\\", "img_clip.jpg");

                // 获取全景图片
                String imageData = jsonPlateResult.get("imageFile").getAsString();
                if (imageData == null || imageData == "")
                {
                    break;
                }

                // 解码后保存文件
                byte[] decoderBytes = Base64.getDecoder().decode(imageData);
                SaveFile(decoderBytes, "d:\\", "img_full.jpg");

            }while(false);
        }
        catch (JsonIOException e)
        {
            e.printStackTrace();
        }
        catch (JsonSyntaxException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {

        }


        doGet(request, response);
    }



    @Transactional
    private PlateRecognition createPlateRecognition(Equipment equipment, String license, Integer direction, String confidence, String carColor) {

        PlateRecognition plateRecognition = new PlateRecognition();

        plateRecognition.setDeviceId(equipment.getDeviceId());

        plateRecognition.setDirection(PlateDirectionEnum.fromInt(direction).getText());

        plateRecognition.setLicense(license);
        plateRecognition.setColourCode(1);
        plateRecognition.setPath("");

        System.out.println("存储车牌号 createPlateRecognition："+plateRecognition.toString());
        System.out.println("存储车牌号 createPlateRecognition："+plateRecognition.getDirection());
        plateRecognition = plateRecognitionRepository.save(plateRecognition);
        if(plateRecognition.getDirection().equals(PlateDirectionEnum.down.getText())){
            System.out.println("存down储车牌号 createPlateRecdownognition："+plateRecognition.getDirection());
            ReportDeliveryOrder reportDeliveryOrder = deliveryOrderService.getValidByLicense(plateRecognition.getLicense());
            NotificationData_loadometer notificationData = new NotificationData_loadometer();
            notificationData.setObject(plateRecognition);
            notificationData.setDeliveryOrder(reportDeliveryOrder);
            eventBus.notify(ReactorEventConfig.notificationConsumer_delivery_order_in_on_weight_event, Event.wrap(notificationData));

        }
        System.out.println("存储车牌号 createPlateRecognition："+plateRecognition.getDirection());
        if(plateRecognition.getDirection().equals(PlateDirectionEnum.up.getText())){
            System.out.println("存up储车牌号 createPlateRecognition："+plateRecognition.getDirection());
            InstanceTransport instanceTransport = instanceTransportService.getLoadingByLicense(plateRecognition.getLicense());
            NotificationData_loadometer notificationData = new NotificationData_loadometer();
            notificationData.setObject(plateRecognition);
            notificationData.setInstanceTransport(instanceTransport);
            eventBus.notify(ReactorEventConfig.notificationConsumer_create_instance_transport_out_on_weight_event, Event.wrap(notificationData));
        }




        return plateRecognition;

    }

    protected void WriteTxt( String path, String txt )
    {
        try
        {
            FileWriter  f = new FileWriter(path);
            BufferedWriter bw=new BufferedWriter(f);
            bw.write(txt);
            bw.close();
        }
        catch(Exception e)
        {
        }
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

}
