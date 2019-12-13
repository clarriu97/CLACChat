package larriu.workshop.chatdscr.chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.Chat;
import larriu.workshop.chatdscr.objects.Message;
import larriu.workshop.chatdscr.objects.Model;

public class MessagesAdapter extends BaseAdapter {

    private Model model;
    private LayoutInflater inflater;
    private Activity activity;
    private int chatNumber;
    private String mens;


    public MessagesAdapter(Activity activity, Model model, int chatNumber) {
        this.model = model;
        inflater = activity.getLayoutInflater();
        this.activity = activity;
        this.chatNumber = chatNumber;
    }

    @Override
    public int getCount() {
        return getMessagesList().size();
    }

    @Override
    public Object getItem(int position) {
        return getMessagesList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Message message = getMessagesList().get(position);
        if(message.getUser().getName().equals(activity.getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null))){
            view = inflater.inflate(R.layout.message_item_me, parent, false);
            TextView m = view.findViewById(R.id.textViewItemMe);
            m.setText(message.getText());
        } else {
            view = inflater.inflate(R.layout.message_item_other, parent, false);
            TextView m = view.findViewById(R.id.textViewItemOther);
            m.setText(message.getText());
        }

        return view;
    }

    public Chat getActualChat(){
        return model.getItem(chatNumber);
    }

    public List<Message> getMessagesList(){
        return getActualChat().getMessageList();
    }

    public void setMens(String mens) {
        this.mens = mens;
    }
}
