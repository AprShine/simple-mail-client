package core;

import ui.model.MailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Use:管理缓存数据
 * @author AprShine
 */
public class DataBuffer {
    /** 当前用户信息 */
    private static User currentUser;
    /** mailModel表格模型List */
    public final static List<MailModel> mailTableModel;
    //初始化操作
    static {
        mailTableModel=new ArrayList<>();
    }
    public static void setCurrentUser(User currentUser) {
        DataBuffer.currentUser = currentUser;
    }
    public static User getCurrentUser() {
        return currentUser;
    }
}
