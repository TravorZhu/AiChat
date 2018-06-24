package top.travorzhu.aichat.ChatListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import top.travorzhu.aichat.R;

public class ChatListViewAdapter extends BaseAdapter {
    private List<ChatListViewBean> messagelist;
    private LayoutInflater inflater;

    public ChatListViewAdapter(Context context, List<ChatListViewBean> messagelist) {
        this.messagelist = messagelist;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return messagelist.size();
    }

    @Override
    public Object getItem(int position) {
        return messagelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return messagelist.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = inflater.inflate(R.layout.chat_item_out, parent, false);
                holder.message = convertView.findViewById(R.id.text_out);
                holder.icon = convertView.findViewById(R.id.iv_icon1);

            } else {
                convertView = inflater.inflate(R.layout.chat_item_in, parent, false);
                holder.message = convertView.findViewById(R.id.text_in);
                holder.icon = convertView.findViewById(R.id.iv_icon);
            }

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.position = position;
        holder.message.setText(messagelist.get(position).getText());
        convertView.setTag(holder);
        return convertView;
    }

    public static class ViewHolder {
        int position;
        ImageView icon;
        TextView message;
    }
}
