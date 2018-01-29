/*
package com.coalvalue.idCard;

import java.io.Console;
import java.io.File;

/// <summary>
    /// 读卡相关类
    /// </summary>
    public class ReadCardInfo_
    {
        /// <summary>
        /// 初始化设备
        /// </summary>
        /// <param name="nPort">(串口:1-16,USB:1001-1016),为零自动找接口</param>
        /// <returns></returns>
        public static boolean InitCom(int nPort, String sMsg)
        {
            sMsg = string.Empty;
            try
            {
                byte[] cmd = { 0x41 };
                int para0 = 0;
                int para1 = 8811;
                int para2 = 9986;
                int nRet = RdCard.instanceDll.UCommand1(cmd, para0, para1, para2);
                if (nRet == 62171 || nRet == -5 || nRet == -7)
                {
                    sMsg = "身份证读卡器连接成功";
                    return true;
                }
                else
                {
                    sMsg = "身份证读卡器连接失败!";
                    return false;
                }
            }
            catch (Exception ex)
            {
                sMsg = "身份证读卡器连接失败,原因是:" + ex.Message;
            }
            return false;
        }

        /// <summary>
        /// 关闭设备
        /// </summary>
        /// <returns></returns>
        public static boolean CloseCom(int nPort)
        {
            byte[] cmd = { 0x42 };
            int para0 = 0;
            int para1 = 8811;
            int para2 = 9986;
            RdCard.instanceDll.UCommand1(cmd, para0, para1, para2);
            return true;
        }

        /// <summary>
        /// 获取身份证信息
        /// </summary>
        public static CardInfo ReadCardInfo(int nPort, String sMsg, String sSavePath)
        {
            CardInfo objCardInfo = null;
            try
            {
                byte[] cmd = { 0x43 };
                int para0 = 1;
                int para1 = 8811;
                int para2 = 9986;
                int nRet = RdCard.instanceDll.UCommand1(cmd, para0, para1, para2);

                if (nRet == 62171)//身份证验证成功
                {
                    byte[] bcmd = { 0x44 };
                    para0 = 1;
                    para1 = 8811;
                    para2 = 9986;
                    nRet = RdCard.instanceDll.UCommand1(bcmd, ref para0, ref para1, ref para2);// '读卡内信息
                    if ( (nRet == 62171) || (nRet == 62172) )
                    {
                        sMsg = string.Empty;
                        objCardInfo = new CardInfo();
                        System.IO.StreamReader objStreamReader = new System.IO.StreamReader(sSavePath + "\\wx.txt", System.Text.Encoding.Default);

                        //读取已转化文本
                        objCardInfo.Name = objStreamReader.ReadLine();
                        objCardInfo.Sex = objStreamReader.ReadLine();
                        objCardInfo.Nation = objStreamReader.ReadLine();
                        objCardInfo.Birthday = objStreamReader.ReadLine();
                        objCardInfo.Address = objStreamReader.ReadLine();
                        objCardInfo.CardNo = objStreamReader.ReadLine();
                        objCardInfo.Department = objStreamReader.ReadLine();
                        objCardInfo.StartDate = objStreamReader.ReadLine();
                        objCardInfo.EndDate = objStreamReader.ReadLine();
                        objCardInfo.PhotoPath = sSavePath + "\\zp.bmp";
                        string sPhotoPath = objCardInfo.PhotoPath;

                        //读取追加地址（读取unicode格式的文本）
                        byte[] ccmd = { 0x45 };//0x45 读追加地址，生成两个对应的文本文件。
                        nRet = RdCard.instanceDll.UCommand1(ccmd, para0, para1, para2);
                        //判断是否存在最新地址
                        if (62171 == nRet)
                        {
                            if (File.Exists(sSavePath + "\\NewAddgb.txt"))
                            {
                                try
                                {
                                    System.IO.StreamReader newAdd = new System.IO.StreamReader(sSavePath + "\\NewAddgb.txt", System.Text.Encoding.Unicode);
                                    string newaddress = newAdd.ReadToEnd();
                                    objCardInfo.AddressEx = newaddress;
                                    newAdd.Close();
                                    newAdd.Dispose();
                                }
                                catch
                                {
                                }
                            }
                        }
                        else
                        {
                            objCardInfo.AddressEx = " ";
                        }
                        objStreamReader.Close();
                        objStreamReader.Dispose();

                        return objCardInfo;
                    }
                    else if (-5 == nRet)
                    {
                        sMsg = "返回值：" + nRet.ToString() + "软件未授权!";
                    }
                    else
                    {
                        sMsg = "返回值：" + nRet.ToString() + "读身份证不成功";
                    }
                }
                else
                    sMsg = "请将身份证放置感应区，谢谢合作！";

                return objCardInfo;
            }
            catch (Exception ex)
            {
                sMsg = "读身份证失败,原因是:" + ex.Message;
                return null;
            }

        }

        /// <summary>
        /// 将图片转换成字节
        /// </summary>
        /// <param name="selectPictureFile"></param>
        /// <returns></returns>
        private static Byte[] ImageToByteArray(String selectPictureFile)
        {
            Image photo = new Bitmap(selectPictureFile);
            System.IO.MemoryStream ms = new System.IO.MemoryStream();
            photo.Save(ms, System.Drawing.Imaging.ImageFormat.Jpeg);
            byte[] imagedata = ms.GetBuffer();
            ms.Close();
            ms.Dispose();
            photo.Dispose();
            return imagedata;
        }
    }
*/
