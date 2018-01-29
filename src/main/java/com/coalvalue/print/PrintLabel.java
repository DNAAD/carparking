package com.coalvalue.print;

import org.apache.commons.lang3.StringUtils;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * 打印序列号
 * @author happyqing
 * 2015.5.28
 */
public class PrintLabel {
    /**
	 * 加载打印Dll，打印接口
	 *
	 */
	public interface TscLibDll extends Library {
		//把TSCLIB.dll发到JAVA_HOME\bin下
	    TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary ("TSCLIB", TscLibDll.class);
//	    TscLibDll INSTANCE = (TscLibDll) Native.loadLibrary ("C:\\Windows\\System32\\TSCLIB.dll", TscLibDll.class);
	    int about ();
	    int openport (String pirnterName);
	    int closeport ();
	    int sendcommand (String printerCommand);
	    int setup (String width,String height,String speed,String density,String sensor,String vertical,String offset);
	    int downloadpcx (String filename,String image_name);
	    int barcode (String x,String y,String type,String height,String readable,String rotation,String narrow,String wide,String code);
	    int printerfont (String x,String y,String fonttype,String rotation,String xmul,String ymul,String text);
	    int clearbuffer ();
	    int printlabel (String set, String copy);
	    int formfeed ();
	    int nobackfeed ();
	    int windowsfont (int x, int y, int fontheight, int rotation, int fontstyle, int fontunderline, String szFaceName, String content);
	}
	
