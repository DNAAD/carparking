package com.coalvalue.configuration;

import com.coalvalue.domain.pojo.StatusInfo;
import com.coalvalue.update.model.Release;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import com.github.dockerjava.core.command.WaitContainerResultCallback;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.ProcessingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class Docker {

    protected transient Logger logger = LoggerFactory.getLogger(getClass());

/*
    ls -la /var/run/docker.sock


    sudo curl --unix-socket /var/run/docker.sock http::/containers/json

    sudo echo -e "GET /images/json HTTP/1.0\r\n" | sudo nc -U /var/run/docker.sock

    sudo nano /lib/systemd/system/docker.service



#ExecStart=/usr/bin/dockerd -H fd://
            #ExecStart=/usr/bin/dockerd -H unix:///var/run/docker.sock -H tcp://0.0.0.0:5678
    ExecStart=/usr/bin/dockerd -H fd:// -H tcp://0.0.0.0:5678


java -jar -Dspring.profiles.active=devlocal -DDOCKER_HOST=tcp://0.0.0.0:5678 -Dimei=100 webreport-0.1.0-SNAPSHOT.jar





            */

    // docker-java requires no configuration
/*
    private Observable<File> download(final URL url) {
        return Observable.fromCallable(() -> {
            ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            File outFile = File.createTempFile("docker", "download");
            outFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(outFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            return outFile;
        });
    }
    private Observable<Boolean> install(final File file) {
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        return Observable.fromCallable(() -> {
            try (FileInputStream fs = new FileInputStream(file)) {
                dockerClient.loadImageCmd(fs).exec();
            }
            return true;
        });
    }
*/

    @Value("${DOCKER_HOST:avc}")
    private String DOCKER_HOST;



    DockerClient dockerClient = null;
    public StatusInfo isValid(){

        StatusInfo statusInfo = new StatusInfo();
        if(dockerClient== null){
           dockerClient = getDockerClient();
        }

        try {
            InfoCmd infoCmd = dockerClient.infoCmd();

            VersionCmd versionCmd = dockerClient.versionCmd();
            logger.debug("docker inforCmd:{}", infoCmd.exec());
            logger.debug("docker versionCmd:{}", versionCmd.exec());
            statusInfo.setStatus(true);

        }catch (ProcessingException e){
            logger.error("docker ProcessingException:{}", e.getMessage());
            statusInfo.setStatus(false);

        }
        return statusInfo;
    }



    public DockerClient  getDockerClient(){


        if(dockerClient!= null){
            return dockerClient;
        }

        System.out.println("----------------我梦在docker中啊啊啊啊啊啊 ");
        Properties properties = new Properties();
        // properties.setProperty("registry.email", "info@baeldung.com");
        //   properties.setProperty("registry.password", "baeldung");
        //    properties.setProperty("registry.username", "baaldung");
        //  properties.setProperty("DOCKER_CERT_PATH", "/home/baeldung/.docker/certs");
        //   properties.setProperty("DOCKER_CONFIG", "/home/baeldung/.docker/");
        //  properties.setProperty("DOCKER_TLS_VERIFY", "1");
        properties.setProperty("DOCKER_HOST", DOCKER_HOST);//unix:///var/run/docker.sock");//
        //   tcp://localhost:2375

        DefaultDockerClientConfig config
                = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withProperties(properties).build();


        if(DOCKER_HOST!= null){
            dockerClient = DockerClientBuilder.getInstance(config).build();

        }else{
            dockerClient = DockerClientBuilder.getInstance().build();

        }

        try {
            InfoCmd infoCmd = dockerClient.infoCmd();

            VersionCmd versionCmd = dockerClient.versionCmd();
            logger.debug("docker inforCmd:{}", infoCmd.exec());
            logger.debug("docker versionCmd:{}", versionCmd.exec());
        }catch (ProcessingException e){
            logger.error("docker ProcessingException:{}", e.getMessage());
        }
        return dockerClient;



    }


    public void all_together____(Release message){


        DockerClient dockerClient = getDockerClient();

        try {

            System.out.println("正在 下载 config image Network--------------------------------------------------");
            dockerClient.pullImageCmd("docker.yulinmei.cn/config")
                    .withTag("latest")
                    .exec(new PullImageResultCallback(){
                        @Override
                        public void onNext(PullResponseItem item) {
                            super.onNext(item);
                            System.out.println("在 PullImageResultCallback" + item.getStatus());
                        }
                    })
                    .awaitCompletion(60*30, TimeUnit.SECONDS);
            System.out.println("下载完成 下载 config image Network--------------------------------------------------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

/*

        List<Container> containers2 = dockerClient.listContainersCmd()
                .withLabelFilter(testLabel)
                .withShowAll(true)
                .exec();
        for (Container container : containers2) {
            LOG.info("listContainer: id=" + container.getId() + " image=" + container.getImage());
        }


        final Container existing = dockerClient.listContainersCmd().withLabelFilter().get("containerId");
*/
        CreateContainerResponse container = dockerClient.createContainerCmd("docker.yulinmei.cn/config")
                .withCmd("touch", "/test")
                .exec();


/*        dockerClient.startContainerCmd(container.getId()).exec();

        dockerClient.stopContainerCmd(container.getId()).exec();

        dockerClient.killContainerCmd(container.getId()).exec();*/













    }


    public void isValid(DockerClient dockerClient) {

        List<Container> containers = dockerClient.listContainersCmd().exec();

        List<Image> images = dockerClient.listImagesCmd().exec();
        images.stream().forEach(e->{

            logger.info("发现了一个 新的 image");
            logger.info("getRepoTags {}",e.getRepoTags());

            logger.info("getId {}",e.getId());

            logger.info("getCreated {}",e.getCreated());
            logger.info("getParentId {}",e.getParentId());

            logger.info("getVirtualSize {}",e.getVirtualSize());
            logger.info("getSize {}",e.getSize());

        });


        String dockerImage = "arm32v7/hello-world:latest";

        Optional<Image> configImageOp = images.stream().filter(e-> {
            if(e== null ){
                return false;
            }
            if(e.getRepoTags()== null ){

                System.out.println(e+"这个image reporteTags 是null----------=");
                return false;
            }
           List<String> list=  Arrays.asList(e.getRepoTags());
            logger.info("--- {}");
            list.forEach(a->System.out.println(a+"0000000----------="));





            if(e.getRepoTags()[0].equals(dockerImage)){
                return true;


        }else{
                return false;
            }

        }).findFirst();


        if (configImageOp.isPresent()){
            Image image = configImageOp.get();


            logger.info(" 存在Image 无需更新");
            return;

        }else{
            logger.info(" 不存在 需要及时更新 config iamge");

        }

        try {

            System.out.println("正在 下载 config image Network--------------------------------------------------");
            dockerClient.pullImageCmd(dockerImage)
                    .withTag("latest")
                    .exec(new PullImageResultCallback(){
                        @Override
                        public void onNext(PullResponseItem item) {
                            super.onNext(item);
                            System.out.println("在 PullImageResultCallback" + item.getStatus());
                        }
                    })
                    .awaitCompletion(60*30, TimeUnit.SECONDS);
            System.out.println("下载完成 下载 config image Network--------------------------------------------------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        Optional<Container> containerOptional = containers.stream().filter(e-> e.getImage().equals(dockerImage)).findFirst();
        if(containerOptional.isPresent()){

            logger.debug("存在这个 image 的 container"+ dockerImage);

            Container container = containerOptional.get();
            logger.info("getLabels {}",container.getLabels());

            logger.info("getImage {}",container.getImage());
            logger.info("getImageId {}",container.getImageId());

            logger.info("getStatus {}",container.getStatus());
            logger.info("getPorts {}",container.getPorts());
            logger.info("getState {}",container.getState());
        }



        Optional<Image> imageOptional = images.stream().filter(e->  e.getRepoTags()!= null && e.getRepoTags()[0].equals(dockerImage)).findFirst();

        if(imageOptional.isPresent()){
            Image image = imageOptional.get();
            System.out.println("存在这个 image "+ dockerImage);

            logger.info("getId {}",image.getId());

            logger.info("getCreated {}",image.getCreated());
            logger.info("getParentId {}",image.getParentId());

            logger.info("getRepoTags {}",image.getRepoTags());
            logger.info("getVirtualSize {}",image.getVirtualSize());
            logger.info("getSize {}",image.getSize());
        }



    }


    public void backup(DockerClient dockerClient) {

        List<Container> containers = dockerClient.listContainersCmd().exec();

/*

        containers.stream().forEach(e->{
            logger.info("getLabels {}",e.getLabels());

            logger.info("getImage {}",e.getImage());
            logger.info("getImageId {}",e.getImageId());

            logger.info("getStatus {}",e.getStatus());
            logger.info("getPorts {}",e.getPorts());
            logger.info("getState {}",e.getState());

            System.out.println("Container--------------------------------------------------"+ e.toString());
        });
*/




        Container container = containers.stream().filter(e-> e.getImage().equals("docker.yulinmei.cn/carparking")).findFirst().get();
        logger.info("getLabels {}",container.getLabels());

        logger.info("getImage {}",container.getImage());
        logger.info("getImageId {}",container.getImageId());

        logger.info("getStatus {}",container.getStatus());
        logger.info("getPorts {}",container.getPorts());
        logger.info("getState {}",container.getState());


        List<Image> images = dockerClient.listImagesCmd().exec();
        Image image = images.stream().filter(e-> e.getRepoTags()[0].equals("docker.yulinmei.cn/carparking:latest")).findFirst().get();

        System.out.println("Image--------------------------------------------------"+ image.toString());

        logger.info("getId {}",image.getId());

        logger.info("getCreated {}",image.getCreated());
        logger.info("getParentId {}",image.getParentId());

        logger.info("getRepoTags {}",image.getRepoTags());
        logger.info("getVirtualSize {}",image.getVirtualSize());
        logger.info("getSize {}",image.getSize());

/*
        images.stream().forEach(e->{
            logger.info("getId {}",e.getId());

            logger.info("getCreated {}",e.getCreated());
            logger.info("getParentId {}",e.getParentId());

            logger.info("getRepoTags {}",e.getRepoTags());
            logger.info("getVirtualSize {}",e.getVirtualSize());
            logger.info("getSize {}",e.getSize());

            System.out.println("Image--------------------------------------------------"+ e.toString());
        });*/



        //  dockerClient.createContainerCmd();


        List<SearchItem> items = dockerClient.searchImagesCmd("Java").exec();
        items.stream().forEach(e->{
            System.out.println("SearchItem--------------------------------------------------"+ e.toString());
        });



        ListVolumesResponse volumesResponse = dockerClient.listVolumesCmd().exec();
        List<InspectVolumeResponse> volumes = volumesResponse.getVolumes();
        volumes.stream().forEach(e->{
            System.out.println("InspectVolumeResponse--------------------------------------------------"+ e.toString());
        });

        List<Network> networks = dockerClient.listNetworksCmd().exec();
        networks.stream().forEach(e->{
            System.out.println("Network--------------------------------------------------"+ e.toString());
        });
    }

    private boolean isValid(Object r, String threshold) {
        Boolean valid = true;
        String sensorReadingStr = r.toString();
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();
        try {
            dockerClient
                    .pullImageCmd("randhirkumars/python-docker")
                    .withTag("latest")
                    .exec(new PullImageResultCallback() {
                        @Override
                        public void onNext(PullResponseItem item) {
                            super.onNext(item);
                            System.out.println(item.getStatus());
                        }
                    }).awaitCompletion();
            CreateContainerResponse createContainerResponse = dockerClient
                    .createContainerCmd("randhirkumars/python-docker")
                    .withEnv("RECORD=" + sensorReadingStr, "THRESHOLD=" + threshold)
                   // .withBinds(new Bind("/var/run/docker.sock", new Volume("/var/run/docker.sock")))
                    .exec();
            dockerClient
                    .startContainerCmd(createContainerResponse.getId())
                    .exec();
            dockerClient
                    .waitContainerCmd(createContainerResponse.getId())
                    .exec(new WaitContainerResultCallback())
                    .awaitCompletion();
            final List<Frame> loggingFrames = getLoggingFrames(dockerClient, createContainerResponse.getId());
            for (final Frame frame : loggingFrames) {
                if (frame.toString().indexOf("INVALID") > 0) {
                    valid = false;
                }
            }
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }
    private List<Frame> getLoggingFrames(DockerClient dockerClient, String containerId) throws Exception {
        FrameReaderITestCallback collectFramesCallback = new FrameReaderITestCallback();
        dockerClient.logContainerCmd(containerId).withStdOut(true).withStdErr(true)
                .withTailAll()
                .exec(collectFramesCallback).awaitCompletion();
        return collectFramesCallback.frames;
    }
    public static class FrameReaderITestCallback extends LogContainerResultCallback {
        public List<Frame> frames = new ArrayList<>();
        @Override
        public void onNext(Frame item) {
            frames.add(item);
            super.onNext(item);
        }
    }
}
