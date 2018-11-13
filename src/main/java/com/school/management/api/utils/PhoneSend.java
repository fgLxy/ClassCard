package com.school.management.api.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class PhoneSend {

    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static String accessKeyId = "LTAIZkoTdcFwuDL5";
    private static String accessKeySecret = "pE46EkZ8Sez8ToxA4ueZkZqC58IbRx";
    //    短信签名
    private static String signName = "智慧班牌";
    //    短信模板
    private static String identifyingTemplateCode = "SMS_148865185";
    //
    private static String registryTemplateCode = "5MhTg6v2JWUgnAhEWhYZmdVBfWb5b7";

    public static void init(String accessKeyId, String accessKeySecret, String signName, String identifyingTempleteCode,
                            String registryTemplateCode) {
        PhoneSend.accessKeyId = accessKeyId;
        PhoneSend.accessKeySecret = accessKeySecret;
        PhoneSend.signName = signName;
        PhoneSend.identifyingTemplateCode = identifyingTempleteCode;
        PhoneSend.registryTemplateCode = registryTemplateCode;
    }

    private static SendSmsResponse sendSms(String mobile, String templateParam, String templateCode)
            throws ClientException {

        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        // 初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号
        request.setPhoneNumbers(mobile);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);

        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        // 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");

        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId("yourOutId");

        // hint 此处可能会抛出异常，注意catch

        return acsClient.getAcsResponse(request);
    }

    public static SendSmsResponse sendNewUserNotice(String mobile, String username, String password) {
        try {
            return sendSms(mobile, "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}",
                    registryTemplateCode);
        } catch (ClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SendSmsResponse sendIdentifyingCode(String mobile, String code) {
        try {
            return sendSms(mobile, "{\"code\":\"" + code + "\"}", identifyingTemplateCode);
        } catch (ClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static SendSmsResponse sendCode(String mobile) {
        try {
            return sendSms(mobile, null, identifyingTemplateCode);
        } catch (ClientException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
