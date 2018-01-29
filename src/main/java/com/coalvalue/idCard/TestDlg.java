/*
package com.coalvalue.idCard;

import java.awt.*;
import java.awt.event.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import com.sun.jna.win32.*;
import com.sun.jna.*;

public class TestDlg extends JFrame implements ActionListener {
    private static final long serialVersionUID = -7495940408592595397L;

    private JPanel mainPanel;						//主面板
    private Button[] button = new Button[6];		//主面板上按钮
    private JPanel[] buttonPanel = new JPanel[4];	//四个二级面板
    private JPanel[] firstPanel = new JPanel[9];	//第一个二级面板下的九个三级面板
    private Button[][] b = new Button[4][3];		//二级面板下的12个按钮
    private Button b33 = new Button("姓名_身份证号");//最后一个二级面板多出的按钮
    //第一个二级面板内的3个标签和3个编辑框
    private JLabel lbPort;
    private JLabel lbMaxByte;
    private JLabel lbBaud;
    private JTextField port;
    private JTextField maxByte;
    private JTextField baud;

    private JTable table;		//信息列表
    private Table_Model model = null;
    private JScrollPane s_pan = null;

    public TestDlg() {
        //设置框架初始化参数
        this.setTitle("二代证读取");
        this.setLocation(200, 50);
		
		*/
/*初始化工作开始*//*

        mainPanel = new JPanel();	//初始化主面板
        //初始化主面板按钮
        button[0] = new Button("复位SAM（慎用）");
        button[1] = new Button("获得SAM状态");
        button[2] = new Button("获得SAM序列号");
        button[3] = new Button("使用结构体返回信息");
        button[4] = new Button("清除所有信息");
        button[4].setPreferredSize(new Dimension(100,25));
        button[5] = new Button("退出");
        button[5].setPreferredSize(new Dimension(94, 25));
        //初始化二级面板
        for (int i = 0; i < 4; i++) {
            buttonPanel[i] = new JPanel();
        }
        //初始化三级面板
        for (int i = 0; i < 9; i++) {
            firstPanel[i] = new JPanel();
        }
        //初始化按钮
        b[0][0] = new Button("自动寻找读卡器");
        b[0][1] = new Button("最大通信字节数");
        b[0][2] = new Button("设置通信波特率");
        b[0][2].setEnabled(false);		//按钮初始不可用
        b[1][0] = new Button("存在C盘根目录");
        b[1][1] = new Button("存在当前目录");
        b[1][2] = new Button("存在选定路径");
        b[2][0] = new Button("存为Bmp格式");
        b[2][1] = new Button("存为Jpeg格式");
        b[2][2] = new Button("存为Base64格式");
        b[3][0] = new Button("tmp");
        b[3][1] = new Button("姓名");
        b[3][2] = new Button("身份证号");
        //初始化标签
        lbPort = new JLabel("端口号：");
        lbMaxByte = new JLabel("<html>设置最大通信<br>字节数(24-255):</html>");
        lbMaxByte.setFont(new java.awt.Font("Dialog", 1, 9));
        lbBaud = new JLabel("波特率：");
        //初始化编辑框
        port = new JTextField(6);
        port.setText("1");		//初始值为1
        maxByte = new JTextField(6);
        maxByte.setText("80");	//初始值为80
        baud = new JTextField(6);
        baud.setEnabled(false);	//初始不可用
        */
