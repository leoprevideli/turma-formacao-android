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
        if (address.getZipCode().equals("")) {
            db.insert(AddressContract.TABLE, null, values);
        } else {
            String where = AddressContract.ZIPCODE + " = ? ";
            String[] params = {address.getZipCode().toString()};
            db.update(AddressContract.TABLE, values, where, params);
        }

        db.close();
        databaseHelper.close();
    }

    public static Address getByZipCode(String zipCode) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String where = AddressContract.ZIPCODE + " = ? ";
        String[] params = {String.valueOf(zipCode)};

        Cursor cursor = db.query(AddressContract.TABLE, AddressContract.COLUMNS, where, params, null, null, null);
        Address address = AddressContract.getAddress(cursor);

        db.close();
        databaseHelper.close();
        return address;
    }

}
