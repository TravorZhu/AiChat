package top.travorzhu.aichat.ChatListView;

import android.graphics.Bitmap;

public class ChatListViewBean {
    private int type; //收到为1，发出为0
    private String text; //消息内容
    private Bitmap icon;


    public ChatListViewBean(int type, String text, Bitmap icon) {
        this.type = type;
        this.text = text;
        this.icon = icon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "ChatListViewBean{" +
                "type=" + type +
                ", text='" + text + '\'' +
                ", icon=" + icon +
                '}';
    }
}
