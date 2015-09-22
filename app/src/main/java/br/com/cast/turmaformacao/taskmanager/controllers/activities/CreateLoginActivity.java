package br.com.cast.turmaformacao.taskmanager.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.entities.User;
import br.com.cast.turmaformacao.taskmanager.model.services.TaskBusinessService;
import br.com.cast.turmaformacao.taskmanager.model.services.UserBusinessService;
import br.com.cast.turmaformacao.taskmanager.util.FormHelper;

public class CreateLoginActivity extends AppCompatActivity {

    public static final String PARAM_USER = "PARAM_USER";
    private EditText editTextNewUsername;
    private EditText editTextNewPassword;
    private Button buttonSaveNewUser;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        initUser();

        editTextNewUsername = (EditText) findViewById(R.id.editTextNewUsername);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        bindButtonSaveNewUser();
    }

    private void bindButtonSaveNewUser() {
        buttonSaveNewUser = (Button) findViewById(R.id.buttonSaveNewUser);

        buttonSaveNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requiredMessage = CreateLoginActivity.this.getString(R.string.msg_required);
                if (!FormHelper.checkRequireFields(requiredMessage, editTextNewUsername, editTextNewPassword)) {
                    bindNewUser();
                    UserBusinessService.save(newUser);
                    Toast.makeText(CreateLoginActivity.this, R.string.msg_save_success, Toast.LENGTH_LONG).show();

                    Intent redirectToLogin = new Intent(CreateLoginActivity.this, LoginActivity.class);
                    startActivity(redirectToLogin);
                }
            }
        });
    }

    private void initUser() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.newUser = (User) extras.getParcelable(PARAM_USER);
        }
        this.newUser = this.newUser == null ? new User() : this.newUser;
    }

    public void bindNewUser() {
        newUser.setUsername(editTextNewUsername.getText().toString());
        newUser.setPassword(editTextNewPassword.getText().toString());
    }


}
