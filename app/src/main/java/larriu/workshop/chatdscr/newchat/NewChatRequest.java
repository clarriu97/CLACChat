package larriu.workshop.chatdscr.newchat;

import com.google.gson.annotations.SerializedName;

class NewChatRequest {

    @SerializedName("email")
    private String email;

    public NewChatRequest(String friendMail) {
        email = friendMail;
    }

    public String getEmail() {
        return email;
    }
}
