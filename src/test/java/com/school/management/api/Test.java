package com.school.management.api;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.school.management.api.entity.Comment;
import com.school.management.api.entity.News;
import com.school.management.api.entity.Quantify;
import com.school.management.api.entity.Student;
import com.school.management.api.utils.ClassUtils;
import com.school.management.api.utils.FileUtils;
import com.school.management.api.utils.ImgUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main1(String[] atgs) throws ClientException {

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

        //替换成你的AK
        final String accessKeyId = "yourAccessKeyId";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "yourAccessKeySecret";//你的accessKeySecret，参考本文档步骤2

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();

        //使用post提交
        request.setMethod(MethodType.POST);

        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
        // 批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
        // 发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.setPhoneNumbers("1500000000");

        //必填:短信签名-可在短信控制台中找到
        request.setSignName("云通信");

        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_1000000");

        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,
        // 比如短信内容中包含\r的情况在JSON中需要表示成,
        // 否则会导致JSON在服务端解析失败
        request.setTemplateParam("{name:Tom, code:123}");

        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
        }
    }

    public static void main2(String[] args) {
        String str = "data: [{quantifyId: 3,quantifyType: 3,quantifyRemark: 早操无故不到,quantifyPhotoUrl: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg,quantifyPhotoUrl2: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg,quantifyPhotoUrl3: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg,quantifyPhotoUrl4: null,quantifyPhotoUrl5: null,quantifyPhotoUrl6: null,quantifyPhotoUrl7: null,quantifyPhotoUrl8: null,quantifyPhotoUrl9: null,classId: 1,quantifyUser: 小红,quantifyDate: 2018-09-17}, { quantifyId: 2, quantifyType: 2, quantifyRemark: 大声吵闹, quantifyPhotoUrl: http://d.hiphotos.baidu.com/image/pic/item/bba1cd11728b4710f294b823cfcec3fdfd0323a1.jpg, quantifyPhotoUrl2: http://d.hiphotos.baidu.com/image/pic/item/bba1cd11728b4710f294b823cfcec3fdfd0323a1.jpg, quantifyPhotoUrl3: http://d.hiphotos.baidu.com/image/pic/item/bba1cd11728b4710f294b823cfcec3fdfd0323a1.jpg, quantifyPhotoUrl4: null, quantifyPhotoUrl5: null, quantifyPhotoUrl6: null, quantifyPhotoUrl7: null, quantifyPhotoUrl8: null, quantifyPhotoUrl9: null, classId: 1, quantifyUser: 小红, quantifyDate: 2018-09-17 }, { quantifyId: 1, quantifyType: 1, quantifyRemark: 地上有纸屑，角落未打扫干净，窗台有灰, quantifyPhotoUrl: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg, quantifyPhotoUrl2: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg, quantifyPhotoUrl3: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg, quantifyPhotoUrl4: null, quantifyPhotoUrl5: null, quantifyPhotoUrl6: null, quantifyPhotoUrl7: null, quantifyPhotoUrl8: null, quantifyPhotoUrl9: null, classId: 1, quantifyUser: 小红, quantifyDate: 2018-09-17 }, { quantifyId: 10, quantifyType: 1, quantifyRemark: 角落没打扫干净, quantifyPhotoUrl: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg, quantifyPhotoUrl2: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg, quantifyPhotoUrl3: http://a.hiphotos.baidu.com/image/pic/item/b64543a98226cffceee78e5eb5014a90f703ea09.jpg, quantifyPhotoUrl4: null, quantifyPhotoUrl5: null, quantifyPhotoUrl6: null, quantifyPhotoUrl7: null, quantifyPhotoUrl8: null, quantifyPhotoUrl9: null, classId: 1, quantifyUser: 小红, quantifyDate: 2018-09-17}]";


        List<Map<Object, Object>> data = new Gson().fromJson(new Gson().toJson(str), new TypeToken<List<Map<Object, Object>>>(){}.getType());
        List<Map<String, Object>> result = new ArrayList<>();

        System.out.println(new Gson().toJson(str));
        //将 data 转换成 result  并且格式为之前的那个图

    }

    public static void main(String[] args) {

        Method[] methods = ClassUtils.getSetPhoto(Comment.class);

    }
}
