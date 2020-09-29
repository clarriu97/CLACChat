package larriu.workshop.chatdscr.profile;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.nio.charset.StandardCharsets;
import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.login.LogInActivity;
import larriu.workshop.chatdscr.main.ErrorResponse;
import larriu.workshop.chatdscr.objects.Model;

class LogoutResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private  Model model;
    private Context context;
    private ProfileActivity profileActivity;

    public LogoutResponseHandler(Gson gson, Model model, Context context, ProfileActivity profileActivity) {
        this.gson = gson;
        this.model = model;
        this.context = context;
        this.profileActivity = profileActivity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            profileActivity.getSharedPreferences("credenciales", Context.MODE_PRIVATE).edit().clear().commit();
            Intent intent = new Intent(context, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            profileActivity.startActivity(intent);
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
