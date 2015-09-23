package br.com.cast.turmaformacao.taskmanager.controllers.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.persistence.label.LabelRepository;
import br.com.cast.turmaformacao.taskmanager.model.persistence.task.TaskRepository;

public class TaskListAdapter extends BaseAdapter {

    private List<Task> taskList;
    private Activity context;

    public TaskListAdapter(Activity context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    public void setDataValues(List<Task> values) {
        taskList.clear();
        taskList.addAll(values);
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Task getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        View taskListItemView = context.getLayoutInflater().inflate(R.layout.list_item_task, parent, false);

        View viewId = (View) taskListItemView.findViewById(R.id.viewId);

        Label label = TaskRepository.getLabelByTaskId(task.getId());
        Label labelTeste = LabelRepository.getById(label.getId());

        int hexColor = android.graphics.Color.parseColor(labelTeste.getColor().getHex());
        viewId.setBackgroundColor(hexColor);

        TextView textViewName = (TextView) taskListItemView.findViewById(R.id.textViewName);
        textViewName.setText(task.getName());
        return taskListItemView;
    }


}
