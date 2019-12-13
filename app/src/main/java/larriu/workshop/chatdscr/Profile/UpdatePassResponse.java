package larriu.workshop.chatdscr.profile;

import larriu.workshop.chatdscr.objects.Session;
import larriu.workshop.chatdscr.objects.User;

public class UpdatePassResponse {

    private User user;
    private Session session;

    public UpdatePassResponse(User user, Session session) {
        this.user = user;
        this.session = session;
    }

    public User getUser() {
        return user;
    }

    public Session getSession() {
        return session;
    }
}
