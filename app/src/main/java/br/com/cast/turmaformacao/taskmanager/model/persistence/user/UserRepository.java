package br.com.cast.turmaformacao.taskmanager.model.persistence.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.cast.turmaformacao.taskmanager.model.entities.User;
import br.com.cast.turmaformacao.taskmanager.model.persistence.DatabaseHelper;

public class UserRepository {

    private static UserRepository singletonInstance;

    private UserRepository(){
        super();
    }

    public static UserRepository getInstance(){
        if(UserRepository.singletonInstance == null){
            UserRepository.singletonInstance = new UserRepository();
        }

        return UserRepository.singletonInstance;
    }

    public static void save(User user) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = UserContract.getContentValues(user);
        if (user.getId() == null) {
            db.insert(UserContract.TABLE, null, values);
        } else {
            String where = UserContract.ID + " = ? ";
            String[] params = {user.getId().toString()};
            db.update(UserContract.TABLE, values, where, params);
        }

        db.close();
        databaseHelper.close();
    }

    public static boolean verifyUser(User user) {

        boolean isValid = false;

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] selectionArgs = {user.getUsername(), user.getPassword()};

        Cursor cursor = db.query(UserContract.TABLE, UserContract.COLUMNS, UserContract.USERNAME + " LIKE ? AND " +
                UserContract.PASSWORD + " LIKE ? ", selectionArgs, null, null, null);

        isValid = cursor.getCount() != 0;
        db.close();
        databaseHelper.close();

        return isValid;
    }

}
