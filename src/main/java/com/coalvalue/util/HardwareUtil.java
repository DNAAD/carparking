package com.coalvalue.util;

import java.io.*;
import java.net.*;
import java.util.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HardwareUtil {

    /**
     * Return Opertaion System Name;
     *
     * @return os name.
     */
    public static String getOsName() {
        String os = "";
        os = System.getProperty("os.name");
        return os;
        //boolean isWindows = System.getProperty("os.name")
        //  .toLowerCase().startsWith("windows");
    }

    /**
     * Returns the MAC address of the computer.
     *
     * @return the MAC address
     */
    public static String getMACAddress() {
        String address = "";
        String os = getOsName();
        if (os.startsWith("Windows")) {
            try {
                String command = "cmd.exe /c ipconfig /all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("Physical Address") > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                        break;
                    } else if (line.indexOf("物理地址") > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                        break;
                    }
                }
                br.close();
                return address.trim();
            } catch (IOException e) {
            }
        } else if (os.startsWith("Linux")) {
            String command = "/bin/sh -c ifconfig -a";
            Process p;
            try {
                p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("HWaddr") > 0) {
                        int index = line.indexOf("HWaddr") + "HWaddr".length();
                        address = line.substring(index);
                        break;
                    }
                }
                br.close();
            } catch (IOException e) {
            }
        }
        address = address.trim();
        return address;
    }


    /**
     * 获取CPU号,多CPU时,只取第一个
     *
     * @return
     */
    public static String getCPUSerial() {
        String result = "";
        String os = getOsName();
        if (os.startsWith("Windows")) {
            try {
                File file = File.createTempFile("tmp", ".vbs");
                file.deleteOnExit();
                FileWriter fw = new java.io.FileWriter(file);

                String vbs = "On Error Resume Next \r\n\r\n" + "strComputer = \".\"  \r\n"
                        + "Set objWMIService = GetObject(\"winmgmts:\" _ \r\n"
                        + "    & \"{impersonationLevel=impersonate}!\\\\\" & strComputer & \"\\root\\cimv2\") \r\n"
                        + "Set colItems = objWMIService.ExecQuery(\"Select * from Win32_Processor\")  \r\n "
                        + "For Each objItem in colItems\r\n " + "    Wscript.Echo objItem.ProcessorId  \r\n "
                        + "    exit for  ' do the first cpu only! \r\n" + "Next                    ";

                fw.write(vbs);
                fw.close();
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = input.readLine()) != null) {
                    result += line;
                }
                input.close();
                file.delete();
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        } else if (os.startsWith("Linux")) {
            String CPU_ID_CMD = "dmidecode -t 4 | grep ID |sort -u |awk -F': ' '{print $2}'";
            Process p;
            try {
                p = Runtime.getRuntime().exec(new String[]{"sh", "-c", CPU_ID_CMD});//管道
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    result += line;
                    break;
                }
                br.close();
            } catch (IOException e) {
            }
        }
        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }
        return result.trim();
    }










    /**
     * 获取CPU号,多CPU时,只取第一个
     *
     * @return
     */
    public static String [] getCPUSerial__() {

        String [] result = new  String [4];

        //String result = "";
        String os = getOsName();
 if (os.startsWith("Linux")) {




            try {
                Process Proc = Runtime.getRuntime().exec("cat /proc/cpuinfo");
                Proc.waitFor();
                BufferedReader Pinput = new BufferedReader(new InputStreamReader(Proc.getInputStream()));
                String Flags = "";



                String str = null ;
                StringTokenizer token = null ;



                while((str= Pinput.readLine())!=null) {


                    System.out.println(str);
                   // System.out.println("countTokens===="+token.countTokens());


/*
                    token = new StringTokenizer(str);
                    if (!token.hasMoreTokens())
                        continue ;

                    str = token.nextToken();
                    System.out.println("str===="+str);


                    if (!token.hasMoreTokens())
                        continue ;*/








       /*             if (str.equalsIgnoreCase( "MemTotal:" ))
                        result[0 ] = Integer.parseInt(token.nextToken());
                    else  if (str.equalsIgnoreCase( "MemFree:" ))
                        result[1 ] = Integer.parseInt(token.nextToken());
                    else  if (str.equalsIgnoreCase( "SwapTotal:" ))
                        result[2 ] = Integer.parseInt(token.nextToken());
                    else  if (str.equalsIgnoreCase( "SwapFree:" ))
                        result[3 ] = Integer.parseInt(token.nextToken());
                    else  */
                        if (str.contains( "Serial" ))
                            result[0 ] =str;// token.nextToken();

                //return result;


                //result += Flags;
// Do your stuff here and extract the necessary information
                }
                Pinput.close();

            } catch (IOException e) {
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
 }
/*        if (result.trim().length() < 1 || result == null) {
            result = "无CPU_ID被读取";
        }*/

for(String string: result){
    System.out.println(string);
}

        return result;//.trim();
    }




    /**
     * Main Class.
     *
     * @param args
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
/*    public static void main(String[] args) throws Exception {

        String macAddress = HardwareUtil.getMACAddress();
        String cpuSerial = HardwareUtil.getCPUSerial();
        String test = macAddress + "@" + cpuSerial;

        System.out.println(test);

    }*/
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            long start = System.currentTimeMillis();
            Process process = Runtime.getRuntime().exec(
                    new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            String serial = sc.next();
            System.out.println(property + ": " + serial);
            System.out.println("time:" + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }




    public static boolean isWindows       = false;
    public static boolean isLinux         = false;
    public static boolean isHpUnix        = false;
    public static boolean isPiUnix        = false;
    public static boolean isSolaris       = false;
    public static boolean isSunOS         = false;
    public static boolean archDataModel32 = false;
    public static boolean archDataModel64 = false;

        public static void getOs() {
        final String os = System.getProperty("os.name").toLowerCase();
        if (os.indexOf("windows") >= 0) {
            isWindows = true;
        }
        if (os.indexOf("linux") >= 0) {
            isLinux = true;
        }
        if (os.indexOf("hp-ux") >= 0) {
            isHpUnix = true;
        }
        if (os.indexOf("hpux") >= 0) {
            isHpUnix = true;
        }
        if (os.indexOf("solaris") >= 0) {
            isSolaris = true;
        }
        if (os.indexOf("sunos") >= 0) {
            isSunOS = true;
        }
        if (System.getProperty("sun.arch.data.model").equals("32")) {
            archDataModel32 = true;
        }
        if (System.getProperty("sun.arch.data.model").equals("64")) {
            archDataModel64 = true;
        }
        if (isLinux) {
            final File file = new File("/etc", "os-release");
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis))) {
                String string;
                while ((string = bufferedReader.readLine()) != null) {
                    if (string.toLowerCase().contains("raspbian")) {
                        if (string.toLowerCase().contains("name")) {
                            isPiUnix = true;
                            break;
                        }
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 获取服务器IP地址
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String  getServerIp(){
        String SERVER_IP = null;

        String local_ip = null;
        String local_host = null;



        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while(e.hasMoreElements())
            {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                System.out.println(n.getDisplayName());
                System.out.println("\t- name:" + n.getName());
                System.out.println("\t- idx:" + n.getIndex());
                System.out.println("\t- max trans unit (MTU):" + n.getMTU());
                System.out.println("\t- is loopback:" + n.isLoopback());
                System.out.println("\t- is PPP:" + n.isPointToPoint());
                System.out.println("\t- isUp:" + n.isUp());
                System.out.println("\t- isVirtual:" + n.isVirtual());
                System.out.println("\t- supportsMulticast:" + n.supportsMulticast());


                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements())
                {InetAddress i = (InetAddress) ee.nextElement();
                    if(n.getName().equals("wlan0") &&  i instanceof Inet4Address){
                        local_ip = i.getHostAddress();

                        break;
                    }


                    System.out.println(i.getHostAddress());
                }
            }

/*
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();


            while (interfaces.hasMoreElements()) {
                NetworkInterface ni =  interfaces.nextElement();
                System.out.println(ni.getDisplayName());
                System.out.println("\t- name:" + ni.getName());
                System.out.println("\t- idx:" + ni.getIndex());
                System.out.println("\t- max trans unit (MTU):" + ni.getMTU());
                System.out.println("\t- is loopback:" + ni.isLoopback());
                System.out.println("\t- is PPP:" + ni.isPointToPoint());
                System.out.println("\t- isUp:" + ni.isUp());
                System.out.println("\t- isVirtual:" + ni.isVirtual());
                System.out.println("\t- supportsMulticast:" + ni.supportsMulticast());

                Enumeration ee = ni.getInetAddresses();
               while(ee.hasMoreElements()){




                    Enumeration<InetAddress> addresses = ni.getInetAddresses();
                    while(addresses.hasMoreElements()){
                        InetAddress address = addresses.nextElement();

                        System.out.println("\t\t-" + address.getHostName() + ":" + address.getHostAddress() + " - "+ address.getAddress());
                    }

                }


            }*/
/*                while (ee.hasMoreElements())
                {
                    InetAddress i = (InetAddress) ee.nextElement();
                    SERVER_IP = i.getHostAddress();
                    if (!i.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && i.getHostAddress().indexOf(":") == -1) {
                        SERVER_IP = i.getHostAddress();
                        break;
                    } else {
                        ip = null;
                    }
                    System.out.println(i.getHostAddress());
                }*/


        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return local_ip;
    }


   // @Test
    public void givenName_whenReturnsNetworkInterface_thenCorrect() {
        NetworkInterface nif = null;
        try {
            nif = NetworkInterface.getByName("lo");
        } catch (SocketException e) {
            e.printStackTrace();
        }

        assertNotNull(nif);
    }
    public void givenInExistentName_whenReturnsNull_thenCorrect() {
        NetworkInterface nif = null;
        try {
            nif = NetworkInterface.getByName("inexistent_name");
        } catch (SocketException e) {
            e.printStackTrace();
        }

        assertNull(nif);
    }

    public void givenIP_whenReturnsNetworkInterface_thenCorrect() {
        byte[] ip = new byte[] { 127, 0, 0, 1 };

        NetworkInterface nif = null;
        try {
            nif = NetworkInterface.getByInetAddress(
                    InetAddress.getByAddress(ip));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        assertNotNull(nif);
    }

    public void givenHostName_whenReturnsNetworkInterface_thenCorrect() throws UnknownHostException, SocketException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(
                InetAddress.getByName("localhost"));

        assertNotNull(nif);
    }

    public void givenLoopBack_whenReturnsNetworkInterface_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(
                InetAddress.getLoopbackAddress());

        assertNotNull(nif);

/*        for (NetworkInterface nif: Collections.list(nets)) {
            //do something with the network interface
        }*/
    }


    public void givenInterface_whenReturnsInterfaceAddresses_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        List<InterfaceAddress> addressEnum = nif.getInterfaceAddresses();
        InterfaceAddress address = addressEnum.get(0);

        InetAddress localAddress=address.getAddress();
        InetAddress broadCastAddress = address.getBroadcast();

        assertEquals("127.0.0.1", localAddress.getHostAddress());
        assertEquals("127.255.255.255",broadCastAddress.getHostAddress());
    }
    public void givenInterface_whenChecksIfUp_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");

        assertTrue(nif.isUp());
    }



//    To check if it is a loopback interface:


    public void givenInterface_whenChecksIfLoopback_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");

        assertTrue(nif.isLoopback());
    }
//    To check if it represents a point to point network connection:


    public void givenInterface_whenChecksIfPointToPoint_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");

        assertFalse(nif.isPointToPoint());
    }
 //   Or if it’s a virtual interface:

    public void givenInterface_whenChecksIfVirtual_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertFalse(nif.isVirtual());
    }
   // To check if multicasting is supported:


    public void givenInterface_whenChecksMulticastSupport_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");

        assertTrue(nif.supportsMulticast());
    }
  //  Or to retrieve its physical address, usually called MAC address:


    public void givenInterface_whenGetsMacAddress_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        byte[] bytes = nif.getHardwareAddress();

        assertNotNull(bytes);
    }
}