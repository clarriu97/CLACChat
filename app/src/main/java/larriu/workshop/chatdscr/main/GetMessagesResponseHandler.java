package larriu.workshop.chatdscr.main;

import android.content.Context;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.objects.Chat;
import larriu.workshop.chatdscr.objects.Message;

public class GetMessagesResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private Chat chat;
    private ChatsAdapter chatsAdapter;

    public GetMessagesResponseHandler(Gson gson, Context context, Chat chat, ChatsAdapter chatsAdapter) {
        this.gson = gson;
        this.context = context;
        this.chat = chat;
        this.chatsAdapter = chatsAdapter;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            GetMessagesResponse getMessagesResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), GetMessagesResponse.class);

            //String result = gson.toJson(getMessagesResponse);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();
            if (getMessagesResponse.getList() != null){
                chat.setMessageList(getMessagesResponse.getList());
            } else chat.setMessageList(new ArrayList<Message>());

            chatsAdapter.notifyDataSetChanged();

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
