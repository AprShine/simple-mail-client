package core;

import ui.model.MailModel;

import javax.mail.Message;
import java.util.Map;

/**
 * Use:管理缓存数据
 * @author AprShine
 */
public class DataBuffer {
    /** 当前用户信息 */
    private static User currentUser;
    /** 收件箱内容 */
    public static Message [] inboxMail;

    public static Map<Long, MailModel> mailTableModel;
    public static void setCurrentUser(User currentUser) {
        DataBuffer.currentUser = currentUser;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}
