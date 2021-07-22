package model.game.sharedRecourses;
import view.CRView;

public class View {

    private CRView view;
    private static View viewInstance = null;

    /**
     * constructor of the class is private and is used to initialize View of the game, is private to guarantee ter can be only one at a time
     * @param crView
     */
    private View(CRView crView){
        view = crView;
    }

    /**
     * initialize view
     * @param crView is used to set the game view
     */
    public static void initView(CRView crView){
        if(viewInstance == null){
            viewInstance = new View(crView);
        }
    }

    /**
     *
     * @return the CRView
     */
    public static CRView CRView(){
        return viewInstance.view;
    }

    /**
     *
     * @param viewInstance is the view instance to be set
     */
    public static void setViewInstance(View viewInstance) {
        View.viewInstance = viewInstance;
    }

    /**
     *
     * @return view instance
     */
    public static View getViewInstance() {
        return viewInstance;
    }
}
