package com.tincio.testbertoni.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tincio.testbertoni.R;

/**
 * Created by tania on 9/19/18.
 */

public class DialogCustomTask extends DialogFragment {

    static Callback callback;

    public static DialogCustomTask getInstance(Callback callbackView){
        callback = callbackView;
        DialogCustomTask dialog = new DialogCustomTask();

        return dialog;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.dialog_custom_dialog, container, false);
        EditText edt = (EditText) view.findViewById(R.id.edtTask);
        view.findViewById(R.id.txt_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onNewTask(edt.getText().toString());
            }
        });

        return view;
    }

    public interface Callback {

        void onNewTask(String item);
    }

}
