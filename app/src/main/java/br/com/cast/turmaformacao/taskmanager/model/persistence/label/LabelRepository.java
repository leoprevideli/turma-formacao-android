package br.com.cast.turmaformacao.taskmanager.model.persistence.label;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.cast.turmaformacao.taskmanager.model.entities.Label;
import br.com.cast.turmaformacao.taskmanager.model.entities.Task;
import br.com.cast.turmaformacao.taskmanager.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.taskmanager.model.persistence.task.TaskContract;

public final class LabelRepository {

    private LabelRepository() {
        super();
    }

    public static void save(Label label) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = LabelContract.getContentValues(label);
        if (label.getId() == null) {
            db.insert(LabelContract.TABLE, null, values);
        } else {
            String where = LabelContract.ID + " = ? ";
            String[] params = {label.getId().toString()};
            db.update(LabelContract.TABLE, values, where, params);
        }

        db.close();
        databaseHelper.close();
    }

    public static Label getById(long id) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String where = LabelContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};

        Cursor cursor = db.query(LabelContract.TABLE, LabelContract.COLUMNS, where, params, null, null, null);
        Label label = LabelContract.getLabel(cursor);

        db.close();
        databaseHelper.close();
        return label;
    }

    public static List<Label> getAll() {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(LabelContract.TABLE, LabelContract.COLUMNS, null, null, null, null, null);
        List<Label> values = LabelContract.getLabels(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }
}
