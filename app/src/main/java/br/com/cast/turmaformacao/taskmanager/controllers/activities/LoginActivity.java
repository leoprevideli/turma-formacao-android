package br.com.cast.turmaformacao.taskmanager.controllers.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.model.entities.User;
import br.com.cast.turmaformacao.taskmanager.model.persistence.user.UserContract;
import br.com.cast.turmaformacao.taskmanager.model.persistence.user.UserRepository;
import br.com.cast.turmaformacao.taskmanager.model.services.UserBusinessService;
import br.com.cast.turmaformacao.taskmanager.util.FormHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonSignIn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindEditTextUsername();
        bindEditTextPassword();
        bindButtonLogin();
        bindButtonSignIn();
    }

    private void bindButtonLogin() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setUsername(editTextUsername.getText().toString());
                user.setPassword(editTextPassword.getText().toString());

                boolean validUser = UserBusinessService.verifyUser(user);
                if(validUser){
                    //Entra!
                    Intent redirectToTaskList = new Intent(LoginActivity.this, TaskListActivity.class);
                    startActivity(redirectToTaskList);
                    finish();
                }
                else{
                    //Dialog usuário inválido!
                }

            }
        });
        User user = new User();
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(editTextPassword.getText().toString());

    }

    private void bindButtonSignIn() {
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent redirectToCreateLogin = new Intent(LoginActivity.this, CreateLoginActivity.class);
                startActivity(redirectToCreateLogin);
                finish();
            }
        });
    }

    private void bindEditTextPassword() {
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    private void bindEditTextUsername() {
        editTextUsername = (EditText) findViewById(R.id.editTextLogin);
    }

}
