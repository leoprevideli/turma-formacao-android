package br.com.cast.turmaformacao.taskmanager.model.http;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import br.com.cast.turmaformacao.taskmanager.model.entities.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class TaskService {

    private static final String URL = "http://10.11.21.193:3000/api/v1/task/";

    public TaskService() {
        super();
    }

    public static List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();

        try {
            URL url = new URL(URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();
            Log.i("getAllTasks",
                    "Código de retorno da requisição da lista de tarefas: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }

            InputStream inputStream = conn.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Task.class);
            tasks = objectMapper.readValue(inputStream, collectionType);

            conn.disconnect();
        } catch (IOException e) {
            Log.e(AddressService.class.getName(), e.getMessage());
        }

        return tasks;
    }

    public static Task getTaskByWebId(Long id) {

        Task task = null;

        try {
            URL url = new URL(URL + id.toString());
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();
            Log.i("getTaskByWebId",
                    "Código de retorno da requisição da tarefa: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }

            InputStream inputStream = conn.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            task = objectMapper.readValue(inputStream, Task.class);

            conn.disconnect();
        } catch (IOException e) {
            Log.e(AddressService.class.getName(), e.getMessage());
        }

        return task;
    }

    public static void saveTaskOnWeb(Task task) {

        try {
            URL url = new URL(URL);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            int responseCode = conn.getResponseCode();
            Log.i("saveTaskOnWeb",
                    "Código de retorno da requisição da tarefa: " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error code: " + responseCode);
            }

            OutputStream outputStream = conn.getOutputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, task);

            conn.disconnect();
        } catch (IOException e) {
            Log.e(AddressService.class.getName(), e.getMessage());
        }
    }

}
