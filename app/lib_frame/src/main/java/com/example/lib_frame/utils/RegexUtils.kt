package com.example.lib_frame.utils

import java.util.regex.Pattern

object RegexUtils {
    /**
     * 验证Email
     *
     * @param email email地址，格式：123456789@qq.com，xxxxxx@xxx.com.cn，
     * xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkEmail(email: String): Boolean {
        val regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?"
        return Pattern.matches(regex, email)
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkIdCard(idCard: String?): Boolean {
        val regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}"
        return Pattern.matches(regex, idCard)
    }

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param mobile 移动、联通、电信运营商的号码段
     *
     *
     * 移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     * 、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用） 177 170 166
     * 开头
     *
     *
     *
     * 联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）
     *
     *
     *
     * 电信的号段：133、153、180（未启用）、189
     *
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkMobile(mobile: String?): Boolean {
        val regex = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$"
        return Pattern.matches(regex, mobile)
    }

    /**
     * 验证固定电话号码
     *
     * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
     *
     *
     * **国家（地区） 代码 ：**标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9
     * 的一位或多位数字， 数字之后是空格分隔的国家（地区）代码。
     *
     *
     *
     * **区号（城市代码）：**这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
     * 对不使用地区或城市代码的国家（地区），则省略该组件。
     *
     *
     *
     * **电话号码：**这包含从 0 到 9 的一个或多个数字
     *
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkPhone(phone: String?): Boolean {
        val regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"
        return Pattern.matches(regex, phone)
    }

    /**
     * 验证整数（正整数和负整数）
     *
     * @param digit 一位或多位0-9之间的整数
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkDigit(digit: String?): Boolean {
        val regex = "\\-?[1-9]\\d+"
        return Pattern.matches(regex, digit)
    }

    /**
     * 验证整数和浮点数（正负整数和正负浮点数）
     *
     * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkDecimals(decimals: String?): Boolean {
        val regex = "\\-?[1-9]\\d+(\\.\\d+)?"
        return Pattern.matches(regex, decimals)
    }

    /**
     * 验证空白字符
     *
     * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkBlankSpace(blankSpace: String?): Boolean {
        val regex = "\\s+"
        return Pattern.matches(regex, blankSpace)
    }

    /**
     * 验证中文
     *
     * @param chinese 中文字符
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkChinese(chinese: String?): Boolean {
        val regex = "^[\u4E00-\u9FA5]+$"
        return Pattern.matches(regex, chinese)
    }

    /**
     * 验证日期（年月日）
     *
     * @param birthday_ 日期，格式：1992-09-03，或1992.09.03
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkBirthday(birthday_: String?): Boolean {
        val regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}"
        return Pattern.matches(regex, birthday_)
    }

    /**
     * 验证URL地址
     *
     * @param url 格式：http://blog.csdn.net:80/xyang81/article/details/7705960? 或
     * http://www.csdn.net:80
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkURL(url: String?): Boolean {
        val regex =
            "((https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?)|(.*\\.html$)"
        return Pattern.matches(regex, url)
    }

    /**
     * <pre>
     * 获取网址 URL 的一级域
    </pre> *
     */
    fun getDomain(url: String?): String {
        val p = Pattern.compile(
            "(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",
            Pattern.CASE_INSENSITIVE
        )
        // 获取完整的域名
        // Pattern
        // p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",
        // Pattern.CASE_INSENSITIVE);
        val matcher = p.matcher(url)
        matcher.find()
        return matcher.group()
    }

    /**
     * 匹配中国邮政编码
     *
     * @param postcode 邮政编码
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkPostcode(postcode: String?): Boolean {
        val regex = "[1-9]\\d{5}"
        return Pattern.matches(regex, postcode)
    }

    /**
     * 匹配IP地址(简单匹配，格式，如：192.168.1.101，127.0.0.1，没有匹配IP段的大小)
     *
     * @param ipAddress IPv4标准地址
     * @return 验证成功返回true，验证失败返回false
     */
    fun checkIpAddress(ipAddress: String?): Boolean {
        val regex =
            "^((http|https)://)((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(:([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$)"
        return Pattern.matches(regex, ipAddress)
    }
}
