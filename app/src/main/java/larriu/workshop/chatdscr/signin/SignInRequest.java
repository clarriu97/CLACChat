package larriu.workshop.chatdscr.signin;

import com.google.gson.annotations.SerializedName;

class SignInRequest {

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    public SignInRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail(){return email;}

    public String getPassword(){return password;}

    public String getName(){return name;}

}
