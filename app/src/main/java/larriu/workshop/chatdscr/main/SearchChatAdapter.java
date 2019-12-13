package larriu.workshop.chatdscr.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.Chat;

public class SearchChatAdapter extends BaseAdapter {

    private ArrayList<Chat> coincidences;
    private Activity activity;
    private LayoutInflater inflater;

    public SearchChatAdapter(ArrayList<Chat> list, Activity activity) {
        this.coincidences = list;
        this.activity = activity;
        inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return coincidences.size();
    }

    @Override
    public Object getItem(int position) {
        return coincidences.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Chat chat = coincidences.get(position);
        view = inflater.inflate(R.layout.chat_item, parent, false);
        view.setTag(chat.getId());
        TextView textView = view.findViewById(R.id.chatNameView);
        textView.setText(chat.getOtherUser(activity.getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null)).getName());

        return view;
    }
}
