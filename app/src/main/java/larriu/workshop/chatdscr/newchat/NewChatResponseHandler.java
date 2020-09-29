package larriu.workshop.chatdscr.newchat;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import larriu.workshop.chatdscr.objects.Message;
import larriu.workshop.chatdscr.objects.Model;
import larriu.workshop.chatdscr.main.ErrorResponse;
import cz.msebera.android.httpclient.Header;

class NewChatResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private Model model;
    private NewChatActivity newChatActivity;

    public NewChatResponseHandler(Gson gson, Context context, Model model, NewChatActivity newChatActivity) {
        this.gson = gson;
        this.context = context;
        this.model = model;
        this.newChatActivity = newChatActivity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            NewChatResponse newChatResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), NewChatResponse.class);

            //String result = gson.toJson(newChatActivity);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

            newChatResponse.getChat().setMessageList(new ArrayList<Message>());
            model.addChat(newChatResponse.getChat());
            newChatActivity.finish();

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
