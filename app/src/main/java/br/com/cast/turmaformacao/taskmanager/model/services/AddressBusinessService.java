package br.com.cast.turmaformacao.taskmanager.model.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.cast.turmaformacao.taskmanager.model.entities.Address;
import br.com.cast.turmaformacao.taskmanager.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.taskmanager.model.persistence.address.AddressContract;
import br.com.cast.turmaformacao.taskmanager.model.persistence.address.AddressRepository;

public class AddressBusinessService {

    public AddressBusinessService() {
        super();
    }

    public static void save(Address address) {
        AddressRepository.save(address);
    }

    public static Address getByZipCode(String zipCode) {
        return AddressRepository.getByZipCode(zipCode);
    }

    public static List<Address> getAll(){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(AddressContract.TABLE, AddressContract.COLUMNS, null, null, null, null, null);
        List<Address> values = AddressContract.getAddresses(cursor);

        db.close();
        databaseHelper.close();
        return values;
    }
}
