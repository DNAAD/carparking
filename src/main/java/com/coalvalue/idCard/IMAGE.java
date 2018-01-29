/*
package com.coalvalue.idCard;

class IMAGE
    {
        /// <summary>
        /// 将Image对象转化成二进制流
        /// </summary>
        /// <param name="image"></param>
        /// <returns></returns>
        public static byte[] ImageToByteArray(Image image)
        {
            //实例化流
            MemoryStream imageStream = new System.IO.MemoryStream();
            //将图片的实例保存到流中
            image.Save(imageStream, image.RawFormat);
            //保存流的二进制数组
            byte[] imageContent = new Byte[imageStream.Length];
            imageStream.Position = 0;
            //将流泻如数组中
            imageStream.Read(imageContent, 0, (int)imageStream.Length);
            byte[] b = imageStream.ToArray();
            int i = b.Length;
            return b;
        }
        /// <summary>
        /// 将二进制流转化为Image对象
        /// </summary>
        /// <param name="bytes"></param>
        /// <returns></returns>
        public static Bitmap ByteArrayToImage(byte[] bytes)
        {
            if (bytes.Length == 0)
            {
                return null;
            }
            //读取文件流 
            MemoryStream ms = new MemoryStream(bytes);
            //从流中穿件image
            Bitmap bmp = (Bitmap)Image.FromStream(ms, true);
            return bmp;
        }
        /// <summary>
        /// 读取图片
        /// </summary>
        /// <param name="path">图片路径</param>
        /// <returns></returns>
        public static Image GetImage(string path)
        {
            FileStream fs = null;
            Image image = null;
            try
            {
                fs = new FileStream(path, FileMode.Open, FileAccess.Read, FileShare.ReadWrite);
                int length = (int)fs.Length;
                byte[] bytes = new byte[length];
                fs.Read(bytes, 0, length);
                image = Image.FromStream(new MemoryStream(bytes));
            }
            catch (Exception ex)
            {
                string errorpath = Application.StartupPath + "\\error.jpg";
                if (File.Exists(errorpath))
                {
                    if (fs != null) fs.Close();
                    fs = new FileStream(errorpath, FileMode.Open, FileAccess.Read);
                    int length = (int)fs.Length;
                    byte[] bytes = new byte[length];
                    fs.Read(bytes, 0, length);
                    image = Image.FromStream(new MemoryStream(bytes));
                }
                else
                {
                    image = null;
                }
            }
            finally
            {
                if (fs != null) fs.Close();
            }
            return image;
        }
    }*/