/*初始化工作结束*//*


        //添加按钮监视事件
        for (int i = 0; i < 4; i++) {
            for(int j=0; j<3; j++){
                b[i][j].addActionListener(this);
            }
        }
        b33.addActionListener(this);
        for (int i = 0; i < 6; i++){
            button[i].addActionListener(this);
        }

        //将按钮添加到主面板
        for ( int i = 0; i < 6; i++) {
            mainPanel.add(button[i]);
        }

        //设置各二级面板标题
        buttonPanel[0].setBorder(BorderFactory.createTitledBorder("SAM设置函数"));
        buttonPanel[1].setBorder(BorderFactory.createTitledBorder("照片存放路径设置"));
        buttonPanel[2].setBorder(BorderFactory.createTitledBorder("照片保存格式"));
        buttonPanel[3].setBorder(BorderFactory.createTitledBorder("照片文件名格式"));

        //设置各二级面板大小及行列数
        buttonPanel[0].setPreferredSize(new Dimension(260,120));
        buttonPanel[0].setLayout(new GridLayout(3, 3, 1, 1));
        buttonPanel[1].setPreferredSize(new Dimension(120,120));
        buttonPanel[1].setLayout(new GridLayout(3, 1, 1, 5));
        buttonPanel[2].setPreferredSize(new Dimension(120,120));
        buttonPanel[2].setLayout(new GridLayout(3, 1, 1, 5));
        buttonPanel[3].setPreferredSize(new Dimension(120,120));
        buttonPanel[3].setLayout(new GridLayout(4, 1, 1, 1));

        //添加相应内容到九个三级面板
        firstPanel[0].add(lbPort);
        firstPanel[1].add(port);
        firstPanel[2].add(b[0][0]);
        firstPanel[3].add(lbMaxByte);
        firstPanel[4].add(maxByte);
        firstPanel[5].add(b[0][1]);
        firstPanel[6].add(lbBaud);
        firstPanel[7].add(baud);
        firstPanel[8].add(b[0][2]);

        //将三级面板添加到第一个二级面板
        for (int i = 0; i < 9; i++) {
            buttonPanel[0].add(firstPanel[i]);
        }

        //将其余按钮加入相应二级面板
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                buttonPanel[i].add(b[i][j]);
            }
        }
        buttonPanel[3].add(b33);

        //将二级面板添加到主面板
        for (int i = 0; i < 4; i++) {
            mainPanel.add(buttonPanel[i]);
        }

    	*/
/*信息列表设置开始*//*

        //初始化信息列表
        model = new Table_Model(20);
        table = new JTable(model);
        table.setBackground(Color.white);
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(50);
        tcm.getColumn(1).setPreferredWidth(300);

        s_pan = new JScrollPane(table);
        s_pan.setPreferredSize(new Dimension(640, 300));
    	*/
