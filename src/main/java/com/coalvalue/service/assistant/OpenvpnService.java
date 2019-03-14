package com.coalvalue.service.assistant;

import com.coalvalue.notification.liveEvent.IPythonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by silence on 2018-07-15.
 */
@Component
public class OpenvpnService implements AssistantService {


    @Autowired
    private ResourceLoader resourceLoader;


    String name = "face_detect.py";
    private Process process ;

    private ProcessBuilder pb;

    public static void main(String args[]) {
        OpenvpnService openvpnService = new OpenvpnService();
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


    @Override
    public void start() {
        String s = null;

        String your_config = "";
        try {
            if(process!= null && process.isAlive()){
                System.out.println(process+" process");
                System.out.println(process+" process");
                // run the Unix "ps -ef" command
                // using the Runtime exec method:

            }else{

                String homeDirectory = System.getProperty("user.home");
/*                Process process;
                if (isWindows) {
                    process = Runtime.getRuntime()
                            .exec(String.format("cmd.exe /c dir %s", homeDirectory));
                } else {
                    process = Runtime.getRuntime()
                            .exec(String.format("sh -c ls %s", homeDirectory));
                }
                StreamGobbler streamGobbler =
                        new StreamGobbler(process.getInputStream(), System.out::println);
                Executors.newSingleThreadExecutor().submit(streamGobbler);
                int exitCode = process.waitFor();
                assert exitCode == 0;*/

                //Process p = Runtime.getRuntime().exec("openvpn your_config.ovpn"+your_config);
               // Process p = Runtime.getRuntime().exec("dir "+your_config);
                Process p = Runtime.getRuntime()
                        .exec(String.format("cmd.exe /c dir %s", homeDirectory));
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(p.getErrorStream(),"GB2312"));

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

              //  System.exit(0);
            }

        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            //System.exit(-1);
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
