package larriu.workshop.chatdscr.chat;

import android.content.Context;
import android.view.Display;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.main.ErrorResponse;
import larriu.workshop.chatdscr.objects.Message;
import larriu.workshop.chatdscr.objects.Model;

class SendMessageResponseHandler extends AsyncHttpResponseHandler {

    private Context context;
    private Gson gson;
    private MessagesAdapter messagesAdapter;
    private Model model;
    private int actualChat;

    public SendMessageResponseHandler(Gson gson, Context context, MessagesAdapter messagesAdapter, Model model, int actualChat) {
        this.gson = gson;
        this.context = context;
        this.messagesAdapter = messagesAdapter;
        this.model = model;
        this.actualChat = actualChat;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            SendMessagesResponse sendMessagesResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), SendMessagesResponse.class);

            //String result = gson.toJson(getMessagesResponse);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

            //model.getList().get(actualChat).getMessageList().add(sendMessagesResponse.getList().get(model.getList().get(actualChat).getMessageList().size()-1));

            if(sendMessagesResponse.getList() != null){
                model.getList().get(actualChat).getMessageList().add(sendMessagesResponse.getList().get(0));
            } else {
                model.getList().get(actualChat).setMessageList(new ArrayList<Message>());
            }

            messagesAdapter.notifyDataSetChanged();

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
