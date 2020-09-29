package larriu.workshop.chatdscr.signin;

import larriu.workshop.chatdscr.objects.Session;
import larriu.workshop.chatdscr.objects.User;

class SignInResponse {

    private Session session;
    private User user;

    public SignInResponse(Session session, User user) {
        this.session = session;
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public User getUser() {
        return user;
    }

}
