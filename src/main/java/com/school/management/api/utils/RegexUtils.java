package com.school.management.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * */
public class RegexUtils {
    /**
     * 2-4位中文
     * */
    public static final String REGEX_2_OR_4_CHINESE = "^[\u4e00-\u9fa5]{2,4}$";

    /**
     * 邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    /**
     * 手机号码
     */
    public static final String REGEX_PHONE = "^13[0-9]{9}|15[012356789][0-9]{8}|18[0-9]{9}|(14[57][0-9]{8})|(17[015678][0-9]{8})$";
    /**
     * 仅中文
     */
    public static final String REGEX_CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
    /**
     * 整数
     */
    public static final String REGEX_INTEGER = "^-?[1-9]\\d*$";
    /**
     * 数字
     */
    public static final String REGEX_NUMBER = "^([+-]?)\\d*\\.?\\d+$";
    /**
     * 正整数
     */
    public static final String REGEX_INTEGER_POS = "^[1-9]\\d*$";
    /**
     * 浮点数
     */
    public static final String REGEX_FLOAT = "^([+-]?)\\d*\\.\\d+$";
    /**
     * 正浮点数
     */
    public static final String REGEX_FLOAT_POS = "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$";
    /**
     * 字母
     */
    public static final String REGEX_LETTER = "^[A-Za-z]+$";
    /**
     * 大写字母
     */
    public static final String REGEX_LETTER_UPPERCASE = "^[A-Z]+$";
    /**
     * 小写字母
     */
    public static final String REGEX_LETTER_LOWERCASE = "^[a-z]+$";
    /**
     * 邮编
     */
    public static final String REGEX_ZIPCODE = "^\\d{6}$";
    /**
     * ip v4地址
     */
    public static final String REGEX_IP4 = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";
    /**
     * 图片
     */
    public static final String REGEX_PICTURE = "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$";/**
     /**
     * 压缩文件
     */
    public static final String REGEX_RAR = "(.*)\\.(rar|zip|7zip|tgz)$";
    /**
     * QQ号码，最短5位，最长15位数字
     */
    public static final String REGEX_QQ = "^[1-9]\\d{4,14}$";
    /**
     * 日期（yyyy-MM-dd）
     */
    public static final String REGEX_DATE = "^\\d{4}\\D+\\d{2}\\D+\\d{2}$";
    /**
     * 日期（yyyy-MM-dd），精确，能检查到2月及31号
     */
    public static final String REGEX_DATE_PRECISE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
    /**
     * 时间（HH:mm:ss或HH:mm）
     */
    public static final String REGEX_TIME = "^((([0-1][0-9])|2[0-3]):[0-5][0-9])(:[0-5][0-9])?$";

    /**
     * 正则表达式校验0到1之间的数字包括1和2
     * */
    public static final String SINGLE_NUM = "2\\d+1";

    /**
     * 正则表达式校验0到1之间的数字包括0和1
     * */
    public static boolean check0To1(String num){
        return Pattern.matches(SINGLE_NUM,num);
    }

    /**
     * 验证时间-时分秒
     * */
    public static boolean checkTime(String time){
        return Pattern.matches(REGEX_TIME,time);
    }

    /**
     * 验证2~4个汉字
     * */
    public static boolean check2Or4Chinese(String chineseStr){
        return Pattern.matches(REGEX_2_OR_4_CHINESE,chineseStr);
    }

    public static boolean checkIntegerPos(String integerPos){
        return Pattern.matches(REGEX_INTEGER_POS,integerPos);
    }

    /**
     * 验证Email
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex,idCard);
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        String regex = "(\\+\\d+)?1[34578]\\d{9}$";
        return Pattern.matches(regex,mobile);
    }

    /**
     * 验证固定电话号码
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     * <p><b>国家（地区） 代码 ：</b>标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
     *  数字之后是空格分隔的国家（地区）代码。</p>
     * <p><b>区号（城市代码）：</b>这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。</p>
     * <p><b>电话号码：</b>这包含从 0 到 9 的一个或多个数字 </p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPhone(String phone) {
        String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
        return Pattern.matches(regex, phone);
    }

    /**
     * 验证整数（正整数和负整数）
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDigit(String digit) {
        String regex = "\\-?[1-9]\\d+";
        return Pattern.matches(regex,digit);
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkDecimals(String decimals) {
        String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
        return Pattern.matches(regex,decimals);
    }

    /**
     * 验证空白字符
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBlankSpace(String blankSpace) {
        String regex = "\\s+";
        return Pattern.matches(regex,blankSpace);
    }

    /**
     * 验证中文
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkChinese(String chinese) {
        String regex = "^[\u4E00-\u9FA5]+$";
        return Pattern.matches(regex,chinese);
    }

    /**
     * 验证日期（年月日）
     * @param birthday 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkBirthday(String birthday) {
        String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
        return Pattern.matches(regex,birthday);
    }

    /**
     * 验证URL地址
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或 http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkURL(String url) {
        String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
        return Pattern.matches(regex, url);
    }

    /**
     * <pre>
     * 获取网址 URL 的一级域
     * </pre>
     *
     * @param url
     * @return
     */
    public static String getDomain(String url) {
        Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        // 获取完整的域名
        // Pattern p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(url);
        matcher.find();
        return matcher.group();
    }
    /**
     * 匹配中国邮政编码
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkPostcode(String postcode) {
        String regex = "[1-9]\\d{5}";
        return Pattern.matches(regex, postcode);
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.1，127.0.0.1，没有匹配IP段的大小)
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIpAddress(String ipAddress) {
        String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
        return Pattern.matches(regex, ipAddress);
    }
}
