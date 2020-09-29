package larriu.workshop.chatdscr.chat;

import java.util.List;

import larriu.workshop.chatdscr.objects.Message;

class SendMessagesResponse {

    private List<Message> messages;

    public SendMessagesResponse(List<Message> list) {
        this.messages = list;
    }

    public List<Message> getList() {
        return messages;
    }
}
