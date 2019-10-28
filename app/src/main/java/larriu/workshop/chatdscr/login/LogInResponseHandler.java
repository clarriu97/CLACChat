package larriu.workshop.chatdscr.login;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

class LogInResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private LogInActivity logInActivity;

    public LogInResponseHandler(Gson gson, Context context, LogInActivity logInActivity) {
        this.context = context;
        this.gson = gson;
        this.logInActivity = logInActivity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            LogInResponse logInResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), LogInResponse.class);

            String result = gson.toJson(logInResponse);
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
