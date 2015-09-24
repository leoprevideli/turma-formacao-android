package br.com.cast.turmaformacao.taskmanager.controllers.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.cast.turmaformacao.taskmanager.R;
import br.com.cast.turmaformacao.taskmanager.model.entities.Address;
import br.com.cast.turmaformacao.taskmanager.model.entities.User;
import br.com.cast.turmaformacao.taskmanager.model.http.AddressService;
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

        //new GetAddressTask().execute("15995046");
    }

    private class GetAddressTask extends AsyncTask<String, Void, Address>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //Recebe um PARAM(stringCep) e retorna um RESULT(address)
        @Override
        protected Address doInBackground(String... params) {
            return AddressService.getAdressByZipCode(params[0]);
        }

        @Override
        protected void onPostExecute(Address address){
            onPostExecute(address);
        }
    }

    private void bindButtonLogin() {
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User();
                user.setUsername(editTextUsername.getText().toString());
                user.setPassword(editTextPassword.getText().toString());

                String requiredMessage = LoginActivity.this.getString(R.string.msg_required);
                if (!FormHelper.checkRequireFields(requiredMessage, editTextUsername, editTextPassword)){
                    boolean validUser = UserBusinessService.verifyUser(user);
                    if(validUser){
                        Intent redirectToTaskList = new Intent(LoginActivity.this, TaskListActivity.class);
                        startActivity(redirectToTaskList);
                    }
                    else{
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        dialogBuilder.setTitle("Invalid combination!");
                        dialogBuilder.setMessage("The user name or password is incorrect.");
                        dialogBuilder.setNeutralButton(android.R.string.ok, null);
                        dialogBuilder.show();
                    }
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
