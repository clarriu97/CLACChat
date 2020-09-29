package larriu.workshop.chatdscr.main;

import java.util.List;

import larriu.workshop.chatdscr.objects.Chat;

class ChatsResponse {

    private int counts;
    private List<Chat> chats;

    public ChatsResponse(int counts, List<Chat> chats) {
        this.counts = counts;
        this.chats = chats;
    }

    public int getCounts() {
        return counts;
    }

    public List<Chat> getChats() {
        return chats;
    }
}
