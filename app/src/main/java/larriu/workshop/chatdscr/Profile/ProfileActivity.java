package larriu.workshop.chatdscr.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.chat.ChatActivity;
import larriu.workshop.chatdscr.chat.ExitFromChatResponseHandler;
import larriu.workshop.chatdscr.objects.DataBase;
import larriu.workshop.chatdscr.objects.Model;

public class ProfileActivity extends AppCompatActivity {

    private TextView headerName, name, email;
    private String token;
    private Gson gson;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        headerName = findViewById(R.id.nameHeaderProfileActivity);
        headerName.setText(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null));
        name = findViewById(R.id.textViewSetNombreProfileActivity);
        name.setText(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null));
        email = findViewById(R.id.textViewSetEmailProfileActivity);
        email.setText(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("email", null));

        findViewById(R.id.editProfileImage).setOnClickListener(new EditProfileListener());
        findViewById(R.id.eliminarCuentaButton).setOnClickListener(new EliminarCuentaListener());
        findViewById(R.id.logoutButton).setOnClickListener(new LogoutButtonListener());

        token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);
        gson = new Gson();
        model = ((DataBase) getApplication()).getModel();
    }

    @Override
    protected  void onResume(){
        super.onResume();
        headerName.setText(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null));
        name.setText(getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("userName", null));
    }

    private class EditProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        }
    }

    private class EliminarCuentaListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    private class LogoutButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("X-AUTH-TOKEN", token);
            client.get(
                    ProfileActivity.this,
                    "https://api.messenger.tatai.es/v3/auth/logout",
                    new LogoutResponseHandler(gson, model, ProfileActivity.this, ProfileActivity.this)
            );
        }
    }
}
