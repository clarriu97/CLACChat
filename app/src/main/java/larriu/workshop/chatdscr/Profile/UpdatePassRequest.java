package larriu.workshop.chatdscr.profile;

import com.google.gson.annotations.SerializedName;

public class UpdatePassRequest {

    @SerializedName("password")
    private String password;

    public UpdatePassRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
