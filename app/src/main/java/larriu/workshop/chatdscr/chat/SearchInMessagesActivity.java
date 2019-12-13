package larriu.workshop.chatdscr.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Message;
import larriu.workshop.chatdscr.objects.Model;

public class SearchInMessagesActivity extends AppCompatActivity {

    private EditText searchText;
    private Model model;
    private int chatNumber;
    private SearchMessagesAdapter searchMessagesAdapter;
    private ListView listView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_in_messages);

        model = ((DataBase) getApplication()).getModel();
        chatNumber = (int) getIntent().getExtras().getBundle("bundle").getInt("chatNumber");
        linearLayout = findViewById(R.id.linearLayout);
        findViewById(R.id.searchMessageButton).setOnClickListener(new SearchInMessagesListener());
        searchText = findViewById(R.id.editTextSearchMessage);
        listView = findViewById(R.id.resultSearch);
        listView.setDivider(null);
        listView.setDividerHeight(5);
    }

    private class SearchInMessagesListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            String search = searchText.getText().toString();
            ArrayList<Message> coincidences = new ArrayList<>();
            for (Message m : model.getList().get(chatNumber).getMessageList()) {
                if (m.getText().contains(search)){
                    coincidences.add(m);
                }
            }
            if (coincidences.size() > 0){
                searchMessagesAdapter = new SearchMessagesAdapter(coincidences, SearchInMessagesActivity.this);
                listView.setAdapter(searchMessagesAdapter);
            } else {
                Toast.makeText(SearchInMessagesActivity.this, "No hay coincidencias", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
