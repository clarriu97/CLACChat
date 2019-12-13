package larriu.workshop.chatdscr.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.victor.loading.rotate.RotateLoading;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.objects.Session;
import larriu.workshop.chatdscr.objects.User;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.main.MainScreenActivity;
import larriu.workshop.chatdscr.signin.SignInActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText emailView, passwordView;
    private String email, password;
    private ConstraintLayout constraintLayout;
    private Gson gson;
    private RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        Date date_expiration = null;
        Date current_date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        if (getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("valid_until", null) != null){
            String fechaExpiracion = getSharedPreferences("credenciales", Context.MODE_PRIVATE).getString("valid_until", null);
            try {
                date_expiration = sdf.parse(fechaExpiracion);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date_expiration.after(current_date)){
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                finish();
            }
        }

        emailView = findViewById(R.id.editTextEmailLogInActivity);
        passwordView = findViewById(R.id.editTextPasswordLogInActivity);

        (findViewById(R.id.buttonLogInLogInActivity)).setOnClickListener(new LogInButtonListener());
        (findViewById(R.id.buttonRegisterLogInActivity)).setOnClickListener(new RegisterButtonListener());
        constraintLayout = findViewById(R.id.loginActivity);

        rotateLoading = findViewById(R.id.rotateloading);

    }

    private class LogInButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if (emailView.getText().toString().isEmpty() || passwordView.getText().toString().isEmpty()){
                Toast.makeText(LogInActivity.this, "Introduce la información requerida", Toast.LENGTH_SHORT).show();
            } else {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
                if (!rotateLoading.isStart()){
                    rotateLoading.start();
                }

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
                            new LogInResponseHandler(gson, LogInActivity.this, LogInActivity.this, rotateLoading)
                    );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class RegisterButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void startMainActivity(Session session, User user) {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", session.getToken());
        editor.putString("valid_until", session.getValid_until());
        editor.putString("userName", user.getName());
        editor.putString("email", user.getEmail());
        editor.commit();
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
