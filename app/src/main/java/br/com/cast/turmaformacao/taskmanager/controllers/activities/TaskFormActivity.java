package br.com.cast.turmaformacao.taskmanager.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.controllers.adapters.LabelListAdapter;
import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.services.LabelBusinessService;
import br.com.cast.turmaformacao.taskmanager.model.services.TaskBusinessService;
import br.com.cast.turmaformacao.taskmanager.util.FormHelper;

public class TaskFormActivity extends AppCompatActivity {

    public static final String PARAM_TASK = "PARAM_TASK";
    private EditText editTextName;
    private EditText editTextDescription;
    private Task task;
    private Spinner spinnerLabel;
    private Button buttonAddLabel;
    private List<Label> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_form);
        initTask();

        list = LabelBusinessService.getAll();
        bindEditTextName();
        bindEditTextDescription();
        bindSpinner();
        updateSpinner();
        bindButtonAddLabel();
    }

    @Override
    protected void onResume() {
        list = LabelBusinessService.getAll();
        updateSpinner();
        if(task.getLabel() != null){
            spinnerLabel.setSelection(list.indexOf(task.getLabel()));
        }
        super.onResume();
    }

    private void initTask() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.task = (Task) extras.getParcelable(PARAM_TASK);
        }
        this.task = this.task == null ? new Task() : this.task;
    }

    private void bindButtonAddLabel() {
        buttonAddLabel = (Button) findViewById(R.id.buttonAddLabel);
        buttonAddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToLabelActivity = new Intent(TaskFormActivity.this, LabelFormActivity.class);
                startActivity(redirectToLabelActivity);
            }
        });
    }

    private void bindTask() {
        task.setName(editTextName.getText().toString());
        task.setDescription(editTextDescription.getText().toString());
        task.setLabel((Label) spinnerLabel.getSelectedItem());
    }

    private void bindEditTextDescription() {
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextDescription.setText(task.getDescription() == null ? "" : task.getDescription());
    }

    private void bindEditTextName() {
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextName.setText(task.getName() == null ? "" : task.getName());
    }

    private void bindSpinner() {
        spinnerLabel = (Spinner) findViewById(R.id.spinnerColor);
    }

    private void updateSpinner() {
        LabelListAdapter adapter = (LabelListAdapter) spinnerLabel.getAdapter();

        if(adapter == null) {
            adapter = new LabelListAdapter(TaskFormActivity.this, list);
            spinnerLabel.setAdapter(adapter);
        }

        adapter.setItens(list);
        //int teste = (int)(task.getLabel().getId() == null ? 0 : task.getLabel().getId());
        //spinnerLabel.setSelection(teste);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_task_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                onMenuAddClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onMenuAddClick() {
        String requiredMessage = TaskFormActivity.this.getString(R.string.msg_required);
        if (!FormHelper.checkRequireFields(requiredMessage, editTextName)) {
            bindTask();
            TaskBusinessService.save(task);
            Toast.makeText(TaskFormActivity.this, R.string.msg_save_success, Toast.LENGTH_LONG).show();
            TaskFormActivity.this.finish();
        }
    }

}
