package util;

import java.util.regex.Pattern;

/** Use:邮箱工具类
 * @author AprShine
 */
public class MailUtil {
    /** 验证邮箱的正则表达式 */
    public static final Pattern mailPattern=Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,})$");
    /** 安全传输设置 */
    //使用安全套接字协议进行传输,全程加密
    public static final String secureConfig="ssl";
    /** POP3服务器域名 */
    public static final String POP3Domain="pop.163.com";
    /** POP3服务器安全端口 */
    public static final String POP3SSLPort="995";
    /** SMTP服务器域名 */
    public static final String SMTPDomain="smtp.163.com";
    /** SMTP服务器端口号 */
    public static final String SMTPPort="994";
}
