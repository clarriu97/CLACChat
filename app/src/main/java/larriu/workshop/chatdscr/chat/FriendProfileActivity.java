package larriu.workshop.chatdscr.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;

public class FriendProfileActivity extends AppCompatActivity {

    private TextView friendName, friendEmail;
    private Model model;
    private int chatNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        model = ((DataBase) getApplication()).getModel();
        chatNumber = (int) getIntent().getExtras().getBundle("bundle").getInt("chatNumber");
        friendName = findViewById(R.id.friend_textViewSetNombreProfileActivity);
        friendName.setText(model.getList().get(chatNumber).getOtherUser(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null)).getName());
        friendEmail = findViewById(R.id.friend_textViewSetEmailProfileActivity);
        friendEmail.setText(model.getList().get(chatNumber).getOtherUser(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null)).getEmail());
    }
}
