package util;

public enum ConnectStatus {
    /** 成功链接状态 */
    CONNECTED_POP3,
    /** 服务器不存在 */
    NO_PROVIDER_FOR_POP3,
    /** 无法连接到服务器 */
    COULD_NOT_CONNECT
}
