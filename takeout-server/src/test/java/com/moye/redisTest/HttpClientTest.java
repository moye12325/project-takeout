package com.moye.redisTest;

import com.moye.TakeoutApplication;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

@SpringBootTest(classes = TakeoutApplication.class)
public class HttpClientTest {

    @Test
    public void testGet() throws IOException {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpGet httpGet = new HttpGet("http://localhost:8080/user/shop/status");
        //发送请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //获取服务端返回的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);
        String body = EntityUtils.toString(response.getEntity());
        System.out.println("body = " + body);
        //关闭资源
        response.close();
        httpClient.close();
    }

    @Test
    public void testPost() throws IOException {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建请求对象
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/employee/login");
        StringEntity stringEntity = new StringEntity("{\"username\":\"admin\",\"password\":\"123456\"}");
        stringEntity.setContentEncoding("utf-8");
        stringEntity.setContentType("application/json");
        httpPost.setEntity(stringEntity);
        //发送请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        //获取服务端返回的状态码
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);
        String body = EntityUtils.toString(response.getEntity());
        System.out.println("body = " + body);
        //关闭资源
        response.close();
        httpClient.close();
    }
}
