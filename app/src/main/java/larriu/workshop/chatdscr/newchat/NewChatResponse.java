package larriu.workshop.chatdscr.newchat;

import larriu.workshop.chatdscr.objects.Chat;

class NewChatResponse {

    private Chat chat;

    public NewChatResponse(Chat chat) {
        this.chat = chat;
    }

    public Chat getChat() {
        return chat;
    }
}
