package larriu.workshop.chatdscr.Objects;

public class Session {

     private String token, valid_until;

    public Session(String token, String valid_until) {
        this.token = token;
        this.valid_until = valid_until;
    }

    public String getToken() {
        return token;
    }

    public String getValid_until() {
        return valid_until;
    }
}
