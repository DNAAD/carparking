package com.coalvalue.publicCommand;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Find out the local IP address and default gateway
 * @author Henry Zheng
 * @url http://www.ireasoning.com
 */
public class RtTable
{
    private String _gateway;
    private String _ip;
    private static RtTable _instance;

    String command = "echo 200 custom >> /etc/iproute2/rt_tables";
/*
    String command = "ip rule add from 192.168.30.200 lookup custom";
/etc/network/interfaces

    post-up ip rule add from 192.168.30.200 lookup custom

    post-up ip route add default via 192.168.30.1 dev eth1 table custom

*/



    public static void main(String[] args)
    {
        try
        {
            RtTable pr = RtTable.getInstance();
            System.out.println( "Gateway: " + pr.getGateway() );
            System.out.println( "IP: " + pr.getLocalIPAddress() );
            ;


        }
        catch(Exception e)
        {
            System.out.println( e);
            e.printStackTrace();
        }
    }

/*
    ip rule show

    ip route show table all

    ip address show <device>

    ip link show

    ip link show up

    ip help
*/

    public List<String> ip_route_get() throws Exception {
        String s = null;
        List<String> strings = new ArrayList<>();

        Process p = Runtime.getRuntime().exec(String.format("ip route get %s", "10.8.1.201"));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
            List<String> tokensList = Collections.list(new StringTokenizer(s, "\t")).stream()
                    .map(token -> (String) token)
                    .collect(Collectors.toList());
            if (tokensList.size() > 1) {
                //1.2.3.4 via 10.0.1.1 dev wlan0  src 10.0.1.202
                String next_hop = tokensList.get(2); //0102A8C0
                String outbound_interface = tokensList.get(4); //0102A8C0
                String source_IP = tokensList.get(6); //0102A8C0
                strings.add(next_hop);
                strings.add(outbound_interface);
                strings.add(source_IP);
                if (source_IP.length() == 8) {
                    String[] s4 = new String[4];
                    s4[3] = String.valueOf(Integer.parseInt(source_IP.substring(0, 2), 16));
                    s4[2] = String.valueOf(Integer.parseInt(source_IP.substring(2, 4), 16));
                    s4[1] = String.valueOf(Integer.parseInt(source_IP.substring(4, 6), 16));
                    s4[0] = String.valueOf(Integer.parseInt(source_IP.substring(6, 8), 16));
                    _gateway = s4[0] + "." + s4[1] + "." + s4[2] + "." + s4[3];
                }
            }


        }
        return strings;

    }


        public List<List<String>> getGateway_ip() {
        return gateway_ip;
    }



    private RtTable()
    {
        parse();
    }
 
    private static boolean isWindows ()
    {
        String os = System.getProperty ( "os.name" ).toUpperCase ();
        return os.contains( "WINDOWS" ) ;
    }
 
    private static boolean isLinux ()
    {
        String os = System.getProperty ( "os.name" ).toUpperCase ();
        return os.contains( "LINUX" )  ;
    }
 
    public String getLocalIPAddress()
    {
        return _ip;
    }
 
    public String getGateway()
    {
        return _gateway;
    }
 
    public static RtTable getInstance()
    {
        if(_instance == null)
        {
            _instance = new RtTable();
        }
        return _instance;
    }
 
    private void parse() 
    {
        if(isWindows())
        {
            parseWindows();
        }
        else if(isLinux())
        {
            parseLinux();
        }
    }

    List<List<String>> gateway_ip = new ArrayList<>();
    private void parseWindows()
    {

    }
 
    private void parseLinux()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("/etc/iproute2/rt_tables"));
            String line;
            while((line = reader.readLine())!=null)
            {
                line = line.trim();
                List<String> tokensList = Collections.list(new StringTokenizer(line, "\t")).stream()
                        .map(token -> (String) token)
                        .collect(Collectors.toList()); // StringTokenizer.(line, ' ', true , true);//
                StringTokenizer stringTokenizer = new StringTokenizer(line, "\t", true);
                String [] tokens = tokensList.toArray(new String [tokensList.size()]  );//stringTokenizer.countTokens().(line, '\t', true , true);//
                if(tokens.length > 1 )
                {
                    String gateway = tokens[2]; //0102A8C0
                    if(gateway.length() == 8)
                    {
                        String[] s4 = new String[4];
                        s4[3] = String.valueOf(Integer.parseInt(gateway.substring(0, 2), 16));
                        s4[2] = String.valueOf(Integer.parseInt(gateway.substring(2, 4), 16));
                        s4[1] = String.valueOf(Integer.parseInt(gateway.substring(4, 6), 16));
                        s4[0] = String.valueOf(Integer.parseInt(gateway.substring(6, 8), 16));
                        _gateway = s4[0] + "." + s4[1] + "." + s4[2] + "." + s4[3];
                    }
                    String iface = tokens[0];
                    NetworkInterface nif = NetworkInterface.getByName(iface);
                    Enumeration addrs = nif.getInetAddresses();
                    while(addrs.hasMoreElements())
                    {
                        Object obj = addrs.nextElement();
                        if(obj instanceof Inet4Address)
                        {
                            _ip =  obj.toString();
                            if(_ip.startsWith("/")) _ip = _ip.substring(1);
                            return;
                        }
                    }
                    return;
                }
            }
            reader.close();
        }
        catch(IOException e)
        {
            System.err.println(e);
            e.printStackTrace();
        }
    }
 
 
 
}