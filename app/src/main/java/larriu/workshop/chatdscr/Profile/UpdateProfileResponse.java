package larriu.workshop.chatdscr.profile;

import larriu.workshop.chatdscr.objects.User;

public class UpdateProfileResponse {
    private  User user;

    public UpdateProfileResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
