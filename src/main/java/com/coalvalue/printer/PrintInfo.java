package com.coalvalue.printer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.DigestException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by silence on 2018-04-12.
 */
public class PrintInfo {


    /**
     * 检查是否有纸指令
     */
    public static final byte[] stateBype2 = new byte[] { 0x1B, 0x76 };
    /**
     * 居左对齐
     */
    public static final Byte[] toLeft = new Byte[] { 0x1B, 0x61, 0x00 };
    /**
     * 居中对齐
     */
    public static final Byte[] toCenter = new Byte[] { 0x1B, 0x61, 0x01 };
    /**
     * 加大2倍
     */
    public static final Byte[] toLarge = new Byte[] { 0x1D, 0x21, 0x11 };
    /**
     * 取消加大
     */
    public static final Byte[] cancleLarge = new Byte[] { 0x1D, 0x21, 0x00 };
    /**
     * 加粗
     */
    public static final Byte[] toLarge2 = new Byte[] { 0x1B, 0x45, 0x01 };
    /**
     * 取消加粗
     */
    public static final Byte[] cancleLarge2 = new Byte[] { 0x1B, 0x45, 0x00 };

    /**
     * 设置单元格大小
     */
    public static final Byte[] setCodeSize = new Byte[] { 0x1D, 0x28, 0x6B, 0x30, 0x67, 0x07 };
    /**
     * 设置纠错正等级
     */
    public static final Byte[] setCodeLevel = new Byte[] { 0x1D, 0x28, 0x6B, 0x30, 0x69, 0x48 };
    /**
     * 加载二维码
     */
// public static final Byte[] setCode2 = new Byte[] { 0x1D, 0x28, 0x6B, 0x30, (byte) 0x80, 0x55, 0x00 };
    public static Byte[] setCode = new Byte[7];
    /**
     * 打印二维码
     */
    public static final Byte[] printCode = new Byte[] { 0x1D, 0x28, 0x6B, 0x30, (byte) 0x81 };

    /**
     * 加载走纸命令
     */
    public static final Byte[] cut = new Byte[] { 0x1D, 0x56, 0x42, 0x00 };// 切纸并且走纸

    /**
     * 设置加载二维码指令
     * @param code
     */
    public static void doSetCode(String code) {
        PrintInfo.setCode[0] = 0x1D;
        PrintInfo.setCode[1] = 0x28;
        PrintInfo.setCode[2] = 0x6B;
        PrintInfo.setCode[3] = 0x30;
        PrintInfo.setCode[4] = (byte) 0x80;
        PrintInfo.setCode[5] = (byte) code.length();
        PrintInfo.setCode[6] = 0x00;
    }

    /**
     * 合并两个byte数组
     *
     * @param byte_1
     * @param byte_2
     * @return
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    /**
     * int[]转byte[]
     * @param arg
     * @return
     */
    public static byte[] intTobyte(int arg[]) {
        byte[] byteTmp = new byte[arg.length];
        for (int i = 0; i < arg.length; i++) {
            byteTmp[i] = (byte)arg[i];
        }
        return byteTmp;
    }

    /**
     * byte转Byte
     *
     * @param srcArray
     * @param cpyArray
     */
    public static void CopyArray(byte[] srcArray, Byte[] cpyArray) {
        for (int index = 0; index < cpyArray.length; index++) {
            cpyArray[index] = srcArray[index];
        }
    }

    /**
     * List<Byte>转换为byte[]
     * @param ByteArray
     * @return
     */
    public static byte[] convertFromListByteArrayTobyteArray(
            List<Byte> ByteArray) {
        byte[] byteArray = new byte[ByteArray.size()];
        for (int index = 0; index < byteArray.length; index++) {
            byteArray[index] = ByteArray.get(index);
        }

        return byteArray;
    }

    /**
     * 去重复
     * @param li
     * @return
     */


    public static final int PORT_CHECKPRINT = 4000;
    public static final int PORT_PRINT = 9100;
    public static final int TIMEOUT = 10000;

    public static List<String> removeDuplicateWithOrder(List<String> li) {
        List<String> list = new ArrayList<String>();
        for(int i=0; i<li.size(); i++){
            String str = li.get(i); //获取传入集合对象的每一个元素
            if(!list.contains(str)){ //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list; //返回集合
    }

    public static void main(String[] args) {


        System.out.print(doCheckJiaboPaperState());



        //组装数据
        ArrayList<Byte> list = new ArrayList<Byte>();
        Byte[] tempList = null;
        byte[] data = null;

//title部分
/*        list.addAll(Arrays.asList(PrintInfo.toLarge));
        list.addAll(Arrays.asList(PrintInfo.toCenter));
        try {
            data = "草鱼".getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tempList = new Byte[data.length];
        PrintInfo.CopyArray(data, tempList);
        list.addAll(Arrays.asList(tempList));
        list.addAll(Arrays.asList(PrintInfo.cancleLarge));*/


        list.addAll(Arrays.asList(PrintInfo.toCenter));
        list.addAll(Arrays.asList(PrintInfo.setCodeSize));
        list.addAll(Arrays.asList(PrintInfo.setCodeLevel));

        PrintInfo.doSetCode("ddddddddddddddddddd");
        list.addAll(Arrays.asList(PrintInfo.setCode));
        try {
            data = ("ddddddddddddddddddd"+"\r\n\r\n").getBytes("GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        tempList = new Byte[data.length];
        PrintInfo.CopyArray(data, tempList);
        list.addAll(Arrays.asList(tempList));
        list.addAll(Arrays.asList(PrintInfo.printCode));

// 加载切纸指令
        list.addAll(Arrays.asList(PrintInfo.cut));

        Socket socketPrint = null;
        try {
            socketPrint = new Socket();
            InetSocketAddress isa = new InetSocketAddress("192.168.123.100", PORT_PRINT);
            socketPrint.connect(isa, TIMEOUT);
            OutputStream bos = socketPrint.getOutputStream();
//向打印机发送数据
            byte[] bt = PrintInfo.convertFromListByteArrayTobyteArray(list);
            bos.write(bt, 0, bt.length);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketPrint.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        list = null;
        tempList = null;
        data = null;
       // bt = null;


    }
/*    **
            * 佳博打印机，检查纸的状态：
            * 当接收状态为20时，表示网络打印机是正常状态；接收状态为28(无纸)或60(没关盖)时，表示不正常状态
    */
    private static boolean doCheckJiaboPaperState() {
        boolean flag = false;
        Socket socket2 = null;
        try {
            socket2 = new Socket();
            InetSocketAddress isa = new InetSocketAddress("192.168.123.100", PORT_CHECKPRINT);
            socket2.connect(isa, TIMEOUT);
            BufferedInputStream bis = new BufferedInputStream(socket2.getInputStream());
            OutputStream bos = socket2.getOutputStream();
            bos.write(PrintInfo.stateBype2);
            bos.flush();
            int tmp = bis.read();
            //Common.showDeBug(tmp);
            if (tmp == 20) {
                flag = true;
            } else {
                flag = false;
            }
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            try {
                if (socket2 != null) {
                    socket2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

}
