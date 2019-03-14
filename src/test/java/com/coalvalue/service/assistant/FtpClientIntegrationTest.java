package com.coalvalue.service.assistant;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class FtpClientIntegrationTest {

    private FakeFtpServer fakeFtpServer;

    private FtpClient ftpClient;

   // @Before
    public void setup() throws IOException {
/*        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount("user", "password", "/data"));

        FileSystem fileSystem = new UnixFakeFileSystem();
        fileSystem.add(new DirectoryEntry("/data"));
        fileSystem.add(new FileEntry("/data/foobar.txt", "abcdef 1234567890"));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(0);

        fakeFtpServer.start();*/

        ftpClient = new FtpClient("localhost", 2121, "admin", "admin");
        ftpClient.open();
    }

   // @After
    public void teardown() throws IOException {
        ftpClient.close();
       // fakeFtpServer.stop();
    }

 //   @Test
    public void givenRemoteFile_whenListingRemoteFiles_thenItIsContainedInList() throws IOException {
        Collection<String> files = ftpClient.listFiles("");

        assertThat(files).contains("foobar.txt");
    }

 //   @Test
    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() throws IOException {
        ftpClient.downloadFile("/foobar.txt", "downloaded_buz.txt");

        assertThat(new File("downloaded_buz.txt")).exists();
        new File("downloaded_buz.txt").delete(); // cleanup
    }

   // @Test
    public void givenLocalFile_whenUploadingIt_thenItExistsOnRemoteLocation() throws URISyntaxException, IOException {
        System.out.println("000000000000------"+getClass().getClassLoader().getResource("ftp/baz.txt").getPath());
        File file = new File(getClass().getClassLoader().getResource("./ftp/baz.txt").toURI());


        ftpClient.putFileToPath(file, "/buz.txt", file.getName());

        //assertThat(fakeFtpServer.getFileSystem().exists("/buz.txt")).isTrue();
    }


}