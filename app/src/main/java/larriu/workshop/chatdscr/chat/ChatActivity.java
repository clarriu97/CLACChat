package larriu.workshop.chatdscr.chat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;

public class ChatActivity extends AppCompatActivity {

    private int chatNumber;
    private String token, message;
    private Gson gson;
    private MessagesAdapter messagesAdapter;
    private EditText messageEditText;
    private ListView listView;
    private TextView friendNameTextView;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);
        messageEditText = findViewById(R.id.editTextChatActivity);
        listView = findViewById(R.id.messagesListView);
        friendNameTextView = findViewById(R.id.friendNameTextView);
        findViewById(R.id.sendMessageButton).setOnClickListener(new SendMessageButtonListener());
        findViewById(R.id.exitFromChatButton).setOnClickListener(new ExitFromChatButtonListener());
        findViewById(R.id.chatInfoButton).setOnClickListener(new ChatInfoButton());
        findViewById(R.id.searchInMessagesButton).setOnClickListener(new SearchInMessagesListener());

        chatNumber = (int) getIntent().getExtras().getBundle("bundle").getInt("chatNumber");
        model = ((DataBase) getApplication()).getModel();

        friendNameTextView.setText(model.getList().get(chatNumber).getOtherUser(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null)).getName());
        friendNameTextView.setOnClickListener(new FriendProfileListener());
        gson = new Gson();
        messagesAdapter = new MessagesAdapter(this, model, chatNumber);
        listView.setAdapter(messagesAdapter);
        listView.setDivider(null);
        listView.setDividerHeight(5);

    }

    private class SendMessageButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            messagesAdapter.setMens(messageEditText.getText().toString());
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            CharSequence dateTime = df.format("yyyy-MM-dd hh:mm:ss", new Date());
            message = messageEditText.getText().toString();
            messageEditText.setText("");
            SendMessageRequest sendMessageRequest = new SendMessageRequest(message, dateTime.toString());
            String requestJson = gson.toJson(sendMessageRequest);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-AUTH-TOKEN", token);
            try {
                client.post(
                        ChatActivity.this,
                        "https://api.messenger.tatai.es/v3/chat/" + model.getList().get(chatNumber).getId() + "/message",
                        new StringEntity(requestJson),
                        "application/json",
                        new SendMessageResponseHandler(gson, ChatActivity.this, messagesAdapter, model, chatNumber)
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ExitFromChatButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-AUTH-TOKEN", token);
            client.get(
                    ChatActivity.this,
                    "https://api.messenger.tatai.es/v3/chat/" + model.getList().get(chatNumber).getId() + "/exit",
                    new ExitFromChatResponseHandler(gson, model, ChatActivity.this, ChatActivity.this, chatNumber)
            );
        }
    }

    private class FriendProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("chatNumber", chatNumber);
            Intent intent = new Intent(ChatActivity.this, FriendProfileActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    private class ChatInfoButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("chatNumber", chatNumber);
            Intent intent = new Intent(ChatActivity.this, ChatInfoActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }

    private class SearchInMessagesListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putInt("chatNumber", chatNumber);
            Intent intent = new Intent(ChatActivity.this, SearchInMessagesActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }
}
