package larriu.workshop.chatdscr.objects;

import android.app.Application;

public class DataBase extends Application {

    private Model model;

    @Override
    public void onCreate(){
        super.onCreate();
        model = new Model();
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
