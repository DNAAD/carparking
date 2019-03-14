package com.coalvalue.publicCommand;



import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Find out the local IP address and default gateway
 * @author Henry Zheng
 * @url http://www.ireasoning.com
 */
public class ParseRoute 
{
    private String _gateway;
    private String _ip;
    private static ParseRoute _instance;
 
    public static void main(String[] args)
    {
        try
        {
            ParseRoute pr = ParseRoute.getInstance();
            System.out.println( "Gateway: " + pr.getGateway() );
            System.out.println( "IP: " + pr.getLocalIPAddress() );
            ;

            pr.getGateway_ip().stream().forEach(e->System.out.println( "IP: " +e.toString()));
        }
        catch(Exception e)
        {
            System.out.println( e);
            e.printStackTrace();
        }
    }

    public List<List<String>> getGateway_ip() {
        return gateway_ip;
    }



    private ParseRoute ()
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
 
    public static ParseRoute getInstance()
    {
        if(_instance == null)
        {
            _instance = new ParseRoute();
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
        try
        {
            Process pro = Runtime.getRuntime().exec("cmd.exe /c route print");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream(),"gb2312"));
 
            String line;
            Boolean first = true;
            while((line = bufferedReader.readLine())!=null)
            {
                line = line.trim();
                List<String> tokens = Collections.list(new StringTokenizer(line, " ")).stream()
                        .map(token -> (String) token)
                        .collect(Collectors.toList()); // StringTokenizer.(line, ' ', true , true);//
                System.out.println(tokens.size()+"ddddddddddddd----------"+line);

                if(tokens.size() == 5 )
                {
                    if(first && tokens.get(0).equals("0.0.0.0")){
                        _gateway = tokens.get(2);
                        _ip = tokens.get(3);
                        first=false;
                    }


                    List<String> ipgateway = new ArrayList<String>();
                    ipgateway.add(tokens.get(2));
                    ipgateway.add( tokens.get(3));

                    gateway_ip.add(ipgateway);

                    //return;
                }
            }
            //pro.waitFor();      
        }
        catch(IOException e)
        {
            System.err.println(e);
            e.printStackTrace();
        }
    }
 
    private void parseLinux()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/net/route"));
            String line;
            while((line = reader.readLine())!=null)
            {
                line = line.trim();
                String [] tokens = line.split(" "); //Tokenizer.parse(line, '\t', true , true);//
                if(tokens.length > 1 && tokens[1].equals("00000000"))
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