package larriu.workshop.chatdscr.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Serializable {

    private int id, participants;
    private String created_at;
    private List<User> users;
    private List<Message> messageList;

    public Chat(int id, int participants, String created_at, List<User> users) {
        this.id = id;
        this.participants = participants;
        this.created_at = created_at;
        this.users = users;
        messageList = new ArrayList<Message>();
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

    public List<User> getUsers() {
        return users;
    }

    public User getOtherUser(String userName){
        User user1 = null;
        /*for (int i = 0; i < users.size(); i++){
            if (!users.get(i).getName().equals(userName)){
                user1 = users.get(i);
            }
        }*/
        if (users.size() == 2){
            for (int i = 0; i < users.size(); i++){
                if (!users.get(i).getName().equals(userName)){
                    user1 = users.get(i);
                }
            }
        }

        if (users.size() == 1){
            user1 = users.get(0);
        }

        return user1;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<Message> getMessageList() {
        return messageList;
    }


}
