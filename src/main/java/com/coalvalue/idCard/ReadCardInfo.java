/*
package com.coalvalue.idCard;

import java.io.Console;
import java.io.File;

/// <summary>
    /// 读卡相关类
    /// </summary>
    public class ReadCardInfo
    {
        /// <summary>
        /// 初始化
        /// </summary>
        /// <returns></returns>
        public static Boolean OpenInstrument()
        {
            if ((ReadCardAPI.InitComm(1001) == 1) || (ReadCardAPI.InitComm(1002) == 1) || (ReadCardAPI.InitComm(1003) == 1) || (ReadCardAPI.InitComm(1004) == 1))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        /// <summary>
        /// 关闭端口
        /// </summary>
        /// <returns></returns>
        public static Boolean CloseComm()
        {
            if (ReadCardAPI.CloseComm() == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        /// <summary>
        /// 卡认证
        /// </summary>
        /// <returns></returns>
        public static Boolean Authenticate()
        {
            if (ReadCardAPI.Authenticate() == 1)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        /// <summary>
        /// 读卡
        /// </summary>
        /// <returns>错误信息</returns>
        public static String Read_Content()
        {
            String errormessage = null;
            //1 : 读基本信息，2 : 只读文字信息，3 : 读最新住址信息
            int errorcode = ReadCardAPI.Read_Content(1);
            errormessage = GetError(errorcode);
            if (errormessage != null) return errormessage;
            errorcode = ReadCardAPI.Read_Content(2);
            errormessage = GetError(errorcode);
            if (errormessage != null) return errormessage;
            errorcode = ReadCardAPI.Read_Content(3);
            errormessage = GetError(errorcode);
            if (errormessage != null) return errormessage;
            return null;
        }
        /// <summary>
        /// 判断C盘根目录是否有授权文件，没有则复制过去
        /// </summary>
        public static void Authorization()
        {
            //授权文件是否存在，若不存在，则复制过去
            try
            {
                String path1 = @"C:\Termb.Lic";
                if (!File.Exists(path1))
                {
                    String path2 = Application.StartupPath + "\\Authorization\\Termb.Lic";
                    if (File.Exists(path2))
                    {
                        File.Copy(path2, path1, true);
                    }
                }
            }
            catch (Exception ex)
            {
                String message = ex.ToString();
                Console.WriteLine(message);
            }
        }
        /// <summary>
        /// 读取卡内容--表示层应用
        /// </summary>
        /// <param name="message">错误信息</param>
        /// <returns></returns>
        public static Model ReadCard(out string message)
        {
            Authorization();//判断授权文件
            if (OpenInstrument())//初始化
            {
                if (Authenticate())
                {
                    message = Read_Content();
                    if (message == null)
                    {
                        Model model = GetCardInfo();
                        return model;
                    }
                    else
                    {
                        return null;
                    }
                }
                CloseComm();//关闭
                message = null;
                return null;
            }
            else//初始化失败
            {
                CloseComm();//关闭
                message = "身份证扫描仪连接失败，请检查仪器然后重新连接！";
                return null;
            }
        }
        /// <summary>
        /// 读取生成文件内容
        /// </summary>
        /// <returns></returns>
        public static Model GetCardInfo()
        {
            Model model = new Model();
            FileStream FSinfo = null;
            String infoPatn = Application.StartupPath + "\\wz.txt";
            //string NewAddressPath = Application.StartupPath + "\\NewAdd.txt";
            String path = Application.StartupPath + "\\zp.bmp";
            try
            {
                FSinfo = new FileStream(infoPatn, FileMode.Open, FileAccess.Read);
                int infoLength = (int)FSinfo.Length;
                byte[] infoBytes = new byte[infoLength];
                FSinfo.Read(infoBytes, 0, infoLength);
                String strs = Encoding.Unicode.GetString(infoBytes);
                model.NAME = Encoding.Unicode.GetString(infoBytes, 0, 30).Trim();//姓名
                model.SEX = Encoding.Unicode.GetString(infoBytes, 30, 2).Trim() == "1" ? 1 : 0;//性别
                int nationid = Convert.ToInt32(Encoding.Unicode.GetString(infoBytes, 32, 4).Trim());//民族
                model.NATION = GetNation(nationid);
                String birth = Encoding.Unicode.GetString(infoBytes, 36, 16).Trim();//出生日期
                model.BIRTH = DateTime.ParseExact(birth, "yyyyMMdd", null);
                model.ADDRESS = Encoding.Unicode.GetString(infoBytes, 52, 70).Trim();//住址
                model.IDNO = Encoding.Unicode.GetString(infoBytes, 122, 36).Trim();//身份证号码
                model.ISSUE_AUTH = Encoding.Unicode.GetString(infoBytes, 158, 30).Trim();//签发机关
                String began = Encoding.Unicode.GetString(infoBytes, 188, 16).Trim();//有效期开始
                model.BEGIN_DATE = DateTime.ParseExact(began, "yyyyMMdd", null);
                String end = Encoding.Unicode.GetString(infoBytes, 204, 16).Trim();//有效期结束
                if (end != "长期")
                {
                    model.END_DATE = DateTime.ParseExact(end, "yyyyMMdd", null);
                }
                else
                {
                    model.END_DATE = DateTime.ParseExact("2100-01-01", "yyyyMMdd", null);
                }
                model.HEADPORTRAIT = (Bitmap)CLASS.IMAGE.GetImage(path);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.ToString());
            }
            finally
            {
                if (FSinfo != null) FSinfo.Close();
                File.Delete(infoPatn);//删除信息文件
                File.Delete(path);//删除头像
            }
            return model;
        }
        /// <summary>
        /// 错误信息
        /// </summary>
        /// <param name="errorCode">错误代码</param>
        /// <returns></returns>
        public static String GetError(int errorCode)
        {
            String errorStr = null;
            switch (errorCode)
            {
                case 33:
                    errorStr = "信息验证错误,错误代码：33";
                    break;
                case 34:
                    errorStr = "尚未找卡，不能进行对卡的操作,错误代码：34";
                    break;
                case 40:
                    errorStr = "无法识别的卡类型,错误代码：40";
                    break;
                case 41:
                    errorStr = "读卡操作失败,错误代码：41";
                    break;
                case 50:
                    errorStr = "写卡操作失败,错误代码：50";
                    break;
                case 61:
                    errorStr = "用户登录失败,错误代码：61";
                    break;
                case -1:
                    errorStr = "相片解码错误,错误代码：-1";
                    break;
                case -2:
                    errorStr = "wlt文件后缀错误,错误代码：-2";
                    break;
                case -3:
                    errorStr = "wlt文件打开错误,错误代码：-3";
                    break;
                case -4:
                    errorStr = "wlt文件格式错误,错误代码：-4";
                    break;
                case -5:
                    errorStr = "软件未授权,错误代码：-5";
                    break;
                case -6:
                    errorStr = "设备连接错误,错误代码：-6";
                    break;
                case -8:
                    errorStr = "文件存储失败,错误代码：-8";
                    break;
                case -10:
                    errorStr = "端口操作失败,错误代码：-10";
                    break;
                case -11:
                    errorStr = "解码失败,错误代码：-11";
                    break;
                case 2:
                    errorStr = "接收数据超时,错误代码：2";
                    break;
                case 80:
                    errorStr = "找卡不成功,错误代码：80";
                    break;
                case 81:
                    errorStr = "选卡不成功,错误代码：81";
                    break;
                case 31:
                    errorStr = "卡认证机具失败,错误代码：31";
                    break;
                case 32:
                    errorStr = "机具认证卡失败,错误代码：32";
                    break;
            }
            return errorStr;
        }
        /// <summary>
        /// 获得民族名称
        /// </summary>
        /// <param name="NationID">民族编号</param>
        /// <returns></returns>
        public static String GetNation(int NationID)
        {
            String nation = "";
            switch (NationID)
            {
                case 1:
                    nation = "汉族";
                    break;
                case 2:
                    nation = "蒙古族";
                    break;
                case 3:
                    nation = "回族";
                    break;
                case 4:
                    nation = "藏族";
                    break;
                case 5:
                    nation = "维吾尔族";
                    break;
                case 6:
                    nation = "苗族";
                    break;
                case 7:
                    nation = "彝族";
                    break;
                case 8:
                    nation = "壮族";
                    break;
                case 9:
                    nation = "布依族";
                    break;
                case 10:
                    nation = "朝鲜族";
                    break;
                case 11:
                    nation = "满族";
                    break;
                case 12:
                    nation = "侗族";
                    break;
                case 13:
                    nation = "瑶族";
                    break;
                case 14:
                    nation = "白族";
                    break;
                case 15:
                    nation = "土家族";
                    break;
                case 16:
                    nation = "哈尼族";
                    break;
                case 17:
                    nation = "哈萨克族";
                    break;
                case 18:
                    nation = "傣族";
                    break;
                case 19:
                    nation = "黎族";
                    break;
                case 20:
                    nation = "傈僳族";
                    break;
                case 21:
                    nation = "佤族";
                    break;
                case 22:
                    nation = "畲族";
                    break;
                case 23:
                    nation = "高山族";
                    break;
                case 24:
                    nation = "拉祜族";
                    break;
                case 25:
                    nation = "水族";
                    break;
                case 26:
                    nation = "东乡族";
                    break;
                case 27:
                    nation = "纳西族";
                    break;
                case 28:
                    nation = "景颇族";
                    break;
                case 29:
                    nation = "柯尔克孜族";
                    break;
                case 30:
                    nation = "土族";
                    break;
                case 31:
                    nation = "达斡尔族";
                    break;
                case 32:
                    nation = "仫佬族";
                    break;
                case 33:
                    nation = "羌族";
                    break;
                case 34:
                    nation = "布朗族";
                    break;
                case 35:
                    nation = "撒拉族";
                    break;
                case 36:
                    nation = "毛南族";
                    break;
                case 37:
                    nation = "仡佬族";
                    break;
                case 38:
                    nation = "锡伯族";
                    break;
                case 39:
                    nation = "阿昌族";
                    break;
                case 40:
                    nation = "普米族";
                    break;
                case 41:
                    nation = "塔吉克族";
                    break;
                case 42:
                    nation = "怒族";
                    break;
                case 43:
                    nation = "乌孜别克族";
                    break;
                case 44:
                    nation = "俄罗斯族";
                    break;
                case 45:
                    nation = "鄂温克族";
                    break;
                case 46:
                    nation = "德昂族";
                    break;
                case 47:
                    nation = "保安族";
                    break;
                case 48:
                    nation = "裕固族";
                    break;
                case 49:
                    nation = "京族";
                    break;
                case 50:
                    nation = "塔塔尔族";
                    break;
                case 51:
                    nation = "独龙族";
                    break;
                case 52:
                    nation = "鄂伦春族";
                    break;
                case 53:
                    nation = "赫哲族";
                    break;
                case 54:
                    nation = "门巴族";
                    break;
                case 55:
                    nation = "珞巴族";
                    break;
                case 56:
                    nation = "基诺族";
                    break;
                case 57:
                    nation = "其他";
                    break;
                default:
                    nation = "外国血统";
                    break;
            }
            return nation;
        }
    }*/
