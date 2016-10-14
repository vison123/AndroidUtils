package com.vison.androidutils.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {
    //验证手机号格式
    public static boolean isMobileNo(String mobiles) {
        Pattern p = Pattern.compile("^[1][34578]\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    //验证密码格式:英文大小写及字母6-16位
    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile("^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S.{6,16}$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    //验证短信验证码格式：6位数字
    public static boolean isSMSCode(String smsCode) {
        Pattern p = Pattern.compile("^[0-9]{6}$");
        Matcher m = p.matcher(smsCode);
        return m.matches();
    }

    //验证密码格式
    public static boolean isUserName(String userName) {
        Pattern p = Pattern.compile("^[a-zA-Z_0-9]{5,20}");
        Matcher m = p.matcher(userName);
        return m.matches();
    }

    //验证真实姓名格式:中文和英文字母
    public static boolean isRealName(String realName) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]{1,10}$");
        Matcher m = p.matcher(realName);
        return m.matches();
    }

    //验证座机号格式:数字和-
    public static boolean isTelephone(String telephone) {
        Pattern p = Pattern.compile("^0\\d{2,3}-?\\d{7,8}$");
        Matcher m = p.matcher(telephone);
        return m.matches();
    }

    //验证qq格式
    public static boolean isQQNo(String qqNo) {
        Pattern p = Pattern.compile("^[1-9][0-9]{4,19}+$");
        Matcher m = p.matcher(qqNo);
        return m.matches();
    }

    //验证微信号格式
    public static boolean isWechat(String wechat) {
        Pattern p = Pattern.compile("^\\w{5,40}+$");
        Matcher m = p.matcher(wechat);
        return m.matches();
    }

    //验证Email格式
    public static boolean isEmail(String wechat) {
        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?+$");
        Matcher m = p.matcher(wechat);
        return m.matches();
    }

    //验证职务格式:中文和英文字母
    public static boolean isJobTitle(String wechat) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z]+$");
        Matcher m = p.matcher(wechat);
        return m.matches();
    }

    //验证组织机构代码格式：8位数字和字母的组合
    public static boolean isOrgCode(String orgCode) {
        Pattern p = Pattern.compile("^[a-zA-Z0-9-]{10}$");
        Matcher m = p.matcher(orgCode);
        return m.matches();
    }

    //验证注册资本格式：6位整数和最多两位有效数字
    public static boolean isRegisterCapital(String registeredCapital) {
        Pattern p = Pattern.compile("^[1-9]{1,6}([.][0-9]{1,2})?$");
        Matcher m = p.matcher(registeredCapital);
        return m.matches();
    }

    //验证银行账户名称格式：中文字母数字组合
    public static boolean isAccountName(String accountName) {
        Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5a-zA-Z0-9]+$");
        Matcher m = p.matcher(accountName);
        return m.matches();
    }

    //验证银行账号格式：1-50位数字
    public static boolean isAccountNumber(String accountNumber) {
        Pattern p = Pattern.compile("^(\\d{1,50})$");
        Matcher m = p.matcher(accountNumber);
        return m.matches();
    }

    //验证是否含有中文
    public static boolean isChinese(String chinese) {
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
        Matcher m = p.matcher(chinese);
        return m.find();
    }

    //验证是否含有表情
    public static boolean isEnableEmoji(String str) {
        Pattern p = Pattern.compile("^[\\w\\u4e00-\\u9fa5\\u0000-\\u00FF\\uFF00-\\uFFFF。、“”……——【】《》]+$");
        Matcher m = p.matcher(str);
        return m.find();
    }

    //验证输入的是否是表情
    public static boolean isEmojiCharacter(char codePoint) {
        return !((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) ||
                ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }
}
