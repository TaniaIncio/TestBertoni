package com.tincio.testbertoni.presentation;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tincio.testbertoni.R;
import com.tincio.testbertoni.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tania on 9/18/18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    public MainAdapter(Listener listener) {
       // this.items = items;
        this.listener = listener;
    }

    interface Listener {
        void onItemClick(Task item, Boolean willDelete);
    }

    private List<Task> items = new ArrayList<>();
    private Listener listener;

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_main_item, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        final Task item = items.get(position);
        holder.btn.setChecked(item.getStatus().equals("1")? true : false);
        holder.textView.setText(item.getDescription());
        holder.btn.setOnClickListener(v -> listener.onItemClick(item, false));
        holder.imgDelete.setOnClickListener(v -> listener.onItemClick(item, true));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox btn;
        ImageView imgDelete;

        MainViewHolder(LinearLayout linear) {
            super(linear);
            this.textView = linear.findViewById(R.id.lbl_text);
            this.btn = linear.findViewById(R.id.check);
            this.imgDelete= linear.findViewById(R.id.deleteItem);
        }
    }

    public void addTask(Task task) {
    /*    final int index = findCardIndexByTimestamp(card.timestamp);
        // Replace a old duplicated card if it exists.
        if (index < mCards.size() && mCards.get(index).equals(card)) {
            mCards.remove(index);
            mCards.add(index, card);
            notifyItemChanged(index);
        } else {*/
         /*   items.add(1, task);
            notifyItemInserted(1);*/
         items.add(task);
         notifyDataSetChanged();

    }

    public void cleanItems(){
        items.clear();
    }

    public int getItems(){
        return items.size();
    }
  //  }
}
