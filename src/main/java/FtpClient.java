package main.java;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FtpClient {

    URL url;

    public FtpClient(String login, String pass, String ip) throws MalformedURLException {
        url = new URL("ftp://"+login+":"+pass+"@"+ip);
    }
   public List<Student> getData() throws IOException {
        List<Student> list = new ArrayList<>();
        String jsonString = "";
        URLConnection urlc = url.openConnection();
        InputStream is = urlc.getInputStream();
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        jsonString = new String(buffer, "UTF-8");

        Pattern codePattern = Pattern.compile("\"id\"\\s*:\\s*([^,]*),");
        Pattern messagePattern = Pattern.compile("\"name\"\\s*:\\s*\"([^,]*)\"");
        Matcher code_matcher = codePattern.matcher(jsonString);
        Matcher message_matcher = messagePattern.matcher(jsonString);
        while (code_matcher.find() && message_matcher.find()) {
            Student student = new Student(Integer.parseInt(code_matcher.group(1)), message_matcher.group(1));
            list.add(student);
        }
        return list;
    }
     void saveData(String json) throws IOException {
         URLConnection urlc = url.openConnection();
         OutputStream outputStream = urlc.getOutputStream();
         outputStream.write(json.getBytes());
         outputStream.close();
     }

}
