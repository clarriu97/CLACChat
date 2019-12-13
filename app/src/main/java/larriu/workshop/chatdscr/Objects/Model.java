package larriu.workshop.chatdscr.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Model implements Serializable {

    private List<Chat> list;

    public Model(){
        list = new ArrayList<Chat>();
    }

    public int size(){
        return list.size();
    }

    public void setList(List<Chat> list){
        this.list = list;
    }

    public Chat getItem(int position) {
        return list.get(position);
    }

    public void addChat(Chat chat) {
        list.add(chat);
    }

    public List<Chat> getList() {
        return list;
    }

}
