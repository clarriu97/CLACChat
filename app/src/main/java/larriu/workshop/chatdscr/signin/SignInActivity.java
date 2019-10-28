package larriu.workshop.chatdscr.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.R;

public class SignInActivity extends AppCompatActivity {

    private EditText emailView, passwordView, nameView;
    private String email, password, name;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailView = findViewById(R.id.editTextEmailSignInActivity);
        passwordView = findViewById(R.id.editTextPasswordSignInActivity);
        nameView = findViewById(R.id.editTextNombreSignInActivity);

        findViewById(R.id.buttonRegisterSignInActivity).setOnClickListener(new RegisterSignInListener());

    }


    private class RegisterSignInListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            email = emailView.getText().toString();
            password = passwordView.getText().toString();
            name = nameView.getText().toString();

            gson = new Gson();
            SignInRequest signInRequest = new SignInRequest(email, password, name);
            String requestJson = gson.toJson(signInRequest);
            AsyncHttpClient client = new AsyncHttpClient();
            try {
                client.post(
                        SignInActivity.this,
                        "https://api.messenger.tatai.es/v3/auth/register",
                        new StringEntity(requestJson),
                        "application/json",
                        new SignInResponseHandler(gson, SignInActivity.this, SignInActivity.this)
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
