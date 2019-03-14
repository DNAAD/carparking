package com;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;
import io.reactivex.Observable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Docker {

    // docker-java requires no configuration
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


    private void all_together(){
        // And all together now
/*        download(someUrl).flatMap(this::install).subscribe(result -> {
            logger.info("Installed? {}", result);
        });*/
    }

}
