package com.coalvalue.printer;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Pos {
    //定义编码方式
    private static String encoding = null;

    private Socket sock = null;
    // 通过socket流进行读写
    private OutputStream socketOut = null;
    private OutputStreamWriter writer = null;

    /**
     * 初始化Pos实例
     *
     * @param ip 打印机IP
     * @param port 打印机端口号
     * @param encoding 编码
     * @throws IOException
     */
    public Pos(String ip, int port, String encoding) throws IOException {
        sock = new Socket(ip, port);
        socketOut = new DataOutputStream(sock.getOutputStream());
        this.encoding = encoding;
        writer = new OutputStreamWriter(socketOut, encoding);
    }

    //蓝牙初始化
    public Pos(OutputStream outputStream, String encoding) throws IOException {
        writer = new OutputStreamWriter(outputStream, encoding);
        socketOut = outputStream;
        initPrinter();
    }

    public void print(byte[] bs) throws IOException {
        socketOut.write(bs);
    }

    public void printRawBytes(byte[] bytes) throws IOException {
        socketOut.write(bytes);
        socketOut.flush();
    }

    /**
     * 初始化打印机
     *
     * @throws IOException
     */
    public void initPrinter() throws IOException {
        socketOut.write(0x1B);
        socketOut.write(0x40);
        socketOut.flush();
    }
    /**
     * 关闭IO流和Socket
     *
     * @throws IOException
     */
    public void closeIOAndSocket() throws IOException {
        writer.close();
        socketOut.close();
        sock.close();
    }


  /*  *//**
     * 二维码排版对齐方式
     *
     * @param position
     *            0：居左(默认) 1：居中 2：居右
     * @param moduleSize
     *            二维码version大小
     * @return
     * @throws IOException
     *//*
    private EscPos alignQr(int position, int moduleSize) throws IOException {
        writer.write(0x1B);
        writer.write(97);
        if (position == 1) {
            writer.write(1);
            centerQr(moduleSize);
        } else if (position == 2) {
            writer.write(2);
            rightQr(moduleSize);
        } else {
            writer.write(0);
        }
        return this;
    }
*/
    /**
     * 打印二维码
     *
     * @param qrData
     * @return
     * @throws IOException
     */
    private void qrCode(Integer position, String qrData) throws IOException {
        int moduleSize = 0;
        int length = qrData.getBytes(encoding).length;
        int l = (int) (Math.ceil(1.5 * length) * 8);
        if (l < 200) {
            moduleSize = 1;
        } else if (l < 429) {
            moduleSize = 2;
        } else if (l < 641) {
            moduleSize = 3;
        } else if (l < 885) {
            moduleSize = 4;
        } else if (l < 1161) {
            moduleSize = 5;
        } else if (l < 1469) {
            moduleSize = 6;
        }

       // alignQr(position, moduleSize);

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


    /**
     * 打印二维码
     *
     * @param qrData 二维码的内容
     * @throws IOException
     */
    public void qrCode(String qrData) throws IOException {
        int moduleSize = 8;
        int length = qrData.getBytes(encoding).length;

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

    /**
     * 进纸并全部切割
     *
     * @return
     * @throws IOException
     */
    public void feedAndCut() throws IOException {
        writer.write(0x1D);
        writer.write(86);
        writer.write(65);
        //    writer.write(0);
        //切纸前走纸多少
        writer.write(100);
        writer.flush();

        //另外一种切纸的方式
        //    byte[] bytes = {29, 86, 0};
        //    socketOut.write(bytes);
    }

    /**
     * 打印换行
     *
     * @return length 需要打印的空行数
     * @throws IOException
     */
    public void printLine(int lineNum) throws IOException {
        for (int i = 0; i < lineNum; i++) {
            writer.write("\n");
        }
        writer.flush();
    }

    /**
     * 打印换行(只换一行)
     *
     * @throws IOException
     */
    protected void printLine() throws IOException {
        writer.write("\n");
        writer.flush();
    }

    /**
     * 打印空白(一个Tab的位置，约4个汉字)
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printTabSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            writer.write("\t");
        }
        writer.flush();
    }

    /**
     * 打印空白（一个汉字的位置）
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printWordSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            writer.write(" ");
        }
        writer.flush();
    }

    /**
     * 打印位置调整
     *
     * @param position 打印位置 0：居左(默认) 1：居中 2：居右
     * @throws IOException
     */
    public void printLocation(int position) throws IOException {
        writer.write(0x1B);
        writer.write(97);
        writer.write(position);
        writer.flush();
    }

    /**
     * 绝对打印位置
     *
     * @throws IOException
     */
    public void printLocation(int light, int weight) throws IOException {
        writer.write(0x1B);
        writer.write(0x24);
        writer.write(light);
        writer.write(weight);
        writer.flush();
    }

    /**
     * 打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printText(String text) throws IOException {
        String s = text;
        byte[] content = s.getBytes("gbk");
        socketOut.write(content);
        socketOut.flush();
    }

    /**
     * 新起一行，打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printTextNewLine(String text) throws IOException {
        //换行
        writer.write("\n");
        writer.flush();

        String s = text;
        byte[] content = s.getBytes("gbk");
        socketOut.write(content);
        socketOut.flush();
    }

    /**
     * 初始化打印机
     *
     * @throws IOException
     */
    public void initPos() throws IOException {
        writer.write(0x1B);
        writer.write(0x40);
        writer.flush();
    }

    /**
     * 加粗
     *
     * @param flag false为不加粗
     * @return
     * @throws IOException
     */
    public void bold(boolean flag) throws IOException {
        if (flag) {
            //常规粗细
            writer.write(0x1B);
            writer.write(69);
            writer.write(0xF);
            writer.flush();
        } else {
            //加粗
            writer.write(0x1B);
            writer.write(69);
            writer.write(0);
            writer.flush();
        }
    }


/**
     * 对图片进行压缩（去除透明度）
     *
     * @param
     *//*

    public static Bitmap compressPic(Bitmap bitmap) {
        // 获取这个图片的宽和高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 指定调整后的宽度和高度
        int newWidth = 200;
        int newHeight = 200;
        Bitmap targetBmp = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
        Canvas targetCanvas = new Canvas(targetBmp);
        targetCanvas.drawColor(0xffffffff);
        targetCanvas.drawBitmap(bitmap, new Rect(0, 0, width, height), new Rect(0, 0, newWidth, newHeight), null);
        return targetBmp;
    }
*/

  /*  *//**
     * 灰度图片黑白化，黑色是1，白色是0
     *
     * @param x   横坐标
     * @param y   纵坐标
     * @param bit 位图
     * @return
     *//*
    public static byte px2Byte(int x, int y, Bitmap bit) {
        if (x < bit.getWidth() && y < bit.getHeight()) {
            byte b;
            int pixel = bit.getPixel(x, y);
            int red = (pixel & 0x00ff0000) >> 16; // 取高两位
            int green = (pixel & 0x0000ff00) >> 8; // 取中两位
            int blue = pixel & 0x000000ff; // 取低两位
            int gray = RGB2Gray(red, green, blue);
            if (gray < 128) {
                b = 1;
            } else {
                b = 0;
            }
            return b;
        }
        return 0;
    }*/

    /**
     * 图片灰度的转化
     */
    private static int RGB2Gray(int r, int g, int b) {
        int gray = (int) (0.29900 * r + 0.58700 * g + 0.11400 * b);  //灰度转化公式
        return gray;
    }

  /*  *//*************************************************************************
     * 假设一个240*240的图片，分辨率设为24, 共分10行打印
     * 每一行,是一个 240*24 的点阵, 每一列有24个点,存储在3个byte里面。
     * 每个byte存储8个像素点信息。因为只有黑白两色，所以对应为1的位是黑色，对应为0的位是白色
     **************************************************************************//*
    *//**
     * 把一张Bitmap图片转化为打印机可以打印的字节流
     *
     * @param bmp
     * @return
     *//*
    public void draw2PxPoint(Bitmap bmp) throws IOException {

        //用来存储转换后的 bitmap 数据。为什么要再加1000，这是为了应对当图片高度无法
        //整除24时的情况。比如bitmap 分辨率为 240 * 250，占用 7500 byte，
        //但是实际上要存储11行数据，每一行需要 24 * 240 / 8 =720byte 的空间。再加上一些指令存储的开销，
        //所以多申请 1000byte 的空间是稳妥的，不然运行时会抛出数组访问越界的异常。
        int size = bmp.getWidth() * bmp.getHeight() / 8 + 1000;
        byte[] data = new byte[size];
        int k = 0;
        //设置行距为0的指令
        data[k++] = 0x1B;
        data[k++] = 0x33;
        data[k++] = 0x00;
        // 逐行打印
        for (int j = 0; j < bmp.getHeight() / 24f; j++) {
            //打印图片的指令
            data[k++] = 0x1B;
            data[k++] = 0x2A;
            data[k++] = 33;
            data[k++] = (byte) (bmp.getWidth() % 256); //nL
            data[k++] = (byte) (bmp.getWidth() / 256); //nH
            //对于每一行，逐列打印
            for (int i = 0; i < bmp.getWidth(); i++) {
                //每一列24个像素点，分为3个字节存储
                for (int m = 0; m < 3; m++) {
                    //每个字节表示8个像素点，0表示白色，1表示黑色
                    for (int n = 0; n < 8; n++) {
                        byte b = px2Byte(i, j * 24 + m * 8 + n, bmp);
                        data[k] += data[k] + b;
                    }
                    k++;
                }
            }
            data[k++] = 10;//换行
        }
        socketOut.write(data);
        socketOut.flush();
    }*/

    public static void main(String[] args) {


        System.out.print(doCheckJiaboPaperState());


    }
    public static void main__(String[] args) {


        doCheckJiaboPaperState();








        Socket client = null;
        PrintWriter oStream = null;
        try {

            Pos pos = new Pos("192.168.123.100", 9100, "GBK");
            //初始化打印机
            pos.initPos();

            //初始化订单数据
           // initData();

/*            pos.bold(true);
            pos.printTabSpace(2);
            pos.printWordSpace(1);
            pos.printText("**测试店铺");

            pos.printLocation(0);
            pos.printTextNewLine("----------------------------------------------");
            pos.bold(true);

            pos.printTextNewLine("订 单 号：1005199");
            pos.bold(false);
            pos.printTextNewLine("用 户 名：15712937281");
            pos.bold(true);
            pos.printTextNewLine("桌    号：3号桌");
            pos.printTextNewLine("订单状态：订单已确认");
            pos.printTextNewLine("订单日期：2016/2/19 12:34:53");
            pos.printTextNewLine("付 款 人：线下支付（服务员：宝哥）");
            pos.printTextNewLine("服 务 员：1001");
            pos.printTextNewLine("订单备注：不要辣，少盐");
            pos.printLine(2);

            pos.printText("品项");
            pos.printLocation(20, 1);
            pos.printText("单价");
            pos.printLocation(99, 1);
            pos.printWordSpace(1);
            pos.printText("数量");
            pos.printWordSpace(3);
            pos.printText("小计");
            pos.printTextNewLine("----------------------------------------------");




            pos.printTextNewLine("----------------------------------------------");

            pos.printLocation(1);
            pos.printLine(2);*/
            //打印二维码
            pos.qrCode("http://blog.csdn.net/haovip123");
            pos.qrCode(null,"ddddddddddddddd");
            //切纸
            pos.feedAndCut();

            pos.closeIOAndSocket();
            pos = null;
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
     * 检查是否有纸指令
     */
    public static final byte[] stateBype2 = new byte[] { 0x1B, 0x76 };
    /**
     * 佳博打印机，检查纸的状态：
     * 当接收状态为20时，表示网络打印机是正常状态；接收状态为28(无纸)或60(没关盖)时，表示不正常状态
     */
    private static boolean doCheckJiaboPaperState() {
        boolean flag = false;
        Socket socket2 = null;
        try {
            socket2 = new Socket();
            InetSocketAddress isa = new InetSocketAddress("192.168.123.100",9100);
            socket2.connect(isa, 200);
            System.out.println("------------------");
            BufferedInputStream bis = new BufferedInputStream(socket2.getInputStream());
            OutputStream bos = socket2.getOutputStream();
            bos.write(Pos.stateBype2);
            bos.flush();
            int tmp = bis.read();
            System.out.println("------------------"+tmp);
           // Common.showDeBug(tmp);
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