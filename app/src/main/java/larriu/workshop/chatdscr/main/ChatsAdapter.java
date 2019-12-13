package larriu.workshop.chatdscr.main;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import larriu.workshop.chatdscr.objects.Chat;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.Model;

public class ChatsAdapter extends BaseAdapter {

    private Model model;
    private LayoutInflater inflater;
    private String userName;
    private MainScreenActivity mainScreenActivity;

    public ChatsAdapter(Activity activity, String userName, MainScreenActivity mainScreenActivity, Model model) {
        this.model = model;
        inflater = activity.getLayoutInflater();
        this.userName = userName;
        this.mainScreenActivity = mainScreenActivity;
        notifyDataSetChanged();
    }

    public void setList(List<Chat> list) {
        model.setList(list);
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if(view == null){
            view = inflater.inflate(R.layout.chat_item, parent, false);
        }
        ((TextView)view.findViewById(R.id.chatNameView)).setText(model.getItem(position).getOtherUser(userName).getName());
        if (model.getItem(position).getMessageList() != null && model.getItem(position).getMessageList().size() > 0){
            ((TextView)view.findViewById(R.id.lastMessageTextView)).setText(model.getItem(position).getMessageList().get(model.getItem(position).getMessageList().size()-1).getText());
        }

        return view;

    }

    /*private class ChatListener implements View.OnClickListener, Serializable {

        @Override
        public void onClick(View v) {
            //El view que recibimos es Chat al que queremos acceder
            Chat chat = model.getItem((int)v.getTag());
            mainScreenActivity.startChatActivity(chat.getOtherUser(userName).getName(), (int)v.getTag());

        }
    }*/

}