	//厂商提供测试方法
	public static void mainTest(String[] args) {
        //TscLibDll.INSTANCE.about();
        TscLibDll.INSTANCE.openport("TSC TTP-2410M");
        //TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
        TscLibDll.INSTANCE.sendcommand("REM ***** This is a test by JAVA. *****");
        TscLibDll.INSTANCE.setup("100", "100", "5", "8", "0", "0", "0");
        TscLibDll.INSTANCE.clearbuffer();
        //TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
        TscLibDll.INSTANCE.printerfont ("100", "10", "3", "0", "1", "1", "(JAVA) DLL Test!!");
        TscLibDll.INSTANCE.barcode("100", "40", "128", "50", "1", "0", "2", "2", "123456789");
        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 0, 3, 1, "arial", "DEG 0");
        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 90, 3, 1, "arial", "DEG 90");
        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 180, 3, 1, "arial", "DEG 180");
        TscLibDll.INSTANCE.windowsfont(400, 200, 48, 270, 3, 1, "arial", "DEG 270");
        TscLibDll.INSTANCE.printlabel("1", "1");
        TscLibDll.INSTANCE.closeport();
    }
	
	/**
	 * 打印序列号
	 * @param serialno
	 * @throws Exception
	 */
	public static void printSerialno(String serialno,String printer) throws Exception{
		//TscLibDll.INSTANCE.about();
//      TscLibDll.INSTANCE.openport("TSC TTP-2410M");
		//TscLibDll.INSTANCE.openport("\\\\happy\\Gprinter  GP-3150TN");
		//TscLibDll.INSTANCE.openport("Gprinter  GP-3150TN");
//		if(StringUtils.isEmpty(printer)){
//			throw new ServiceException("未配置序列号打印机！");
//		}
		TscLibDll.INSTANCE.openport(printer);
		//TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
//      TscLibDll.INSTANCE.sendcommand("REM ***** This is a test by JAVA. *****");
		TscLibDll.INSTANCE.setup("50", "20", "5", "5", "0", "3", "0");
		TscLibDll.INSTANCE.clearbuffer();
		//TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");
//      TscLibDll.INSTANCE.printerfont ("50", "10", "3", "0", "1", "1", "(JAVA) DLL Test!!");
      	TscLibDll.INSTANCE.barcode("30", "50", "128", "70", "1", "0", "2", "2", serialno); //"A-123456789" 314-235-984
	    
	    TscLibDll.INSTANCE.printlabel("1", "1");
	    TscLibDll.INSTANCE.closeport();
    
	}
	
	/**
	 * 打印标签（含序列号）
	 * @param dto
	 * @throws Exception
	 */
	public static void printLabel(Dto dto,String printer) throws Exception{
		//TscLibDll.INSTANCE.about();
		//TscLibDll.INSTANCE.openport("\\\\happy\\Gprinter  GP-3150TN");
		//TscLibDll.INSTANCE.openport("Gprinter  GP-3150TN");
//		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.APP);
//		String printer = pHelper.getValue("print70");
//		if(StringUtils.isEmpty(printer)){
//			throw new ServiceException("未配置标签打印机！");
//		}
		TscLibDll.INSTANCE.openport(printer);
		TscLibDll.INSTANCE.setup("50", "70", "5", "5", "0", "3", "0");
		TscLibDll.INSTANCE.clearbuffer();
		
	    TscLibDll.INSTANCE.windowsfont(85, 40, 40, 0, 2, 0, "宋体", dto.getAsString("projectname")); //x y 字体高度 旋转 字体外形 底线 字体 打印文字内容
	    //TscLibDll.INSTANCE.printerfont ("10", "50", "TSS24.BF2", "0", "2", "2", "中国建设银行");
	    //TscLibDll.INSTANCE.windowsfont(10, 100, 40, 0, 0, 1, "宋体", "      ");
	    
	    TscLibDll.INSTANCE.sendcommand("BAR 20,85,380,2"); //x,y,width,height
	    
	    int y = 110;
	    TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", "报修电话：" + dto.getAsString("repairphone")); //x y 字体高度 旋转 字体外形 底线 字体 打印文字内容
	    //TscLibDll.INSTANCE.windowsfont(170, 100, 25, 0, 2, 0, "宋体", "400-6504866"); //x y 字体高度 旋转 字体外形 底线 字体 打印文字内容
	    //TscLibDll.INSTANCE.sendcommand("BAR 160,130,220,2"); //x,y,width,height
	    
	    y = y + 45;
	    TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", "授权电话：" + dto.getAsString("grantphone"));	    
	    y = y + 45;
	    TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", "投诉电话：" + dto.getAsString("complaintphone"));
	    
	    
	    String merchantname = dto.getAsString("merchantname");//"中国建中国建中国建中国建中国建中国建建";
	    String nom =dto.getAsString("nom");// 设备或商户编号的值
	    String nomName =dto.getAsString("nomName");// 设备或商户编号字段名称
	    
	    if(merchantname.length()<=9){
	    	y = y + 45;
	    	TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", "商户名称：" + merchantname);
	    } else {
	    	y = y + 45;
	    	TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", "商户名称：" + merchantname.substring(0, 9));
	    	y = y + 33;
	    	TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", merchantname.substring(9, merchantname.length()));
	    }
	    if(nom.length()<=15){
	    	 y = y + 45;
	 	    TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", nomName+"：" + nom);
	    }else{
	    	y = y + 45;
	    	TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", nomName+"：" + nom.substring(0, 17));
	    	y = y + 33;
	    	TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", nom.substring(17, nom.length()));
	    }
	   
	    y = y + 45;
	    TscLibDll.INSTANCE.windowsfont(30, y, 25, 0, 2, 0, "宋体", "顺序号： ");
	    TscLibDll.INSTANCE.windowsfont(160, y, 25, 0, 2, 0, "宋体", dto.getAsString("sequenceno"));
	    y = y + 45;
	    TscLibDll.INSTANCE.barcode("30", String.valueOf(y), "128", "70", "1", "0", "2", "2", dto.getAsString("serialno")); 
	    
	    
	    TscLibDll.INSTANCE.printlabel("1", "1");
	    TscLibDll.INSTANCE.closeport();
    
	}
	
	/**
	 * 打印图片 没测通
	 * @param fileName
	 * @throws Exception
	 */
	public static void printImage(String fileName) throws Exception{
		//TscLibDll.INSTANCE.about();
		TscLibDll.INSTANCE.openport("Gprinter  GP-3150TN");
		TscLibDll.INSTANCE.clearbuffer();
		
		TscLibDll.INSTANCE.setup("50", "20", "5", "5", "0", "3", "0");
		TscLibDll.INSTANCE.downloadpcx("C:\\UL.PCX", "UL.PCX");
		TscLibDll.INSTANCE.sendcommand("PUTPCX 550,10,\"UL.PCX\"");

		TscLibDll.INSTANCE.printlabel("1", "1");
	    TscLibDll.INSTANCE.closeport();
    
	}

	//测试方法
	public static void main(String[] args) {
		//314-235-984
		String[] serials = {"314-235-984"}; //, "314-236-026"};
		
		Dto dto = new BaseDto();
		dto.put("projectname", "中国建设银行");
		dto.put("repairphone", "400-1234567");
		dto.put("grantphone", "95500按#123");
		dto.put("complaintphone", "400-123456");
		dto.put("merchantname", "壹贰叁肆伍陆柒捌玖拾壹贰叁"); //壹贰叁肆伍陆柒捌玖拾壹贰叁
		dto.put("merchantid", "123456789012345");
		dto.put("sequenceno", "12345678");
		dto.put("serialno", "ABC-123-346");
		
		for(int i=0; i<serials.length; i++){
      	//printLabel(serials[i]);
	      	try {
	      		printSerialno("ABC-123-346","");
	      		//printLabel(dto);
	      		//printImage("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
      
	}

}

