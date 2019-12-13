package larriu.workshop.chatdscr.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;

public class ChatInfoActivity extends AppCompatActivity {

    private TextView createdAt, chatParticipants;
    private Model model;
    private int chatNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_info);

        model = ((DataBase) getApplication()).getModel();
        chatNumber = (int) getIntent().getExtras().getBundle("bundle").getInt("chatNumber");
        createdAt = findViewById(R.id.textViewCreatedAt);
        createdAt.setText(model.getList().get(chatNumber).getCreated_at());
        chatParticipants = findViewById(R.id.textViewChatParticipants);

        if (model.getList().get(chatNumber).getParticipants() == 2){
            chatParticipants.setText(model.getList().get(chatNumber).getUsers().get(0).getName() + " y " + model.getList().get(chatNumber).getUsers().get(1).getName());
        }

        if (model.getList().get(chatNumber).getParticipants() == 1){
            chatParticipants.setText(model.getList().get(chatNumber).getUsers().get(0).getName());
        }
    }
}
