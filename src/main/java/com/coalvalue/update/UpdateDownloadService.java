/*
package com.coalvalue.update;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import com.coalvalue.update.model.Binary;
import com.coalvalue.update.model.Platform;
import com.coalvalue.update.model.Release;

public class UpdateDownloadService extends Service<Path> {
    private Release release;

    public UpdateDownloadService(Release release) {
        this.release = release;
    }

    @Override
    protected Task<Path> createTask() {
        return new Task<Path>() {
            @Override
            protected Path call() throws Exception {
                Binary toDownload = null;

                for (Binary binary : release.getBinaries()) {
                    if (isCurrentPlatform(binary.getPlatform())) {
                        toDownload = binary;
                        break;
                    }
                }

                if (toDownload == null) {
                    throw new IllegalArgumentException("This release does not contain binaries for this platform");
                }

                URL fileURL = toDownload.getHref();
                URLConnection connection = fileURL.openConnection();
                connection.connect();

                long len = connection.getContentLengthLong();

                if (len == -1) {
                    len = toDownload.getSize();
                }

                updateProgress(0, len);

                String fileName = connection.getHeaderField("Content-Disposition");
                if (fileName != null && fileName.contains("=")) {
                    fileName = fileName.split("=")[1];
                } else {
                    String url = toDownload.getHref().getPath();
                    int lastSlashIdx = url.lastIndexOf('/');

                    if (lastSlashIdx >= 0) {
                        fileName = url.substring(lastSlashIdx + 1, url.length());
                    } else {
                        fileName = url;
                    }
                }

                Path downloadFile = Paths.get(System.getProperty("java.io.tmpdir"), fileName);

                try (OutputStream fos = Files.newOutputStream(downloadFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
                     BufferedInputStream is = new BufferedInputStream(connection.getInputStream())) {
                    byte[] buffer = new byte[512];
                    int n;
                    long totalDownload = 0;

                    while ((n = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, n);
                        totalDownload += n;
                        updateProgress(totalDownload, len);
                    }
                }

                return downloadFile;
            }
        };
    }

    private boolean isCurrentPlatform(Platform platform) {
        if (platform == Platform.independent) {
            return true;
        }

        String currentPlatform = System.getProperty("os.name").toLowerCase();

        if (currentPlatform.startsWith("mac")) {
            return platform == Platform.mac;
        } else if (currentPlatform.startsWith("windows")) {
            if (System.getProperty("os.arch").contains("64")) {
                return platform == Platform.win_x64;
            } else {
                return platform == Platform.win_x86;
            }
        } else {
            throw new IllegalStateException("UpdateFX does not support this platform unless application is platform independent");
        }
    }




    protected Path call() throws Exception {
        Binary toDownload = null;

        for (Binary binary : release.getBinaries()) {
            if (isCurrentPlatform(binary.getPlatform())) {
                toDownload = binary;
                break;
            }
        }

        if (toDownload == null) {
            throw new IllegalArgumentException("This release does not contain binaries for this platform");
        }

        URL fileURL = toDownload.getHref();
        URLConnection connection = fileURL.openConnection();
        connection.connect();

        long len = connection.getContentLengthLong();

        if (len == -1) {
            len = toDownload.getSize();
        }

    //    updateProgress(0, len);

        String fileName = connection.getHeaderField("Content-Disposition");

        System.out.println("+++++++++++++++++++++++"+ fileName);

*/
/*        if (fileName != null && fileName.contains("=")) {
            fileName = fileName.split("=")[1];
        } else {
            String url = toDownload.getHref().getPath();
            int lastSlashIdx = url.lastIndexOf('/');

            if (lastSlashIdx >= 0) {
                fileName = url.substring(lastSlashIdx + 1, url.length());
            } else {
                fileName = url;
            }
        }*//*


        String raw = connection.getHeaderField("Content-Disposition");
        if (raw != null && raw.indexOf("=") != -1) {
            String[] args = raw.split(";");
            for (String arg : args) {
                if (arg.trim().startsWith("filename=")) {
                    fileName = arg.split("=")[1];
                    fileName = fileName.replace("\"", "");
                    break;
                }
            }
        }

        Path downloadFile = Paths.get(System.getProperty("java.io.tmpdir"), fileName);

        try (OutputStream fos = Files.newOutputStream(downloadFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
             BufferedInputStream is = new BufferedInputStream(connection.getInputStream())) {
            byte[] buffer = new byte[512];
            int n;
            long totalDownload = 0;

            while ((n = is.read(buffer)) != -1) {
                fos.write(buffer, 0, n);
                totalDownload += n;
            //    updateProgress(totalDownload, len);
            }
        }

        return downloadFile;
    }
}*/
