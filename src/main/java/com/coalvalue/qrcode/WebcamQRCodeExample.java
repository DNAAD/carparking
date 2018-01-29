package com.coalvalue.qrcode;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.coalvalue.service.CompanyService;
import com.coalvalue.service.QrcodeService;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WebcamQRCodeExample extends JFrame implements Runnable, ThreadFactory {

	private static final long serialVersionUID = 6441489157408381878L;

	private Executor executor = Executors.newSingleThreadExecutor(this);

	private Webcam webcam = null;
    private WebcamPanel panel = null;
	private JTextArea textarea = null;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    protected transient Logger logger = LoggerFactory.getLogger(WebcamQRCodeExample.class);

    @Autowired
    private QrcodeService qrcodeService;

/*    static {
        Webcam.setDriver(new IpCamDriver());
    }*/
	public WebcamQRCodeExample() {
        super("做好自uid！");
        //	super();


   //  CanvasFrame cFrame = new CanvasFrame("做好自uid！");
    setAlwaysOnTop(true);
       setVisible(true);











        setLayout(new FlowLayout());
		setTitle("Read QR / Bar Code With Webcam");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension size = WebcamResolution.XGA.getSize();

/*
        try {
            IpCamDeviceRegistry.register(new IpCamDevice("Lignano_", "rtsp://admin:silence110@192.168.30.119:554", IpCamMode.PUSH));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
*/


  // webcam =Webcam.getDefault();// Webcam.getWebcams().get(0);
  //webcam = Webcam.getWebcams().get(1);

        webcam = Webcam.getWebcams().get(0);


     //   Dimension[] myResolution = new Dimension[] { new Dimension(1920, 1080) };
        Dimension[] myResolution = new Dimension[] { new Dimension(1280, 720) };
 //       Webcam webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(myResolution);
        webcam.setViewSize(myResolution[0]);
   //   webcam.setViewSize(size);




        panel = new WebcamPanel(webcam);
		//panel.setPreferredSize(size);
		panel.setFPSDisplayed(true);

		textarea = new JTextArea();
		textarea.setEditable(false);
	//	textarea.setPreferredSize(size);

		add(panel);
		add(textarea);

		pack();
		setVisible(true);
pack();
		executor.execute(this);
	}

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {

                if ((image = webcam.getImage()) == null) {
                    continue;
                }

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    result = new MultiFormatReader().decode(bitmap);
                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
            }

            if (result != null) {


                textarea.setText(result.getText());

                qrcodeService.analyis(result.getText());
            }

        } while (true);
    }


/*
    @Override
    public void run() {
	do {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Result result = null;
			BufferedImage image = null;

			if (webcam.isOpen()) {

				if ((image = webcam.getImage()) == null) {
					continue;
				}

				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

				try {


					result = new MultiFormatReader().decode(bitmap);

                    qrcodeService.analyis(result.getText());

				} catch (NotFoundException e) {
					// fall thru, it means there is no QR code in image
				}
			}

			if (result != null) {
				textarea.setText(result.getText());
			}

		} while (true);*//*

    }

*/

    @Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "example-runner");
		t.setDaemon(true);
		return t;
	}

	public static void main(String[] args) {
		new WebcamQRCodeExample();
	}





    public  void recordPush(String inputFile,String outputFile,int v_rs) throws FrameGrabber.Exception, FrameRecorder.Exception, InterruptedException{
        Loader.load(opencv_objdetect.class);
        // 转换器，用于Frame/Mat/IplImage相互转换
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        // 使用OpenCV抓取本机摄像头，摄像头设备号默认0


        System.out.println("================================================ 打开 摄像头");
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        //   OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        // 开启抓取器
        grabber.start();
        System.out.println("=============开始");
        //做好自己 - - eguid!,转载请注明出处
        CanvasFrame cFrame = new CanvasFrame("做好自己！--eguid！", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        cFrame.setAlwaysOnTop(true);
        cFrame.setVisible(true);

        Frame frame = null;
        int index = 0;

        //   Mat logo = opencv_imgcodecs.imread("4ycfb.png");
        //   Mat mask = opencv_imgcodecs.imread("4ycfb.png", 0);

        // opencv_imgproc.threshold(mask,mask,25,25,opencv_imgcodecs.IMWRITE_PNG_BILEVEL);

        double alpha = 0.5;// 图像透明权重值,0-1之间

        Result result = null;
        BufferedImage image = null;

        while (cFrame.isShowing()) {
            if ( (frame=grabber.grab()) != null) {
                cFrame.showImage(frame);

                Java2DFrameConverter paintConverter = new Java2DFrameConverter();

                BufferedImage  bufferedImage = paintConverter.getBufferedImage(frame,1);

                LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {


                    result = new MultiFormatReader().decode(bitmap);

                    qrcodeService.analyis(result.getText());

                } catch (NotFoundException e) {
                    // fall thru, it means there is no QR code in image
                }
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


    }


    public void main_()
            throws FrameRecorder.Exception, FrameGrabber.Exception, InterruptedException {



    }
}