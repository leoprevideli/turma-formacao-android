package br.com.cast.turmaformacao.taskmanager.model.persistence.task;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.taskmanager.model.persistence.label.LabelContract;

public final class TaskRepository {

    private TaskRepository() {
        super();
    }

    public static void save(Task task) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = TaskContract.getContentValues(task);
        if (task.get_id() == null) {
            db.insert(TaskContract.TABLE, null, values);
        } else {
            String where = TaskContract._ID + " = ? ";
            String[] params = {task.get_id().toString()};
            db.update(TaskContract.TABLE, values, where, params);
        }

        db.close();
        databaseHelper.close();
    }

    public static void delete(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String where = TaskContract._ID + " = ? ";
        String[] params = {String.valueOf(id)};
        db.delete(TaskContract.TABLE, where, params);

        db.close();
        databaseHelper.close();
    }

    public static List<Task> getAll() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(TaskContract.TABLE, TaskContract.COLUMNS, null, null, null, null, TaskContract._ID);
        List<Task> values = TaskContract.getTasks(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }

    public static Task getTask(long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = TaskContract._ID + " = ? ";
        String[] params = {String.valueOf(id)};

        Cursor cursor = db.query(TaskContract.TABLE, TaskContract.COLUMNS, where, params, null, null, null);
        Task task = TaskContract.getTask(cursor);

        db.close();
        databaseHelper.close();
        return task;
    }

    public static Label getLabelByTaskId(long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = TaskContract._ID + " = ? ";
        String[] params = {String.valueOf(id)};

        Cursor cursor = db.query(TaskContract.TABLE, TaskContract.COLUMNS, where, params, null, null, null);
        Label label = TaskContract.getTask(cursor).getLabel();

        db.close();
        databaseHelper.close();
        return label;
    }

    public static long getTaskByWebId(long webId){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = TaskContract.IDWEB + " = ? ";
        String[] params = {String.valueOf(webId)};

        Cursor cursor = db.query(TaskContract.TABLE, TaskContract.COLUMNS, where, params, null, null, null);
        long idWebRetorno = TaskContract.getTask(cursor).get_id();

        db.close();
        databaseHelper.close();
        return idWebRetorno;
    }
}
