package larriu.workshop.chatdscr.chat;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.nio.charset.StandardCharsets;
import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.main.ErrorResponse;
import larriu.workshop.chatdscr.objects.Model;

public class ExitFromChatResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Model model;
    private Context context;
    private ChatActivity chatActivity;
    private int chatNumber;

    public ExitFromChatResponseHandler(Gson gson, Model model, Context context, ChatActivity chatActivity, int chatNumber) {
        this.gson = gson;
        this.model = model;
        this.context = context;
        this.chatActivity = chatActivity;
        this.chatNumber = chatNumber;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){

            model.getList().remove(chatNumber);
            chatActivity.finish();

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
