package larriu.workshop.chatdscr.Objects;

public class Message {

    private int id;
    private String text, received_at;
    private Chat chat;
    private User user;

    public Message(int id, String text, String received_at, Chat chat, User user) {
        this.id = id;
        this.text = text;
        this.received_at = received_at;
        this.chat = chat;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getReceived_at() {
        return received_at;
    }

    public Chat getChat() {
        return chat;
    }

    public User getUser() {
        return user;
    }
}
