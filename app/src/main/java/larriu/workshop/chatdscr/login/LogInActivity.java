package larriu.workshop.chatdscr.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.signin.SignInActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText emailView, passwordView;
    private String email, password;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailView = findViewById(R.id.editTextEmailLogInActivity);
        passwordView = findViewById(R.id.editTextPasswordLogInActivity);

        (findViewById(R.id.buttonLogInLogInActivity)).setOnClickListener(new LogInButtonListener());
        (findViewById(R.id.buttonRegisterLogInActivity)).setOnClickListener(new RegisterButtonListener());

    }

    private class LogInButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            email = emailView.getText().toString();
            password = passwordView.getText().toString();

            gson = new Gson();
            LogInRequest logInRequest = new LogInRequest(email, password);
            String requestJson = gson.toJson(logInRequest);
            AsyncHttpClient client = new AsyncHttpClient();
            try {
                client.post(
                        LogInActivity.this,
                        "https://api.messenger.tatai.es/v3/auth/login",
                        new StringEntity(requestJson),
                        "application/json",
                        new LogInResponseHandler(gson, LogInActivity.this, LogInActivity.this)
                );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RegisterButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    }
}
