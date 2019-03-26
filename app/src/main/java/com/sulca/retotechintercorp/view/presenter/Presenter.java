package com.sulca.retotechintercorp.view.presenter;

import com.sulca.retotechintercorp.view.interfacee.BaseView;

/**
 * Created by everis on 25/03/19.
 */
public interface Presenter <T extends BaseView> {

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onResume() method.
     */
    void resume();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onPause() method.
     */
    void pause();

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * (Activity or Fragment) onDestroy() method.
     */
    void destroy();

    /**
     * Setter of the view.
     */
    void setView(T view);
}
