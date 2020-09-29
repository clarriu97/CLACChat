package larriu.workshop.chatdscr.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.chat.ChatActivity;
import larriu.workshop.chatdscr.chat.SearchMessagesAdapter;
import larriu.workshop.chatdscr.objects.Chat;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;

public class SearchChatActivity extends AppCompatActivity {

    private EditText searchText;
    private Model model;
    private SearchChatAdapter searchChatAdapter;
    private ListView listView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat);

        model = ((DataBase) getApplication()).getModel();

        searchText = findViewById(R.id.editTextSearchChat);
        listView = findViewById(R.id.resultChatSearch);
        listView.setOnItemClickListener(new ChatsListViewListener(this));

        findViewById(R.id.searchChatButton).setOnClickListener(new SearchChatListener());
        linearLayout = findViewById(R.id.linearLayoutChat);
    }

    private class SearchChatListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            String search = searchText.getText().toString();
            ArrayList<Chat> coincidences = new ArrayList<>();
            for (Chat c : model.getList()) {
                if (c.getOtherUser(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null)).getName().contains(search)){
                    coincidences.add(c);
                }
            }
            if (coincidences.size() > 0){
                searchChatAdapter = new SearchChatAdapter(coincidences, SearchChatActivity.this);
                listView.setAdapter(searchChatAdapter);
            } else {
                Toast.makeText(SearchChatActivity.this, "No hay coincidencias", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ChatsListViewListener implements AdapterView.OnItemClickListener {

        Activity activity;

        public ChatsListViewListener(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int chatId = (int)view.getTag();
            int rightPosition = 0;
            for (int i = 0; i<model.getList().size(); i++){
                if (model.getList().get(i).getId() == chatId){
                    rightPosition = i;
                }
            }
            Bundle bundle = new Bundle();
            bundle.putInt("chatNumber", rightPosition);
            Intent intent = new Intent(activity, ChatActivity.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
            finish();
        }
    }
}
