package core;

import ui.model.MailModel;
import util.ConnectStatus;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

/**
 * @author AprShine
 *
 */
public class RetrieveEmailsUsingPOP3 {

    /**
     *接受所有收件箱信件
     * @param host 服务器域名
     * @param port 服务器端口号
     * @param userName 用户名
     * @param password 密码
     * @param secureCon 安全协议
     * @throws IOException IO异常
     */
	public void getCompleteInbox(String host, String port, String userName, String password, String secureCon) throws IOException {
        Properties properties = new Properties();
 
    
        //---------- Server Setting---------------
        properties.put("mail.pop3.host", host);
        properties.put("mail.pop3.port", port);
        if(secureCon.equalsIgnoreCase("ssl")){
        	properties.put("mail.smtp.ssl.enable", "true");
        }else{
        	properties.put("mail.smtp.ssl.enable", "false");
        }
        //---------- SSL setting------------------
        properties.setProperty("mail.pop3.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.pop3.socketFactory.fallback", "false");
        properties.setProperty("mail.pop3.socketFactory.port", String.valueOf(port));
        Session session = Session.getDefaultInstance(properties);
        //----------------------------------------
      try {
            // connects to the message store
        	System.out.println("Connecting please wait....");
            Store store = session.getStore("pop3");
            store.connect(userName, password);
            System.out.println("Connected to mail via "+host);
            System.out.println();
            // opens the inbox folder
            System.out.println("Getting INBOX..");
            System.out.println();
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
 
            // fetches new messages from server
            Message[] inboxMail= folderInbox.getMessages();
            System.out.println("You have "+inboxMail.length+" mails in your INBOX");
            //对每封邮件进行遍历,得出TableModel
            for(Message message:inboxMail){
                String from=getFrom(message);
                String sendDate=new SimpleDateFormat("yyyy.MM.dd HH:mm")
                        .format(message.getSentDate());
                String subject=message.getSubject();
                String contentType = message.getContentType();
                String messageContent = "";
                StringBuilder attachFiles = new StringBuilder();
                List<MimeBodyPart> mimeBodyPartList=new ArrayList<>();
                if (contentType.contains("multipart")) {
                    // content may contain attachments
                    Multipart multiPart = (Multipart) message.getContent();
                    int numberOfParts = multiPart.getCount();
                    for (int partCount = 0; partCount < numberOfParts; partCount++) {
                        MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
                        if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            String fileName = MimeUtility.decodeText(part.getFileName());
                            attachFiles.append(fileName).append(", ");
                            mimeBodyPartList.add(part);
//                            part.saveFile("./attach" + File.separator + fileName); // hard code service file storage here
                            messageContent = getText(message);  // to get message body of attached emails
                        }

                        else {
                            // this part for the message content
                            messageContent = part.getContent().toString();
                        }
                    }
                    if (attachFiles.length() > 1) {
                        attachFiles = new StringBuilder(attachFiles.substring(0, attachFiles.length() - 2));
                    }
                } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    Object content = message.getContent();
                    if (content != null) {
                        messageContent = content.toString();
                    }
                }
                //添加到列表中
                MailModel mailModel= new MailModel(from,sendDate,subject,!attachFiles.toString().equals(""));
                //设置内容和附件
                mailModel.setContent(messageContent);
                mailModel.setAttachFile(mimeBodyPartList);
                DataBuffer.mailTableModel.add(mailModel);
            }
            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
	}

 /** 方法:检验是否能够连接上服务器
  * @return ConnectStatus
  * @param host 服务器主机名
  * @param port 服务器端口号
  * @param userName 用户名
  * @param password 授权码
  */
    public static ConnectStatus getConnectionStatus(String host, String port, String userName, String password){
        //链接属性集
    	  Properties properties = new Properties();
          //---------- Server Setting---------------
          properties.put("mail.pop3.host", host);
          properties.put("mail.pop3.port", port);
          properties.put("mail.smtp.ssl.enable", "true");
          //---------- SSL setting------------------
          properties.setProperty("mail.pop3.socketFactory.class",
                  "javax.net.ssl.SSLSocketFactory");
          properties.setProperty("mail.pop3.socketFactory.fallback", "false");
          properties.setProperty("mail.pop3.socketFactory.port",
                  String.valueOf(port));
          Session session = Session.getDefaultInstance(properties);
          //----------------------------------------
        ConnectStatus isConnected;
        try {
              // connects to the message store
          	System.out.println("Connecting please wait....");
              Store store = session.getStore("pop3");
              store.connect(userName, password);
              isConnected = ConnectStatus.CONNECTED_POP3;
              System.out.println("Is Connected: "+ isConnected);
              System.out.println("Connected to mail via "+host);
        }catch (NoSuchProviderException ex) {
        	isConnected=ConnectStatus.NO_PROVIDER_FOR_POP3;
            System.out.println(isConnected);
        } catch (MessagingException ex) {
        	isConnected = ConnectStatus.COULD_NOT_CONNECT;
            System.out.println(isConnected);
        }
		return isConnected;
    }
    
    
    /**
     *  This method is used to handle MIME message.
     *  a message with an attachment is represented in MIME as a multipart message. 
     *  In the simple case, the results of the Message object's getContent method will be a MimeMultipart object. 
     *  The first body part of the multipart object wil be the main text of the message. 
     *  The other body parts will be attachments. 
     * @param p
     *              Part
     * @return
     *              Text
     * @throws MessagingException
     *              消息异常
     * @throws IOException
     *              IO异常
     */
    
 	public static String getText(Part p) throws MessagingException, IOException {
 		if (p.isMimeType("text/*")) {
 			String s = (String) p.getContent();
 			return s;
 		}

 		if (p.isMimeType("multipart/alternative")) {
 			// prefer html text over plain text
 			Multipart mp = (Multipart) p.getContent();
 			String text = null;
 			for (int i = 0; i < mp.getCount(); i++) {
 				Part bp = mp.getBodyPart(i);
 				if (bp.isMimeType("text/plain")) {
 					if (text == null)
 						text = getText(bp);
 					continue;
 				} else if (bp.isMimeType("text/html")) {
 					String s = getText(bp);
 					if (s != null)
 						return s;
 				} else {
 					return getText(bp);
 				}
 			}
 			return text;
 		} else if (p.isMimeType("multipart/*")) {
 			Multipart mp = (Multipart) p.getContent();
 			for (int i = 0; i < mp.getCount(); i++) {
 				String s = getText(mp.getBodyPart(i));
 				if (s != null)
 					return s;
 			}
 		}

 return null;
 	}

    /**
     *
     * @param msg 信息
     * @return fromUser
     * @throws MessagingException 信息异常
     * @throws UnsupportedEncodingException 无法解码异常
     */
    public static String getFrom(Message msg) throws MessagingException, UnsupportedEncodingException {
        String from;
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");

        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

}