/*信息列表设置结束*//*


        //将信息列表加入主面板
        mainPanel.add(s_pan);

        //将主面板加入框架
        this.getContentPane().add(mainPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(660, 500);
        this.setVisible(true);
    }

    //此接口一定要继承StdCallLibrary 否则读卡错误!
    public interface SynIDCardAPI extends StdCallLibrary  {
        String path = TestDlg.class.getResource("/").getPath().substring(1).replace("/", "\\") + "SynIDCardAPI";
        SynIDCardAPI INSTANCE = (SynIDCardAPI)Native.loadLibrary(path, SynIDCardAPI.class);

        //SAM端口函数
        public int Syn_SetMaxRFByte(int iPort, char ucByte, int bIfOpen);
        public int Syn_GetCOMBaudEx(int iPort);
        public int Syn_SetCOMBaud(int iPort, int uiCurrBaud, int uiSetBaud);
        public int Syn_OpenPort(int iPort);
        public int Syn_ClosePort (int iPort);
        //SAM类函数
        public int Syn_ResetSAM(int iPort, int iIfOpen);
        public int Syn_GetSAMStatus(int iPort, int	iIfOpen);
        public int Syn_GetSAMID(int iPort, char[] pucSAMID, int iIfOpen);
        //身份证卡类函数
        public int Syn_StartFindIDCard(int iPort, char[] pucIIN, int iIfOpen);
        public int Syn_SelectIDCard(int iPort, char[] pucSN, int iIfOpen);
        public int Syn_ReadMsg(int iPort, int iIfOpen, IDCardData pINCardData);
        public int Syn_FindReader();
        //设置附加功能函数
        public int Syn_SetPhotoPath(int iOption, String cPhotopath);
        public int Syn_SetPhotoType(int iType);
        public int Syn_SetPhotoName(int iType);
        public int Syn_SetSexType( int iType );
        public int Syn_SetNationType(int iType);
        public int Syn_SetBornType(int iType);
        public int Syn_SetUserLifeBType(int iType);
        public int Syn_SetUserLifeEType(int iTyp, int iOption);
    }

    //显示信息自动滚动到最后一行
    private void scrollEnd(){
        int rowCount = table.getRowCount();
        Rectangle rect = table.getCellRect(rowCount-1, 0, true);
        table.scrollRectToVisible(rect);
    }

    //继承自ActionListener
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == button[0]) {
            String sMsg;
            int iPort = Integer.parseInt(port.getText());
            int nRet = SynIDCardAPI.INSTANCE.Syn_OpenPort(iPort);
            if (nRet == 0)
            {
                nRet = SynIDCardAPI.INSTANCE.Syn_ResetSAM(iPort, 0);
                if (nRet == 0)
                {
                    sMsg = "复位SAM模块成功";
                }
                else
                {
                    sMsg = "复位SAM模块失败";
                }
            }
            else
            {
                sMsg = "打开端口错误";
            }
            SynIDCardAPI.INSTANCE.Syn_ClosePort(iPort);
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == button[1]) {
            String sMsg;
            int iPort = Integer.parseInt(port.getText());
            int nRet = SynIDCardAPI.INSTANCE.Syn_OpenPort(iPort);
            if (nRet == 0)
            {
                nRet = SynIDCardAPI.INSTANCE.Syn_GetSAMStatus(iPort, 0);
                if (nRet == 0)
                {
                    sMsg = "SAM模块状态正常";
                }
                else
                {
                    sMsg = "获得SAM模块状态失败";
                }
            }
            else
            {
                sMsg = "打开端口错误";
            }
            SynIDCardAPI.INSTANCE.Syn_ClosePort(iPort);
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == button[2]) {
            String sMsg;
            int iPort = Integer.parseInt(port.getText());
            int nRet = SynIDCardAPI.INSTANCE.Syn_OpenPort(iPort);
            if (nRet == 0)
            {
                char[] strSAMID = new char[8];
                nRet = SynIDCardAPI.INSTANCE.Syn_GetSAMID(iPort, strSAMID, 0);
                if (nRet == 0)
                {
                    long[] iSAMID = new long[3];
                    iSAMID[0] = (long)strSAMID[3] * 65536 + (long)strSAMID[2];
                    iSAMID[1] = (long)strSAMID[5] * 65536 + (long)strSAMID[4];
                    iSAMID[2] = (long)strSAMID[7] * 65536 + (long)strSAMID[6];
                    sMsg = String.format("按16字节获得SAM模块ID成功,SAM模块ID为: %d-%d-%d-%d-%d", (int)strSAMID[0], (int)strSAMID[1], iSAMID[0], iSAMID[1], iSAMID[2]);
                }
                else
                {
                    sMsg = "读取SAM ID号错误";
                }
            }
            else
            {
                sMsg = "打开端口错误";
            }
            SynIDCardAPI.INSTANCE.Syn_ClosePort(iPort);
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == button[3]) {
            String sMsg;
            SynIDCardAPI.INSTANCE.Syn_SetSexType(1);
            SynIDCardAPI.INSTANCE.Syn_SetNationType(1);
            SynIDCardAPI.INSTANCE.Syn_SetBornType(2);
            SynIDCardAPI.INSTANCE.Syn_SetUserLifeBType(3);
            SynIDCardAPI.INSTANCE.Syn_SetUserLifeEType(4,1);

            int iPort = Integer.parseInt(port.getText());
            int nRet = SynIDCardAPI.INSTANCE.Syn_OpenPort(iPort);
            if (nRet == 0)
            {
                if (SynIDCardAPI.INSTANCE.Syn_SetMaxRFByte(iPort, (char)80, 0) == 0)
                {
                    char[] pucIIN = new char[8];
                    char[] pucSN = new char[8];
                    IDCardData idcardData = new IDCardData();
                    nRet = SynIDCardAPI.INSTANCE.Syn_StartFindIDCard(iPort, pucIIN, 0);
                    nRet = SynIDCardAPI.INSTANCE.Syn_SelectIDCard(iPort, pucSN, 0);
                    if (SynIDCardAPI.INSTANCE.Syn_ReadMsg(iPort, 0, idcardData) == 0)
                    {
                        sMsg= "读取身份证信息成功!";
                        model.addRow(sMsg);
                        table.updateUI();
                        try{
                            String strTemp = new String(idcardData.Name, "GBK");
                            sMsg = String.format("姓名:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.Sex, "GBK");
                            sMsg = String.format("性别:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.Nation, "GBK");
                            sMsg = String.format("民族:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.Born, "GBK");
                            sMsg = String.format("出生:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.Address, "GBK");
                            sMsg = String.format("住址:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.IDCardNo, "GBK");
                            sMsg = String.format("身份证号:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.GrantDept, "GBK");
                            sMsg = String.format("发证机关:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.UserLifeBegin, "GBK");
                            sMsg = String.format("有效期开始:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.UserLifeEnd, "GBK");
                            sMsg = String.format("有效期结束:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                            strTemp = new String(idcardData.PhotoFileName, "GBK");
                            sMsg = String.format("照片:%s", strTemp);
                            model.addRow(sMsg);
                            table.updateUI();
                        }catch (UnsupportedEncodingException une) {
                            une.printStackTrace();
                        }
                    }
                    else
                    {
                        sMsg = "读取身份证信息错误!";
                        model.addRow(sMsg);
                        table.updateUI();
                    }
                }
            }
            else
            {
                sMsg = "打开端口错误";
                model.addRow(sMsg);
                table.updateUI();
            }
            SynIDCardAPI.INSTANCE.Syn_ClosePort(iPort);
            scrollEnd();
        }
        else if (source == button[4]) {
            model.removeRows(0, model.getRowCount());
            table.updateUI();
        }
        else if (source == button[5]) {
            System.exit(0);
        }
        else if(source == b[0][0]) {
            int nRet;
            String sMsg, sMsg2;
            nRet = SynIDCardAPI.INSTANCE.Syn_FindReader();
            if (nRet == 0)
            {
                sMsg = "没有找打读卡器";
            }
            else
            {
                //Toolkit.getDefaultToolkit().beep();
                baud.setEnabled(true);
                b[0][2].setEnabled(true);
                if (nRet >1000)
                {
                    sMsg = String.format("读卡器连接在USB端口 %d", nRet);
                }
                else
                {
                    try{
                        Thread.sleep(200);
                    }catch (InterruptedException exc) {
                        System.out.println("error");
                    }
                    int uiCurrBaud = SynIDCardAPI.INSTANCE.Syn_GetCOMBaudEx(nRet);
                    sMsg2 = String.format("%d", uiCurrBaud);
                    baud.setText(sMsg2);
                    sMsg = String.format("读卡器连接在串口 %d,当前SAM波特率为 %d", nRet, uiCurrBaud);
                }
                sMsg2 = String.format("%d", nRet);
                port.setText(sMsg2);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[0][1]) {
            String sMsg,sMsg2;
            sMsg2 = port.getText();
            int iPort = Integer.parseInt(sMsg2);
            sMsg2 = maxByte.getText();
            int iByte = Integer.parseInt(sMsg2);
            char iMaxByte = (char)iByte;
            int nRet = SynIDCardAPI.INSTANCE.Syn_OpenPort(iPort);
            if (nRet == 0)
            {
                if (SynIDCardAPI.INSTANCE.Syn_GetSAMStatus(iPort, 0) == 0)
                {
                    if(SynIDCardAPI.INSTANCE.Syn_SetMaxRFByte(iPort, iMaxByte, 0) == 0)
                    {
                        sMsg = String.format("设置最大通讯字节数成功,最大通讯字节数为 %d", iByte);
                    }
                    else
                    {
                        sMsg = "设置最大通讯字节数出错!";
                    }
                }
                else
                {
                    sMsg = String.format("端口 %d 上没有连接读卡器!", iPort);
                }
            }
            else
            {
                sMsg = "打开端口错误！";
            }
            SynIDCardAPI.INSTANCE.Syn_ClosePort(iPort);
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[0][2]) {
            String sMsg,sMsg2;
            sMsg2 = port.getText();
            int iPort = Integer.parseInt(sMsg2);
            int iCurrBaud = SynIDCardAPI.INSTANCE.Syn_GetCOMBaudEx(iPort);
            if (iCurrBaud == 0 )
            {
                sMsg="获得当前读卡器波特率失败";
                model.addRow(sMsg);
                table.updateUI();
                return;
            }
            sMsg2 = baud.getText();
            int iSetBaud = Integer.parseInt(sMsg2);
            SynIDCardAPI.INSTANCE.Syn_ClosePort(iPort);
            if(SynIDCardAPI.INSTANCE.Syn_SetCOMBaud(iPort, iCurrBaud, iSetBaud) == 0)
            {
                sMsg = String.format("设置读卡器波特率成功,当前波特率为 %d ", iSetBaud);
            }
            else
            {
                sMsg = "设置读卡器波特率失败";
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[1][0]) {
            String strTmp = new String();
            int nRet = SynIDCardAPI.INSTANCE.Syn_SetPhotoPath(0, strTmp);
            String strInfo = new String();
            strInfo = String.format("照片存放路径设置为 C:,nRet = %d", nRet);
            model.addRow(strInfo);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[1][1]) {
            String strTmp = new String();
            int nRet = SynIDCardAPI.INSTANCE.Syn_SetPhotoPath(1, strTmp);
            String strInfo = new String();
            if (nRet == 0)
                strInfo = String.format("照片存放路径设置为当前路径,nRet = %d", nRet);
            else
                strInfo = String.format("照片存放路径设置错误,nRet = %d", nRet);
            model.addRow(strInfo);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[1][2]){
            String strInfo = new String();
            JFileChooser fileChooser = new JFileChooser(".");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("打开文件夹");
            int ret = fileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                //文件夹路径
                String strPath = fileChooser.getSelectedFile().getAbsolutePath();
                int nRet = SynIDCardAPI.INSTANCE.Syn_SetPhotoPath(0, strPath);
                strInfo = String.format("照片存放路径设置为 %s,nRet = %d", strPath, nRet);
            }
            else {
                strInfo = "选取路径失败！";
            }
            model.addRow(strInfo);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[2][0]) {
            String sMsg = new String();
            int nRet = SynIDCardAPI.INSTANCE.Syn_SetPhotoType(0);
            if (nRet == 0)
            {
                sMsg = String.format("照片保存格式设置为 Bmp,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[2][1]) {
            String sMsg = new String();
            int nRet = SynIDCardAPI.INSTANCE.Syn_SetPhotoType(1);
            if (nRet == 0)
            {
                sMsg = String.format("照片保存格式设置为 Jpeg,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[2][2]) {
            String sMsg = new String();
            int nRet = SynIDCardAPI.INSTANCE.Syn_SetPhotoType(2);
            if (nRet == 0)
            {
                sMsg = String.format("照片保存格式设置为 Base64,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[3][0]) {
            int nRet =  SynIDCardAPI.INSTANCE.Syn_SetPhotoName(0);
            String sMsg;
            if (nRet == 0)
            {
                sMsg = String.format("照片保存文件名格式设置为 tmp,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存文件名格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[3][1]) {
            int nRet =  SynIDCardAPI.INSTANCE.Syn_SetPhotoName(1);
            String sMsg;
            if (nRet == 0)
            {
                sMsg = String.format("照片保存文件名格式设置为 姓名,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存文件名格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b[3][2]) {
            int nRet =  SynIDCardAPI.INSTANCE.Syn_SetPhotoName(2);
            String sMsg;
            if (nRet == 0)
            {
                sMsg = String.format("照片保存文件名格式设置为 身份证号,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存文件名格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
        else if (source == b33) {
            int nRet =  SynIDCardAPI.INSTANCE.Syn_SetPhotoName(3);
            String sMsg;
            if (nRet == 0)
            {
                sMsg = String.format("照片保存文件名格式设置为 姓名_身份证号,nRet = %d", nRet);
            }
            else
            {
                sMsg = String.format("照片保存文件名格式设置失败,nRet = %d", nRet);
            }
            model.addRow(sMsg);
            table.updateUI();
            scrollEnd();
        }
    }

    public static void main(String[] args) {
        new TestDlg();
    }
}

class Table_Model extends AbstractTableModel {
    private static final long serialVersionUID = -7495940408592595395L;
    private Vector content = null;
    private String[] title_name = {"发生时间", "信息"};

    public Table_Model(int count) {
        content = new Vector(count);
    }

    //添加数据
    public void addRow(String strInfo) {
        Vector v = new Vector(2);
        Calendar now = Calendar.getInstance();
        String time = now.get(Calendar.YEAR)+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DAY_OF_MONTH)
                +" "+now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);
        v.add(0, time);
        v.add(1, strInfo);
        content.add(v);
    }

    //删除数据
    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (content.size() > row) {
                content.remove(row);
            }
        }
    }

    //以下函数为继承自AbstractTableModel
    public String getColumnName(int col) {
        return title_name[col];
    }
    public int getColumnCount() {
        return title_name.length;
    }
    public int getRowCount() {
        return content.size();
    }
    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }
}


*/
