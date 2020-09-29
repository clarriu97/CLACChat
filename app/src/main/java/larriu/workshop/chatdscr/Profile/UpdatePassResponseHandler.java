package larriu.workshop.chatdscr.profile;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.main.ErrorResponse;

class UpdatePassResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private EditProfileActivity editProfileActivity;

    public UpdatePassResponseHandler(Gson gson, Context context, EditProfileActivity editProfileActivity) {
        this.gson = gson;
        this.context = context;
        this.editProfileActivity = editProfileActivity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            UpdatePassResponse updatePassResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), UpdatePassResponse.class);

            //String result = gson.toJson(signInResponse);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

            editProfileActivity.updatePass(updatePassResponse.getSession());

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
