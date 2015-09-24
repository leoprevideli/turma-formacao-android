package br.com.cast.turmaformacao.taskmanager.controllers.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.controllers.adapters.TaskListAdapter;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.http.TaskService;
import br.com.cast.turmaformacao.taskmanager.model.services.TaskBusinessService;

public class TaskListActivity extends AppCompatActivity {

    private ListView listViewTaskList;
    private Task selectedTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        bindTaskList();
    }

    private void bindTaskList() {
        listViewTaskList = (ListView) findViewById(R.id.listViewTaskList);
        registerForContextMenu(listViewTaskList);
        listViewTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TaskListAdapter adapter = (TaskListAdapter) listViewTaskList.getAdapter();
                selectedTask = adapter.getItem(position);
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        updateTaskList();
        super.onResume();
    }

    private void updateTaskList() {
        TaskListAdapter adapter = (TaskListAdapter) listViewTaskList.getAdapter();
        if (adapter == null) {
            adapter = new TaskListAdapter(this);
            listViewTaskList.setAdapter(adapter);
        }
        adapter.setDataValues(TaskBusinessService.findAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                onMenuAddClick();
                break;
            case R.id.menu_refresh:
                onMenuRefresh();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_task_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                onMenuDeleteClick();
                break;
            case R.id.menu_edit:
                onMenuEditClick();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void onMenuEditClick() {
        Intent goToTaskForm = new Intent(TaskListActivity.this, TaskFormActivity.class);
        goToTaskForm.putExtra(TaskFormActivity.PARAM_TASK, selectedTask);
        startActivity(goToTaskForm);
    }

    private void onMenuDeleteClick() {
        new AlertDialog.Builder(TaskListActivity.this)
                .setTitle(R.string.lbl_confirm)
                .setMessage(R.string.msg_confirm_delete)
                .setPositiveButton(R.string.lbl_yes,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TaskBusinessService.delete(selectedTask);
                                selectedTask = null;
                                String message = getString(R.string.msg_delete_success);
                                updateTaskList();
                                Toast.makeText(TaskListActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

    private void onMenuAddClick() {
        Intent goToTaskFormActivity = new Intent(TaskListActivity.this, TaskFormActivity.class);
        startActivity(goToTaskFormActivity);
    }

    private void onMenuRefresh() {
        new GetTasksOnWeb().execute();
    }

    private class GetTasksOnWeb extends AsyncTask<Void, Void, List<Task>> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(TaskListActivity.this);
            progressDialog.setMessage("Searching tasks...");
            progressDialog.show();
        }

        @Override
        protected List<Task> doInBackground(Void... params) {
            return TaskService.getAllTasks();
        }

        @Override
        protected void onPostExecute(List<Task> tasks) {
            saveWebTasks(tasks);
            updateTaskList();
            progressDialog.dismiss();
        }
    }

    private void saveWebTasks(List<Task> tasks){
        for(Task task : tasks){
            long verifyTaskId = TaskBusinessService.getTaskByWebId(task.getWebId());
            if(verifyTaskId > 0) { //update
                task.set_id(verifyTaskId);
                TaskBusinessService.save(task);
            }
            else { //save
                TaskBusinessService.save(task);
            }
        }
    }
}
