package larriu.workshop.chatdscr.main;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import larriu.workshop.chatdscr.chat.ChatActivity;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.newchat.NewChatActivity;
import larriu.workshop.chatdscr.objects.Chat;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;
import larriu.workshop.chatdscr.profile.ProfileActivity;

public class MainScreenActivity extends AppCompatActivity {

    private String token, userName;
    private ListView listView;
    private Gson gson;
    private ChatsAdapter chatsAdapter;
    private Model model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);
        userName = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null);

        findViewById(R.id.profileButton).setOnClickListener(new ProfileButtonListener());
        findViewById(R.id.newChatButton).setOnClickListener(new NewChatButtonListener());
        findViewById(R.id.searchChatButton).setOnClickListener(new SearchChatListener());

        listView = findViewById(R.id.chatsListView);

        model = ((DataBase) getApplication()).getModel();
        chatsAdapter = new ChatsAdapter(this, userName, this, model);
        listView.setAdapter(chatsAdapter);
        listView.setOnItemClickListener(new ChatsListViewListener(this));

        gson = new Gson();
        makeChatsPetition();
    }


    @Override
    protected void onResume(){
        super.onResume();
        token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);
        userName = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null);
        //makeChatsPetition();
        chatsAdapter.notifyDataSetChanged();
    }

    private void makeChatsPetition(){

        //Get petition
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("X-AUTH-TOKEN", token);
        client.get(
                this,
                "https://api.messenger.tatai.es/v3/chat",
                new ChatsResponseHandler(gson,this, chatsAdapter, this, model)
        );
    }

    public void makeMessagesPetition(Model model) {
        for (Chat chat: model.getList()) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-AUTH-TOKEN", token);
            client.get(
                    this,
                    "https://api.messenger.tatai.es/v3/chat/" + chat.getId() + "/message",
                    new GetMessagesResponseHandler(gson,this, chat, chatsAdapter)
            );

        }



    }

    /*public void startChatActivity(String otherUserName, int chatNumber){
        Bundle bundle = new Bundle();
        bundle.putString("otherUserName", otherUserName);
        bundle.putInt("chatNumber", chatNumber);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("bundle", bundle);
        intent.putExtra("model", model);
        startActivity(intent);
    }*/

    private class ProfileButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainScreenActivity.this, ProfileActivity.class);
            startActivity(intent);
        }

    }

    private class NewChatButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainScreenActivity.this, NewChatActivity.class);
            startActivity(intent);
        }
    }

    private class ChatsListViewListener implements AdapterView.OnItemClickListener {

        Activity activity;

        public ChatsListViewListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putInt("chatNumber", position);
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    private class SearchChatListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainScreenActivity.this, SearchChatActivity.class);
            startActivity(intent);
        }
    }
}
