package larriu.workshop.chatdscr.login;

import com.google.gson.annotations.SerializedName;

class LogInRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    public LogInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail(){return email;}

    public String getPassword(){return password;}
}
