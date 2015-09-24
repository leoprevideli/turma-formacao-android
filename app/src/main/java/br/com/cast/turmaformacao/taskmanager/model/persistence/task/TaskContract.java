package br.com.cast.turmaformacao.taskmanager.model.persistence.task;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;

public final class TaskContract {

    public static final String TABLE = "task";
    public static final String _ID = "_id";
    public static final String IDWEB = "idweb";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String LABEL = "label";

    public static final String[] COLUMNS = {_ID, IDWEB, NAME, DESCRIPTION, LABEL};

    private TaskContract() {
        super();
    }

    public static String getCreateTableScript() {
        final StringBuilder create = new StringBuilder();

        create.append(" CREATE TABLE " + TABLE);
        create.append(" ( ");
        create.append(_ID + " INTEGER PRIMARY KEY, ");
        create.append(IDWEB + " INTEGER UNIQUE, ");
        create.append(NAME + " TEXT NOT NULL, ");
        create.append(DESCRIPTION + " TEXT, ");
        create.append(LABEL + " INTEGER ");
        create.append(" ); ");

        return create.toString();
    }

    public static ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract._ID, task.get_id());
        values.put(TaskContract.IDWEB, task.getWebId());
        values.put(TaskContract.NAME, task.getName());
        values.put(TaskContract.DESCRIPTION, task.getDescription());
        if (task.getLabel() != null) {
            values.put(TaskContract.LABEL, task.getLabel().getId());
        }

        return values;
    }

    public static Task getTask(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Task task = new Task();
            task.set_id(cursor.getLong(cursor.getColumnIndex(TaskContract._ID)));
            task.setWebId(cursor.getLong(cursor.getColumnIndex(TaskContract.IDWEB)));
            task.setName(cursor.getString(cursor.getColumnIndex(TaskContract.NAME)));
            task.setDescription(cursor.getString(cursor.getColumnIndex(TaskContract.DESCRIPTION)));

            long idLabel = cursor.getLong(cursor.getColumnIndex(TaskContract.LABEL));
            if (idLabel > 0) {
                Label label = new Label();
                label.setId(idLabel);
                task.setLabel(label);
            }
            return task;
        }
        return null;
    }

    public static List<Task> getTasks(Cursor cursor) {
        ArrayList<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            tasks.add(getTask(cursor));
        }
        return tasks;
    }

}
