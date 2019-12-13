package larriu.workshop.chatdscr.profile;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.nio.charset.StandardCharsets;
import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.main.ErrorResponse;

public class UpdateProfileResponseHandler extends AsyncHttpResponseHandler {

    private Context context;
    private Gson gson;
    private EditProfileActivity editProfileActivity;

    public UpdateProfileResponseHandler(Gson gson, Context context, EditProfileActivity editProfileActivity1) {
        this.context = context;
        this.gson = gson;
        this.editProfileActivity = editProfileActivity1;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            UpdateProfileResponse updateProfileResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), UpdateProfileResponse.class);

            //String result = gson.toJson(signInResponse);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

            editProfileActivity.updateName(updateProfileResponse.getUser().getName());
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
