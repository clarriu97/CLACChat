package larriu.workshop.chatdscr.login;

import larriu.workshop.chatdscr.objects.Session;
import larriu.workshop.chatdscr.objects.User;

class LogInResponse {

    private Session session;
    private User user;

    public LogInResponse(Session session, User user) {
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
