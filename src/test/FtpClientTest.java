
import main.java.FtpClient;
import main.java.Student;
import org.testng.Assert;
import org.testng.annotations.Test;
import sun.net.ftp.FtpLoginException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

public class FtpClientTest {

   @Test(expectedExceptions = ConnectException.class,  enabled = true)
    public void getData_failConnect() throws IOException {
       FtpClient ftpClient = new FtpClient("123", "123", "127.0.0.1/students.json");
       ftpClient.getData();
    }

    @Test(expectedExceptions = FtpLoginException.class)
    public void getData_failLogin() throws IOException {
        FtpClient ftpClient = new FtpClient(" ", "123", "127.0.0.1/students.json");
        ftpClient.getData();
    }
    @Test(expectedExceptions = FtpLoginException.class)
    public void getData_failPass() throws IOException {
        FtpClient ftpClient = new FtpClient("123", " ", "127.0.0.1/students.json");
        ftpClient.getData();
    }
    @Test(expectedExceptions = FileNotFoundException.class)
    public void getData_failIP() throws IOException {
        FtpClient ftpClient = new FtpClient("123", "123", "127.0.0.1/students.jso");
        ftpClient.getData();
    }
    @Test
    public void getData_success() throws IOException {
        FtpClient ftpClient = new FtpClient("123", "123", "127.0.0.1/students.json");
        List<Student> list = ftpClient.getData();
        Assert.assertNotNull(list);
    }

}
