package model.game.sharedRecourses;
import view.CRView;

public class View {

    private CRView view;
    private static View viewInstance = null;

    private View(CRView crView){
        view = crView;
    }

    public static void initView(CRView crView){
        if(viewInstance == null){
            viewInstance = new View(crView);
        }
    }

    public static CRView CRView(){
        return viewInstance.view;
    }

    public static void setViewInstance(View viewInstance) {
        View.viewInstance = viewInstance;
    }

    public static View getViewInstance() {
        return viewInstance;
    }
}
