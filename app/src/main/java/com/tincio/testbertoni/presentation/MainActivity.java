package com.tincio.testbertoni.presentation;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.tincio.testbertoni.R;
import com.tincio.testbertoni.data.AllTaskLoader;
import com.tincio.testbertoni.data.Task;
import com.tincio.testbertoni.data.TaskCursor;
import com.tincio.testbertoni.presentation.view.DialogCustomTask;

import java.util.List;

import static android.view.View.GONE;

public class MainActivity  extends AppCompatActivity implements MainView,
                                                    View.OnClickListener,
                                                    DialogCustomTask.Callback{

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MainPresenter presenter;
    protected TaskCursor mTasks;
    MainAdapter mAdapter;
    DialogCustomTask dialogNewTask;
    private static final int LOADER_STREAM = 2;
    String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.list);
        progressBar = findViewById(R.id.progress);
        findViewById(R.id.fab).setOnClickListener(this);
        presenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
        mAdapter = new MainAdapter( presenter::onItemClick);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
        startApp();
    }

    void startApp(){
        //addVisitedLocations();
        getSupportLoaderManager()
                .restartLoader(LOADER_STREAM, null, mLoaderCallbacks);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                showDialog();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        /*progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public void hideProgress() {
      /*  progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void setItems(List<Task> items) {
        addVisitedLocations();
        getSupportLoaderManager()
                .restartLoader(LOADER_STREAM, null, mLoaderCallbacks);
    }


    @Override
    public void showMessage(String message) {

    }



    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks
            = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new AllTaskLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            final int id = loader.getId();
            mTasks = new TaskCursor(cursor);
            addVisitedLocations();
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mTasks = null;
        }
    };


    protected void addVisitedLocations() {
        mAdapter.cleanItems();
        while (mTasks.hasNext()) {
            Task Task = mTasks.getCurrent();
            mAdapter.addTask(Task);
            mTasks.moveToNext();
        }

            recyclerView.setVisibility(mAdapter.getItems()>0 ? View.VISIBLE : View.GONE);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refresh() {

        startApp();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    /*TODO methods of BottomSheet*/

    void showDialog(){
        try{
            dialogNewTask = DialogCustomTask.getInstance(this);
            dialogNewTask.show(getSupportFragmentManager(),"");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onNewTask(String item) {
        dialogNewTask.dismiss();
        presenter.insertItem(Task.newInstance(item, "0"));
    }

}
