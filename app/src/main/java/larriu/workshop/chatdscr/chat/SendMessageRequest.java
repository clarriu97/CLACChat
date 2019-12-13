package larriu.workshop.chatdscr.chat;

import com.google.gson.annotations.SerializedName;


class SendMessageRequest {

    @SerializedName("text")
    private String text;

    @SerializedName("created_at")
    private String date;

    public SendMessageRequest(String message, String date) {
        text = message;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
