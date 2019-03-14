package com.coalvalue.service.assistant;

import com.coalvalue.domain.entity.Configuration;
import com.coalvalue.domain.entity.StorageSpace;
import com.coalvalue.repository.ConfigurationRepository;
import com.coalvalue.service.StorageService;
import com.coalvalue.web.MobileIndexController;
import com.coalvalue.web.MobileReportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class LoggerService implements AssistantService {

    @Autowired
    private ConfigurationRepository configurationRepository;
    @Autowired
    private StorageService storageService;

    String name = "face_detect.py";
    private Process process ;

    private ProcessBuilder pb;
    private FtpClient ftpClient = new FtpClient("localhost", 2122, "admin", "admin");;

  //  @Scheduled(fixedDelay = 10000,   initialDelay=15000)

    public void setup() throws IOException ,URISyntaxException {
/*        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();*/


        ftpClient.open();

      //  System.out.println("000000000000------"+getClass().getClassLoader().getResource("ftp/baz.txt").getPath());
        String workingDir = System.getProperty("user.dir");
        System.out.println("000000000000------发送 数据啊啊啊啊"+workingDir);
    //    File file = new File("logs/spring-boot-logger.log");

        File folder = new File("logs/archived");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if(!listOfFiles[i].getName().endsWith("upload")){
                    System.out.println("File " + listOfFiles[i].getName());
                    System.out.println("000000000000------发送 数据啊啊啊啊");
                    ftpClient.putFileToPath(listOfFiles[i], "/fuck9/",listOfFiles[i].getName());

                }


            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        ftpClient.close();

    }

    public static void main(String args[]) {
        LoggerService openvpnService = new LoggerService();
        OpenvpnProfile profile = new OpenvpnProfile();
        try {
            String[] re = openvpnService.prepare(profile);
            Arrays.stream(re).forEach(e->System.out.println("----------"+ e));

        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        String s = null;

        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec("ps -ef");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }*/
    }

    private static Process chromeProcess ;

    @Override
    public void start() {

        System.out.println("打开  chrome 了啊 ");
        List<Configuration> configurations = configurationRepository.findAll();
        String url = null;
        if(configurations.size()> 0){
            List<StorageSpace> storageSpaceList = storageService.getAll();

            url = "http://localhost"+linkTo(methodOn(MobileReportController.class).index(storageSpaceList.get(0).getNo(),null)).withSelfRel().getHref();

        }else{
            try {
                url = "http://localhost"+linkTo(methodOn(MobileIndexController.class).welcome(null)).toUri().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        // ProcessBuilder pb = new ProcessBuilder("bash", "-c", "sudo -u pi chromium-browser http://www.google.com ");
        System.out.println("打开  chrome 了啊 "+url);

        String command = "#!/usr/bin/env bash\n" +
                "export DISPLAY=:0.0\n" +
                "echo $DISPLAY\n" +
                "chromium-browser --incognito --kiosk "+ url;


        String unixComand = command.replaceAll("\r\n", "\n");
        //  File file=new File("d:\\\\"+name);

        File file=new File("/home/pi/shell","tempShell_chromium-browser.sh");

        file.getParentFile().mkdirs();
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }

        System.out.println("filefile  chrome 了啊 "+file.toString());







        try {

            FileOutputStream out=new FileOutputStream(file);
            PrintStream ps = new PrintStream(out);

            ps.print(unixComand);


            out.close();

        } catch (IOException e){
            e.printStackTrace();
        }


        System.out.println("执行任务E:\\carparking\\src\\main\\resources\\proto\\"+file.getAbsolutePath());
        if(chromeProcess== null || !chromeProcess.isAlive()){
            ProcessBuilder pb = new ProcessBuilder("sudo","-u","pi","bash",file.getAbsolutePath(),""+1,""+2);
            pb.redirectErrorStream(true);
            try{
                chromeProcess = pb.start();

                System.out.println("process is is value is : "+chromeProcess.toString());

                BufferedReader br = new BufferedReader(new InputStreamReader(chromeProcess.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("ddddddddddddd"+line);
                }
                int exitCode = chromeProcess.waitFor();
                System.out.println("exitCode = "+exitCode);

            }catch(Exception e){
                e.printStackTrace();
                System.out.println(e);
            }
        }

    }

    @Override
    public void stop() {
            if(process== null){
                process.destroy();

            }else{
                System.out.print("error");
            }


    }

    @Override
    public String status() {
        return "";
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public boolean isProcessAlive(Process process) {
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }


    @Override
    public String doInBackground(Object... params) {
       // publishProgress(VpnStatus.VpnState.PREPARING);
        OpenvpnProfile profile = new OpenvpnProfile();
        try {
            process = Runtime.getRuntime().exec(prepare(profile));
/*            for (int i = 0; i < 30 && isProcessAlive(process)  && sock == null; ++i)
                try { // Wait openvpn to create management socket
                    sock = new ManagementSocket(managementPath);
                } catch (Exception e) {
                    Thread.sleep(1000);
                }
            if (sock == null) {
                InputStream stdout = process.getInputStream();
                byte[] buffer = new byte[stdout.available()];
                stdout.read(buffer);
                for (String s : new String(buffer, "UTF-8")
                        .split("\\r?\\n")) {
                   // log.add(s);
                }
                if (isProcessAlive(process))
                    process.destroy();
                return "Failed to start openvpn process";
            }*/
           // publishProgress(VpnState.CONNECTING);


/*            try {
                doCommands();
            } finally {
                synchronized (this) {
                    sock.shutdownAll();
                    sock.close();
                    sock = null;
                }
            }*/


            return null;
        } catch (Exception e) {
          //  publishProgress(VpnState.UNUSABLE);
           // Log.wtf(getClass().getName(), e);
            return e.getLocalizedMessage();
        } finally {
        //    publishProgress(VpnState.DISCONNECTING);
            try {
                if (process != null)
                    process.waitFor();
            } catch (InterruptedException e) {
               // Log.wtf(getClass().getName(), e);
                return e.getLocalizedMessage();
            }
          //  publishProgress(VpnState.IDLE);
        }
    }


    private String[] prepare(OpenvpnProfile profile) throws Exception {
        ArrayList<String> config = new ArrayList<String>();
       // config.add(new File(getCacheDir(), "openvpn").getAbsolutePath());
        config.add("openvpn");
        config.add("--client");
        config.add("--tls-client");

        config.add("--script-security");
        config.add("0");

        config.add("--management");
       // config.add(managementPath.getAbsolutePath());
        config.add("unixseq");
        config.add("--management-query-passwords");
        config.add("--management-hold");
        config.add("--management-signal");
        config.add("--remap-usr1");
        config.add("SIGTERM");
        config.add("--route-noexec");
        config.add("--ifconfig-noexec");
        config.add("--verb");
        config.add("3");

        config.add("--dev");
        config.add("[[ANDROID]]");
        config.add("--dev-type");
        config.add("tun");

        if (profile.getUserAuth()) {
            config.add("--auth-user-pass");
        }

        if (profile.getLocalAddr() != null) {
            config.add("--local");
            config.add(profile.getLocalAddr());
        } else {
            config.add("--nobind");
        }

        config.add("--proto");
        config.add(profile.getProto());

        config.add("--remote");
        config.add(profile.getServerName());
        config.add(profile.getPort());

        if (profile.getUseCompLzo())
            config.add("--comp-lzo");

        if (!"None".equals(profile.getNsCertType())) {
            config.add("--ns-cert-type");
            config.add(profile.getNsCertType());
        }

        if (profile.getRedirectGateway()) {
            config.add("--redirect-gateway");
        }

        if (profile.getCipher() != null) {
            config.add("--cipher");
            config.add(profile.getCipher());
        }

        if (!profile.getKeySize().equals("0")) {
            config.add("--keysize");
            config.add(profile.getKeySize());
        }

      /*  try {
            if (profile.getUserCertName() != null) {
                KeyStore pkcs12Store = KeyStore.getInstance("PKCS12");
                pkcs12Store.load(null, null);

                PrivateKey pk = KeyChain.getPrivateKey(OpenVpnService.this,
                        profile.getUserCertName());
                X509Certificate[] chain = KeyChain.getCertificateChain(
                        OpenVpnService.this, profile.getUserCertName());
                pkcs12Store.setKeyEntry("key", pk, null, chain);

                if (profile.getCertName() != null) {
                    KeyStore localTrustStore = KeyStore.getInstance("BKS");
                    localTrustStore
                            .load(new ByteArrayInputStream(profile
                                    .getCertName()), null);
                    Certificate root = localTrustStore.getCertificate("c");
                    if (root != null)
                        pkcs12Store.setCertificateEntry("root", root);
                }

                ByteArrayOutputStream f = new ByteArrayOutputStream();
                pkcs12Store.store(f, "".toCharArray());

                config.add("--pkcs12");
                config.add("[[INLINE]]");
                config.add(Base64.encodeToString(f.toByteArray(),
                        Base64.DEFAULT));
                f.close();
            } else if (profile.getCertName() != null) {
                KeyStore localTrustStore = KeyStore.getInstance("BKS");
                localTrustStore.load(
                        new ByteArrayInputStream(profile.getCertName()),
                        null);
                Certificate root = localTrustStore.getCertificate("c");

                if (root == null)
                    throw new RuntimeException(
                            "Certificate authority error");
                StringWriter s = new StringWriter();
                PEMWriter w = new PEMWriter(s);
                w.writeObject(root);
                w.flush();
                config.add("--ca");
                config.add("[[INLINE]]");
                config.add("# CA cert below\n" + s.toString());
                w.close();
            }
        } catch (Exception e) {
            Log.w(OpenVpnService.class.getName(),
                    "Error passing certifications", e);
            throw e;
        }*/

        if (profile.getUseTlsAuth()) {
            config.add("--tls-auth");
            config.add(profile.getTlsAuthKey());
            if (!"None".equals(profile.getTlsAuthKeyDirection()))
                config.add(profile.getTlsAuthKeyDirection());
        }

        if (profile.getExtra() != null)
            for (String s : profile.getExtra().trim()
                    .split(" +(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))
                config.add(s);

        return config.toArray(new String[0]);
    }
    @Override
    public String restart() {
        return null;
    }

}
