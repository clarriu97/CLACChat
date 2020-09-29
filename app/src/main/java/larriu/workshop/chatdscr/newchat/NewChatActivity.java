package larriu.workshop.chatdscr.newchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;

public class NewChatActivity extends AppCompatActivity {

    private EditText friendMailEditText;
    private String friendMail, token;
    private Gson gson;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        friendMailEditText = findViewById(R.id.editTextFriendEmail);
        findViewById(R.id.newChatButtonNewChatActivity).setOnClickListener(new NewChatButtonListener());

        token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);

        gson = new Gson();

        model = ((DataBase)getApplication()).getModel();

    }

    private class NewChatButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            friendMail = friendMailEditText.getText().toString();
            NewChatRequest newChatRequest = new NewChatRequest(friendMail);
            String requestJson = gson.toJson(newChatRequest);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-AUTH-TOKEN", token);
            try {
                client.post(
                        NewChatActivity.this,
                        "https://api.messenger.tatai.es/v3/chat/invite",
                        new StringEntity(requestJson),
                        "application/json",
                        new NewChatResponseHandler(gson, NewChatActivity.this, model, NewChatActivity.this)
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(NewChatActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
