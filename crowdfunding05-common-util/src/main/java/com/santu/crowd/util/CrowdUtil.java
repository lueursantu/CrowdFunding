package com.santu.crowd.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.santu.crowd.constant.CrowdConstant;
import com.santu.crowd.exception.LoginFailedException;
import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import javax.mail.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;

/**
 * @author Santu
 * @date 2021/11/16 10:29
 */
public class CrowdUtil {
    public static boolean judgeRequestType(HttpServletRequest request){
        String accept = request.getHeader("Accept");
        String header = request.getHeader("X-Requested-With");
        return (accept != null && accept.contains("application/json"))
                ||
                (header != null && header.equals("XMLHttpRequest"));
    }
    /**
     * 此方法是用于给字符串进行md5加密的工具方法
     * @return 进行md5加密后的结果
     * @param source 传入要加密的内容
     */
    public static String md5(String source){

        if (source == null || source.length() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            //表示算法名
            String algorithm = "md5";

            //得到MessageDigest对象，设置加密方式为md5
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            //将获得的明文字符串转换为字节数组
            byte[] input = source.getBytes();

            //对转换得到的字节数组进行md5加密
            byte[] output = messageDigest.digest(input);

            //设置BigInteger的signum
            //signum : -1表示负数、0表示零、1表示正数
            int signum = 1;

            //将字节数组转换成Big Integer
            BigInteger bigInteger = new BigInteger(signum,output);

            //设置将bigInteger的值按照16进制转换成字符串，最后全部转换成大写，得到最后的加密结果
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            //返回加密后的字符串
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //触发异常则返回null
        return null;
    }


    /**
     * 这里短信业务用邮件代替， phoneNum暂时表示邮件地址
     * @return
     */
    public static ResultEntity<String> sendShortMessage(String phoneNum){

        Random random = new Random();
        String code="";
        for (int i=0;i<6;i++)
        {
            code+=random.nextInt(10);
        }

        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host","smtp.163.com");

        properties.setProperty("mail.transport.protocol","smtp");

        properties.setProperty("mail.smtp.auth","true");


        try {
            //加载私人邮箱和密码
            Properties emailProp = new Properties();
            // 使用InPutStream流读取properties文件
            InputStream in = CrowdUtil.class.getResourceAsStream("/santuEmail.properties");
            emailProp.load(in);

            String username = emailProp.getProperty("username");
            String password = emailProp.getProperty("password");

            //创建一个session对象
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username,password);
                }
            });

            //开启debug模式
            session.setDebug(true);

            //获取连接对象
            Transport transport = session.getTransport();

            //连接服务器
            transport.connect("smtp.163.com",username,password);

            //创建邮件对象
            MimeMessage mimeMessage = new MimeMessage(session);

            //邮件发送人
            mimeMessage.setFrom(new InternetAddress(username));

            //邮件接收人
            mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(phoneNum));

            //邮件标题
            mimeMessage.setSubject("三兔验证码");

            //邮件内容
            mimeMessage.setContent("您的验证码是："+ code,"text/html;charset=UTF-8");

            //发送邮件
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

            //关闭连接
            transport.close();

            return ResultEntity.successWithData(code);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @Test
    public void testMail() {
        sendShortMessage("1812553272@qq.com");
    }

    /**
     * 上传文件到oss
     * @param endPoint
     * @param accessKeyId
     * @param accessKeySecret
     * @param inputStream
     * @param bucketName
     * @param bucketDomain
     * @param originalName
     * @return
     */
    public static ResultEntity<String> uploadFileToOSS(
            String endPoint,
            String accessKeyId,
            String accessKeySecret,
            InputStream inputStream,
            String bucketName,
            String bucketDomain,
            String originalName ){

        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endPoint,accessKeyId,accessKeySecret);

        // 生成上传文件的目录，按照日期来划分目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // 生成上传文件在OSS服务器上保存的文件名,通过uuid生成随机uuid，将其中的“-”删去（替换成空字符串）
        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        // 使用目录、文件主体名称、文件扩展名拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;


        try {
            // 调用OSS客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName,objectName,inputStream);

            // 从响应结果中获取具体的响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();

            // 根据响应状态判断是否成功
            if (responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;

                // 返回成功，并带上访问路径
                return ResultEntity.successWithData(ossFileAccessPath);
            }else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 没有成功 获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();

                return ResultEntity.failed("当前响应状态码=" + statusCode + " 错误消息=" + errorMessage);
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        } finally {
            // 关闭OSSClient
            ossClient.shutdown();
        }

    }

    @Test
    public void testOSS() throws FileNotFoundException {
        InputStream is = new FileInputStream("D:\\Project\\JavaProjects\\CrowdFunding\\crowdfunding05-common-util\\src\\main\\resources\\asd.png");
        uploadFileToOSS("https://oss-cn-qingdao.aliyuncs.com", "LTAI5tDYqrKh186t7KzQaczh",
                "E2Vm1syLsOvaT3hNVvr5RhF573rMjK",is,
                "santu-oss", "https://santu-oss.oss-cn-qingdao.aliyuncs.com", "asd.png");
    }
}
