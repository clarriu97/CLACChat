package larriu.workshop.chatdscr.signin;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

class SignInResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private SignInActivity signInActivity;

    public SignInResponseHandler(Gson gson, Context context, SignInActivity signInActivity) {
        this.gson = gson;
        this.context = context;
        this.signInActivity = signInActivity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            SignInResponse singInRespone = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), SignInResponse.class);

            String result = gson.toJson(singInRespone);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        if (statusCode == 400){

            ErrorResponse errorResponse = this.gson.fromJson(
                    new String(responseBody, StandardCharsets.UTF_8),
                    ErrorResponse.class
            );

            Toast.makeText(this.context, errorResponse.getDescription(), Toast.LENGTH_LONG).show();

        } else {

            if (error != null){
                Toast.makeText(this.context, "Recibido statusCode " + statusCode + " con error " + error.getMessage(), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.context, "Recibido statusCode" + statusCode, Toast.LENGTH_LONG).show();
            }

        }
    }
}
