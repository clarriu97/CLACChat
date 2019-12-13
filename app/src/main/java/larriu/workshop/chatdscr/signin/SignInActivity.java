package larriu.workshop.chatdscr.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.victor.loading.rotate.RotateLoading;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.entity.StringEntity;
import larriu.workshop.chatdscr.login.LogInActivity;
import larriu.workshop.chatdscr.objects.Session;
import larriu.workshop.chatdscr.objects.User;
import larriu.workshop.chatdscr.R;
import larriu.workshop.chatdscr.main.MainScreenActivity;

public class SignInActivity extends AppCompatActivity {

    private EditText emailView, passwordView, passwordView2, nameView;
    private String email, password, name;
    private RotateLoading rotateLoading;
    private Gson gson;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailView = findViewById(R.id.editTextEmailSignInActivity);
        passwordView = findViewById(R.id.editTextPasswordSignInActivity);
        passwordView2 = findViewById(R.id.editTextPassword2SignInActivity);
        nameView = findViewById(R.id.editTextNombreSignInActivity);
        constraintLayout = findViewById(R.id.constrainLayout2);

        findViewById(R.id.buttonRegisterSignInActivity).setOnClickListener(new RegisterSignInListener());
        findViewById(R.id.volverLoginActivityButton).setOnClickListener(new VolverLoginButtonListener());

        rotateLoading = findViewById(R.id.rotateloading);
    }


    private class RegisterSignInListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            if(emailView.getText().toString().isEmpty() || passwordView.getText().toString().isEmpty() || nameView.getText().toString().isEmpty() || passwordView2.getText().toString().isEmpty()){
                Toast.makeText(SignInActivity.this, "Introduce la información requerida", Toast.LENGTH_LONG).show();
            } if (!passwordView.getText().toString().equals(passwordView2.getText().toString())) {
                Toast.makeText(SignInActivity.this, "Las contraseñas deben coincidir", Toast.LENGTH_LONG).show();
            } else {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);
                if (!rotateLoading.isStart()){
                    rotateLoading.start();
                }

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
                            new SignInResponseHandler(gson, SignInActivity.this, SignInActivity.this, rotateLoading)
                    );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void startMainScreenActivity(Session session, User user){

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

    private class VolverLoginButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SignInActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
