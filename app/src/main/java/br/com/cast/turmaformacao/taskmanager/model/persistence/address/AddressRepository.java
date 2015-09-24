package br.com.cast.turmaformacao.taskmanager.model.persistence.address;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.cast.turmaformacao.taskmanager.model.entities.Address;
import br.com.cast.turmaformacao.taskmanager.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.taskmanager.model.persistence.label.LabelContract;

public class AddressRepository {

    public static void save(Address address) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = AddressContract.getContentValues(address);
        if (address.getId() == null) {
            db.insert(LabelContract.TABLE, null, values);
        } else {
            String where = LabelContract.ID + " = ? ";
            String[] params = {address.getId().toString()};
            db.update(LabelContract.TABLE, values, where, params);
        }

        db.close();
        databaseHelper.close();
    }

    public static Address getByZipCode(String zipCode) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String where = AddressContract.ZIPCODE + " LIKE ? ";
        String[] params = {String.valueOf(zipCode)};

        Cursor cursor = db.query(AddressContract.TABLE, AddressContract.COLUMNS, where, params, null, null, null);
        Address address = AddressContract.getAddress(cursor);

        db.close();
        databaseHelper.close();
        return address;
    }

}
