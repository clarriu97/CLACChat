package larriu.workshop.chatdscr.chat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.Message;

public class SearchMessagesAdapter extends BaseAdapter {

    private ArrayList<Message> coincidences;
    private LayoutInflater inflater;
    private Activity activity;

    public SearchMessagesAdapter(ArrayList<Message> coincidences, Activity activity) {
        this.coincidences = coincidences;
        inflater = activity.getLayoutInflater();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return coincidences.size();
    }

    @Override
    public Object getItem(int position) {
        return coincidences.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Message message = coincidences.get(position);
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
}
