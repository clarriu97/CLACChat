package larriu.workshop.chatdscr.signin;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.victor.loading.rotate.RotateLoading;

import larriu.workshop.chatdscr.main.ErrorResponse;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

class SignInResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private SignInActivity signInActivity;
    private RotateLoading rotateLoading;

    public SignInResponseHandler(Gson gson, Context context, SignInActivity signInActivity, RotateLoading rotateLoading) {
        this.gson = gson;
        this.context = context;
        this.signInActivity = signInActivity;
        this.rotateLoading = rotateLoading;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        if (rotateLoading.isStart()){
            rotateLoading.stop();
        }

        if (statusCode == 200){
            SignInResponse signInResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), SignInResponse.class);

            //String result = gson.toJson(signInResponse);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();
            signInActivity.startMainScreenActivity(signInResponse.getSession(), signInResponse.getUser());

        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        if (rotateLoading.isStart()){
            rotateLoading.stop();
        }

        if (statusCode == 400){

            ErrorResponse errorResponse = this.gson.fromJson(
                    new String(responseBody, StandardCharsets.UTF_8),
                    ErrorResponse.class
            );

            Toast.makeText(this.context, errorResponse.getDescription(), Toast.LENGTH_LONG).show();

        } else if (statusCode == 409){
            Toast.makeText(this.context, "El usuario ya existe", Toast.LENGTH_LONG).show();
        } else {

            if (error != null){
                Toast.makeText(this.context, "Recibido statusCode " + statusCode + " con error " + error.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.context, "Recibido statusCode" + statusCode, Toast.LENGTH_LONG).show();
            }

        }
    }
}
