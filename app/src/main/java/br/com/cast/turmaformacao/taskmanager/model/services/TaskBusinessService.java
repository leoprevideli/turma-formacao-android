package br.com.cast.turmaformacao.taskmanager.model.services;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.taskmanager.model.entities.Color;
import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.persistence.task.TaskRepository;

public final class TaskBusinessService {

    private TaskBusinessService() {
        super();
    }

    public static List<Task> findAll() {
        return TaskRepository.getAll();
    }

    public static void save(Task task) {
        TaskRepository.save(task);
    }

    public static void delete(Task selectedTask) {
        TaskRepository.delete(selectedTask.getId());
    }
}
