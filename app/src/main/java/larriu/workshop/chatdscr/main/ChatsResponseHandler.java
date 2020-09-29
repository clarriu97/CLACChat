package larriu.workshop.chatdscr.main;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import larriu.workshop.chatdscr.objects.Chat;
import larriu.workshop.chatdscr.objects.Model;

class ChatsResponseHandler extends AsyncHttpResponseHandler {

    private Gson gson;
    private Context context;
    private ChatsAdapter chatsAdapter;
    private MainScreenActivity mainScreenActivity;
    private Model model;

    public ChatsResponseHandler(Gson gson, Context context, ChatsAdapter chatsAdapter, MainScreenActivity mainScreenActivity, Model model) {
        this.gson = gson;
        this.context = context;
        this.chatsAdapter = chatsAdapter;
        this.mainScreenActivity = mainScreenActivity;
        this.model = model;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        if (statusCode == 200){
            ChatsResponse chatsResponse = this.gson.fromJson(new String(responseBody, StandardCharsets.UTF_8), ChatsResponse.class);
            String result = gson.toJson(chatsResponse);
            //Toast.makeText(this.context, result, Toast.LENGTH_LONG).show();

            if (chatsResponse.getChats() != null){
                model.setList(chatsResponse.getChats());
            } else {
                model.setList(new ArrayList<Chat>());
            }

            chatsAdapter.notifyDataSetChanged();
            mainScreenActivity.makeMessagesPetition(this.model);
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
