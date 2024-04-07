package com.moye.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Slf4j
public class QiNiuOssUtil {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domain;

    /**
     *
     * 文件上传到七牛云
     *
     * */
    public String upload(byte[] bytes, String objectName){
        try {
            //1.获取文件上传的流 这里传入的bytes
            //2.创建日期目录分隔
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath=dateFormat.format(new Date());
            //3.获取文件名 这里的 objectName就是源文件名
            String suffix = objectName.substring(objectName.lastIndexOf("."));
            String filename=datePath+"/"+ UUID.randomUUID().toString().replace("-","")+suffix;
            //4.构造一个带指定Region对象的配置类
            Configuration cfg = new Configuration(Region.huadongZheJiang2());
            UploadManager uploadManager = new UploadManager(cfg);
            //5.获取七牛云提供的token
            Auth auth = Auth.create(accessKey,secretKey);
            String upToken = auth.uploadToken(bucket);
            uploadManager.put(bytes,filename,upToken);
            //文件访问的路径 http://pic.icefun.icu/filename
            StringBuilder stringBuilder = new StringBuilder("http://");
            stringBuilder.append(domain).append("/").append(filename);
            log.info("文件上传到：{}",stringBuilder.toString());

            return stringBuilder.toString();
        } catch (QiniuException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getMessage());
            System.out.println("Error Code:" + oe.code());
            System.out.println("Request ID:" + oe.url());
            System.out.println("Host ID:" + oe.url());
        }catch (Exception ce){
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        }
        return null;
    }


    /**
     * 文件上传
     *
     * @param bytes      要上传的数据
     * @param objectName 上传数据保存的文件名
     * @return 文件的访问URL
     */
    public String upload1(byte[] bytes, String objectName) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huadong()); // 根据具体的地区进行设置
        UploadManager uploadManager = new UploadManager(cfg);

        // 生成上传凭证
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            // 创建上传请求
            Response response = uploadManager.put(bytes, objectName, upToken);

            if (!response.isOK()) {
                log.error("上传失败: {}", response.toString());
                return null;
            }

        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error("Qiniu Exception: {}", r.toString());
            try {
                log.error("Response body: {}", r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }

        // 文件访问路径规则 http://<Domain>/<ObjectName>
        String fileUrl = String.format("http://%s/%s", domain, objectName);
        log.info("文件上传到: {}", fileUrl);

        return fileUrl;
    }

//    /**
//     * 文件上传
//     *
//     * @param bytes
//     * @param objectName
//     * @return
//     */
//    public String uploadAili(byte[] bytes, String objectName) {
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//        try {
//            // 创建PutObject请求。
//            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//
//        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
//        StringBuilder stringBuilder = new StringBuilder("https://");
//        stringBuilder
//                .append(bucketName)
//                .append(".")
//                .append(endpoint)
//                .append("/")
//                .append(objectName);
//
//        log.info("文件上传到:{}", stringBuilder.toString());
//
//        return stringBuilder.toString();
//    }


}
