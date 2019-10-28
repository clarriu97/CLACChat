package larriu.workshop.chatdscr.Objects;

public class Chat {

    private int id, participants;
    private String created_at;
    private User[] users;

    public Chat(int id, int participants, String created_at, User[] users) {
        this.id = id;
        this.participants = participants;
        this.created_at = created_at;
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public int getParticipants() {
        return participants;
    }

    public String getCreated_at() {
        return created_at;
    }

    public User[] getUsers() {
        return users;
    }
}
