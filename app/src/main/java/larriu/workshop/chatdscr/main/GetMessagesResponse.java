package larriu.workshop.chatdscr.main;

import java.util.List;

import larriu.workshop.chatdscr.objects.Message;

class GetMessagesResponse {

    private int count;
    private List<Message> messages;

    public GetMessagesResponse(int count, List<Message> list) {
        this.count = count;
        this.messages = list;
    }

    public int getCount() {
        return count;
    }

    public List<Message> getList() {
        return messages;
    }
}
