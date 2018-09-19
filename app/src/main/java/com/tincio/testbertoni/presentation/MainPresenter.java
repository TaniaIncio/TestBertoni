package com.tincio.testbertoni.presentation;

import com.tincio.testbertoni.data.Task;
import com.tincio.testbertoni.domain.ItemsInteractor;
import com.tincio.testbertoni.domain.MainCallback;

import java.util.List;

/**
 * Created by tania on 9/18/18.
 */

public class MainPresenter  implements MainCallback {
    private MainView mainView;
    private ItemsInteractor itemsInteractor;

    MainPresenter(MainView mainView) {
        this.mainView = mainView;
        this.itemsInteractor = new ItemsInteractor(mainView.getContext(), this);
    }

    void onResume() {
        if (mainView != null) {
        //    mainView.showProgress();
        }

      //  itemsInteractor.findItems(this::onFinished);
    }

    void onItemClick(Task item, Boolean willDelete) {
        if (willDelete){
            itemsInteractor.deleteItem(item);
        }else{
            itemsInteractor.updateItem(item);
        }
        if (mainView != null) {
            mainView.refresh();
        }
    }

    void onDestroy() {
        mainView = null;
    }

    public void onFinished(List<Task> items) {
        if (mainView != null) {
            mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    public MainView getMainView() {
        return mainView;
    }

    /***INSERT*/

    void insertItem(Task task){
        if(task.getDescription().isEmpty())return;
        itemsInteractor.insertItem(task);
    }

    @Override
    public void onResponse(Boolean result) {
        mainView.refresh();
    }
}

