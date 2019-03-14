package com.coalvalue.printer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

/**
 * Created by silence on 2018-03-29.
 */
public class Printer {


    public static void main(String[] args) {



        Socket client = null;
        PrintWriter oStream = null;
        try {
            client = new Socket("192.168.123.100", 9100);
            oStream = new PrintWriter((new OutputStreamWriter(client.getOutputStream(),"GBK")),true);

            qrCode(oStream, "dddddddddddddd");
            //居中
            oStream.write(27);
            oStream.write(97);
            oStream.write(1);
            oStream.flush();
            oStream.println("发票");
            //取消居中
            oStream.write(27);
            oStream.write(97);
            oStream.write(0);
            oStream.flush();

            oStream.print("客户名：测试\t");
            oStream.print("电话：12333\n");
            oStream.print("地址：测试地址");
            oStream.print("地址：测试地址\n\n\n\n\n");
            oStream.flush();
            for (int i = 0; i < getCutPaperByte().length; i++) {
                oStream.write(getCutPaperByte()[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(oStream!=null){
                    oStream.close();
                }
                if(client!=null){
                    client.close();
                }
            }catch(Exception e){
            }
        }
    }

    /**
     * 打印二维码
     *
     * @param qrData 二维码的内容
     * @throws IOException
     */
    public static void qrCode(PrintWriter writer, String qrData) throws IOException {
        int moduleSize = 8;
      int length = qrData.getBytes("GBK").length;

        //打印二维码矩阵
        writer.write(0x1D);// init
        writer.write("(k");// adjust height of barcode
        writer.write(length + 3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(80); // fn
        writer.write(48); //
        writer.write(qrData);

        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);
        writer.write(0);
        writer.write(49);
        writer.write(69);
        writer.write(48);

        writer.write(0x1D);
        writer.write("(k");
        writer.write(3);
        writer.write(0);
        writer.write(49);
        writer.write(67);
        writer.write(moduleSize);

        writer.write(0x1D);
        writer.write("(k");
        writer.write(3); // pl
        writer.write(0); // ph
        writer.write(49); // cn
        writer.write(81); // fn
        writer.write(48); // m
        writer.flush();

    }
    /**切纸命令*/
    public static byte[] getCutPaperByte(){
        byte [] buffer = new byte[5];
        buffer[0]='\n';//命令必须是单行
        buffer[1]=29;
        buffer[2]=86;
        buffer[3]=66;
        buffer[4]=1;
        return buffer;
    }











/*

    */
/**
     * 佳博蓝牙打印机，检查纸的状态：
     * 当接收状态为18时，表示蓝牙打印机是正常状态；接收状态为114(无纸)或114(没关盖)时，表示不正常状态
     *//*

    public int doCheckJiaboPaperState() {
        */
/**1：正常，0：异常，-1：链接失败*//*

        int flag = 0;
        try {
            InputStream bis = bluetoothSocket.getInputStream();
            outputStream.write(BlueThPrintInfo.stateBype);
            outputStream.flush();
            int tmp = bis.read();
            if (tmp == 18) {
                flag = 1;
            } else {
                flag = 0;
            }
        } catch (Exception e) {
            flag = -1;
            e.printStackTrace();
        }
        return flag;
    }
*/


    Pos pos = null;
    private void pos() {
        // 开启一个子线程
        new Thread() {
            public void run() {
                try {              //打印机网口IP    打印机端口号     编码方式
                   // pos = new Pos(Preference.instance(PayTypeActivity.this).getIPAddress(), Integer.parseInt(Preference.instance(PayTypeActivity.this).getPost_IDs()), "gbk");
                    //初始化打印机
                    pos.initPos();
/*                    if (!TextUtils.isEmpty(objBean.getHead_img())){
                        pos.printLocation(1);
                        bitmap = pos.compressPic(returnBitMap(objBean.getHead_img()));
                        pos.draw2PxPoint(bitmap);
                    }*/
                    pos.printLocation(1);
                    pos.bold(true);
                    pos.printTabSpace(2);
                    pos.printWordSpace(1);
                    //pos.printTextNewLine(objBean.getShop_name());

                    pos.printLocation(0);
                    pos.printTextNewLine("----------------------------------------------");
                    pos.bold(false);
/*                    pos.printTextNewLine("订 单 号："+objBean.getSn());
                    pos.printTextNewLine("用 户 名："+objBean.getMember());
                    pos.printTextNewLine("订单日期："+objBean.getCreate_time());
                    pos.printTextNewLine("支付方式："+objBean.getPay_way());
                    pos.printTextNewLine("订单备注："+objBean.getRemarks());*/
                    pos.printLine(2);

                    pos.printText("货号          尺码    颜色    数量   小计");
                    pos.printLocation(20, 1);
                    pos.printTextNewLine("----------------------------------------------");

/*                    for (ParentBean foods : foodsBean) {
                        pos.printTextNewLine(foods.getGoods_name()+"            "+foods.getCode_number()+"     "+foods.getColour()+"      "+foods.getNum()+"      "+foods.getPay_back());
                        pos.printLocation(20, 1);
                    }*/

                    pos.printTextNewLine("----------------------------------------------");
                    pos.printLocation(0);
                    pos.printLine(1);
                    //pos.printTextNewLine("总数："+objBean.getNums());
                    //pos.printTextNewLine("合计："+objBean.getPay());
                    pos.printLine(2);

//                    //打印二维码  -- 如果提供了二维码的地址则用该方法
//                    pos.qrCode(objBean.getQr_code());

                    //打印二维码的图片 -- 如果提供了二维码的截图则用该方法
/*                    if (!TextUtils.isEmpty(objBean.getQr_code())){
                        pos.printLocation(1);
                        Qbitmap = pos.compressPic(returnBitMap(objBean.getQr_code()));//url 转换为bitmap
                        pos.draw2PxPoint(Qbitmap);
                    }*/
                    //切纸
                    pos.feedAndCut();
                    pos.closeIOAndSocket();
                    pos = null;
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    //Log.d("tag", "错误信息1：" + e.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    //Log.d("tag", "错误信息2：" + e.toString());
                }
            }

        }.start();

    }


/*
    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) { e.printStackTrace(); }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
*/

}
