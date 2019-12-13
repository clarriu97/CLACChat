package larriu.workshop.chatdscr.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.objects.Session;

public class EditProfileActivity extends AppCompatActivity {

    private EditText newNameEditText, newPass1EditText, newPass2EditText;
    private String newName, newPass1, newPass2, token;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        newNameEditText = findViewById(R.id.editTextEditProfileActivity);
        newPass1EditText = findViewById(R.id.editPass1);
        newPass2EditText = findViewById(R.id.editPass2);

        findViewById(R.id.editProfileButton).setOnClickListener(new EditProfileListener());
        findViewById(R.id.editPassButton).setOnClickListener(new EditPassListener());

        token = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("token", null);

    }

    public void updateName(String name) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userName", name);
        editor.commit();
        Toast.makeText(this, "¡Perfil actualizado!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void updatePass(Session session){
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", session.getToken());
        editor.putString("valid_until", session.getValid_until());
        editor.commit();
        Toast.makeText(this, "¡Perfil actualizado!", Toast.LENGTH_LONG).show();
        finish();
    }

    private class EditProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (newNameEditText.getText().toString().isEmpty()){
                Toast.makeText(EditProfileActivity.this, "Por favor introduce tu nuevo nombre", Toast.LENGTH_LONG).show();
            } else {
                newName = newNameEditText.getText().toString();
                gson = new Gson();
                UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(newName);
                String requestJson = gson.toJson(updateProfileRequest);
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("X-AUTH-TOKEN", token);
                try {
                    client.post(
                            EditProfileActivity.this,
                            "https://api.messenger.tatai.es/v3/profile/me",
                            new StringEntity(requestJson),
                            "application/json",
                            new UpdateProfileResponseHandler(gson, EditProfileActivity.this, EditProfileActivity.this)
                    );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class EditPassListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (newPass1EditText.getText().toString().isEmpty() || newPass2EditText.getText().toString().isEmpty()){
                Toast.makeText(EditProfileActivity.this, "Por favor introduce la nueva contraseña", Toast.LENGTH_LONG).show();
            } else {
                newPass1 = newPass1EditText.getText().toString();
                newPass2 = newPass2EditText.getText().toString();

                if (!newPass1.equals(newPass2)){
                    Toast.makeText(EditProfileActivity.this, "Las contraseñas tienen que coincidir", Toast.LENGTH_LONG).show();
                } else {
                    gson = new Gson();
                    UpdatePassRequest updatePassRequest = new UpdatePassRequest(newPass1);
                    String requestJson = gson.toJson(updatePassRequest);
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("X-AUTH-TOKEN", token);
                    try {
                        client.post(
                                EditProfileActivity.this,
                                "https://api.messenger.tatai.es/v3/profile/me/password",
                                new StringEntity(requestJson),
                                "application/json",
                                new UpdatePassResponseHandler(gson, EditProfileActivity.this, EditProfileActivity.this)
                        );
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }
}
