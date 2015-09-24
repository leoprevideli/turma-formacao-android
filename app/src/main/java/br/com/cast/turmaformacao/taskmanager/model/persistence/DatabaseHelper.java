package br.com.cast.turmaformacao.taskmanager.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.cast.turmaformacao.taskmanager.model.persistence.address.AddressContract;
import br.com.cast.turmaformacao.taskmanager.model.persistence.label.LabelContract;
import br.com.cast.turmaformacao.taskmanager.model.persistence.task.TaskContract;
import br.com.cast.turmaformacao.taskmanager.model.persistence.user.UserContract;
import br.com.cast.turmaformacao.taskmanager.util.ApplicationUtil;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskmanagerdb";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance() {
        return new DatabaseHelper(ApplicationUtil.getContext());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskContract.getCreateTableScript());
        db.execSQL(LabelContract.getCreateTableScript());
        db.execSQL(UserContract.getCreateTableScript());
        db.execSQL(AddressContract.getCreateTableScript());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}