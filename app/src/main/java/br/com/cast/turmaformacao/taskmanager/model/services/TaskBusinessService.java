package br.com.cast.turmaformacao.taskmanager.model.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.taskmanager.model.entities.Color;
import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.http.TaskService;
import br.com.cast.turmaformacao.taskmanager.model.persistence.task.TaskRepository;

public final class TaskBusinessService {

    private TaskBusinessService() {
        super();
    }

    public static List<Task> findAll() {
        return TaskRepository.getAll();
    }

    public static Task getTask(long id) {
        return TaskRepository.getTask(id);
    }

    public static void save(Task task) {
        TaskRepository.save(task);
    }

    public static void delete(Task selectedTask) {
        TaskRepository.delete(selectedTask.get_id());
    }

    public static long getTaskByWebId(long webId) {
        return TaskRepository.getTaskByWebId(webId);
    }

    public static void sincronizeTasks() {
        saveWebTasks(TaskService.getAllTasks());
    }

    private static void saveWebTasks(List<Task> tasks) {
        for (Task task : tasks) {
            long verifyTaskId = TaskBusinessService.getTaskByWebId(task.getWebId());
            if (verifyTaskId > 0) { //update
                task.set_id(verifyTaskId);
            }
            TaskBusinessService.save(task);
        }
    }
}
