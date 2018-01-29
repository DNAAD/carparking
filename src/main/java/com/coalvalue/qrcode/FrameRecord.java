package com.coalvalue.qrcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;
import org.bytedeco.javacv.FrameGrabber.Exception;

import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 调用本地摄像头窗口视频 
 * @author eguid   
 * @version 2016年6月13日   
 * @see javavcCameraTest   
 * @since  javacv1.2 
 */
  
public class FrameRecord
{
    private static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    public static void recordPush(String inputFile,String outputFile,int v_rs) throws Exception, FrameRecorder.Exception, InterruptedException{
        Loader.load(opencv_objdetect.class);
        // 转换器，用于Frame/Mat/IplImage相互转换
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        // 使用OpenCV抓取本机摄像头，摄像头设备号默认0

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
     //   OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        // 开启抓取器
        grabber.start();
        //做好自己 - - eguid!,转载请注明出处
        CanvasFrame cFrame = new CanvasFrame("做好自己！--eguid！", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        cFrame.setAlwaysOnTop(true);
        cFrame.setVisible(true);
        // 水印文字位置
        Point point = new Point(10, 50);
        // 颜色，使用黄色
        Scalar scalar = new Scalar(0, 255, 255, 0);
        Frame frame = null;
        int index = 0;

     //   Mat logo = opencv_imgcodecs.imread("4ycfb.png");
     //   Mat mask = opencv_imgcodecs.imread("4ycfb.png", 0);

       // opencv_imgproc.threshold(mask,mask,25,25,opencv_imgcodecs.IMWRITE_PNG_BILEVEL);

        double alpha = 0.5;// 图像透明权重值,0-1之间
        while (cFrame.isShowing()) {
            if ( (frame=grabber.grab()) != null) {
                cFrame.showImage(frame);

                Java2DFrameConverter paintConverter = new Java2DFrameConverter();

                BufferedImage  bufferedImage = paintConverter.getBufferedImage(frame,1);

                long startTime= System.currentTimeMillis();

                LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));




                Result result = null;
                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {

                    // fall thru, it means there is no QR code in image
                }

                long endTime= System.currentTimeMillis();
                System.out.println("==========time= "+(startTime - endTime));
                if (result != null) {
                  //  qrcodeService.analyis(result.getText());
                }
            }

            if (false && (frame=grabber.grab()) != null) {
                //cFrame.showImage(frame);
                // 取一帧视频（图像），并转换为Mat
               Mat mat = converter.convertToMat(frame);


             /*   opencv_objdetect.HOGDescriptor hog = new opencv_objdetect.HOGDescriptor();
                hog.winSize(new Size(448, 280));
                hog.blockSize(new Size(48, 24));
                hog.blockStride(new Size(8, 8));
                hog.cellSize(new Size(24, 12));
                hog.nbins(9);


                FloatPointer etector=hog.getDefaultPeopleDetector();

                if(etector.capacity() == 0);
                {System.out.println("No detector");

                }
                hog.setSVMDetector(mat);*/

                // 加文字水印，opencv_imgproc.putText（图片，水印文字，文字位置，字体，字体大小，字体颜色，字体粗度，平滑字体，是否翻转文字）
        //        opencv_imgproc.putText(mat, "eguid!", point, opencv_imgproc.CV_FONT_VECTOR0, 1.2, scalar, 1, 20, false);
                // 定义感兴趣区域(位置，logo图像大小)
             //   Mat ROI = mat.apply(new Rect(40, 35, logo.cols(), logo.rows()));

               // opencv_core.addWeighted(ROI, alpha, logo, 1.0 - alpha, 0.0, ROI);
                // 把logo图像复制到感兴趣区域
//				 logo.copyTo(ROI, mask);
                // 显示图像到窗口
               // cFrame.showImage(converter.convert(mat));
                if (index == 0) {
                    // 保存第一帧图片到本地
                //    opencv_imgcodecs.imwrite("eguid.jpg", mat);
                }
                // 释放Mat资源
              //  ROI.release();
            //    ROI.close();
                mat.release();
               mat.close();
                Thread.sleep(40);
                index++;
            }

            index++;
        }
        // 关闭窗口
        cFrame.dispose();
        // 停止抓取器
        grabber.stop();
        // 释放资源
      //  logo.release();
       // logo.close();
     //   mask.release();
    //    mask.close();
        scalar.close();
        point.close();

    }

    public static BufferedImage IplImageToBufferedImage(opencv_core.IplImage src) {
        OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
        Java2DFrameConverter paintConverter = new Java2DFrameConverter();
        Frame frame = grabberConverter.convert(src);
        return paintConverter.getBufferedImage(frame,1);
    }
    public static void main(String[] args)
            throws FrameRecorder.Exception, Exception, InterruptedException {

/*        //String inputFile = "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0";
        String inputFile = "rtsp://admin:silence110@192.168.30.119:554";


        // Decodes-encodes
        String outputFile = "recorde.mp4";


      //  String inputFile = "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0";

       // String outputFile="rtmp://192.168.30.21/live/pushFlow";

        recordPush(inputFile, outputFile,25);
      //  frameRecord(inputFile, outputFile,25);*/


        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {

                    System.out.println("==============executorService============================== ");
                    //String inputFile = "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0";
                    String inputFile = "rtsp://admin:silence110@192.168.30.119:554";


                    // Decodes-encodes
                    String outputFile = "recorde.mp4";


                    //  String inputFile = "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0";

                    // String outputFile="rtmp://192.168.30.21/live/pushFlow";

                    recordPush(inputFile, outputFile,25);
                    //  frameRecord(inputFile, outputFile,25);
                } catch (java.lang.Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}