package br.com.cast.turmaformacao.taskmanager.model.persistence.user;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.cast.turmaformacao.taskmanager.model.entities.Address;
import br.com.cast.turmaformacao.taskmanager.model.entities.User;

public class UserContract {

        public static final String TABLE = "user";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String ADDRESS_ID = "address_id";
        public static final String[] COLUMNS = { ID, USERNAME, PASSWORD, ADDRESS_ID };

                public static String getCreateTableScript(){
                StringBuilder sql = new StringBuilder();
                sql.append(" CREATE TABLE ");
                sql.append(TABLE);
                sql.append(" ( ");
                sql.append(ID + " INTEGER PRIMARY KEY, ");
                sql.append(USERNAME + " TEXT, ");
                sql.append(PASSWORD + " TEXT, ");
                sql.append(ADDRESS_ID + " INTEGER ");
                sql.append(" ); ");
                return sql.toString();
            }

                public static ContentValues getContentValues(User user){
                ContentValues values = new ContentValues();
                values.put(UserContract.ID, user.getId());
                values.put(UserContract.USERNAME, user.getUsername());
                values.put(UserContract.PASSWORD, user.getPassword());
                values.put(UserContract.ADDRESS_ID, user.getUsername());
                return  values;
            }

            public static User bind(Cursor cursor){
                if(!cursor.isBeforeFirst() || cursor.moveToNext()){
                    User user = new User();
                    user.setId(cursor.getInt(cursor.getColumnIndex(UserContract.ID)));
                    user.setUsername(cursor.getString(cursor.getColumnIndex(UserContract.USERNAME)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(UserContract.PASSWORD)));

                    Address address = new Address();
                    address.setZipCode(cursor.getString(cursor.getColumnIndex(UserContract.ADDRESS_ID)));
                    user.setAddress(address);

                    return user;
                }
            return null;

            }

}
