package roman.com.cryptobox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roman on 9/17/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * main constructor
     * @param context
     */
    public DatabaseHandler(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }


    /**
     * initialize database on first use of app
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TableUsers.CREATE_TABLE);
    }

    /**
     * on database upgrade - drop all tables and re-create the database
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(DatabaseContract.TableUsers.DELETE_TABLE);
        // Create tables again
        onCreate(db);
    }


    /**
     * add a user to the database
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableUsers.COLUMN_USERNAME, user.getUserName());
        values.put(DatabaseContract.TableUsers.COLUMN_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(DatabaseContract.TableUsers.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    /**
     * get a user from the database
     * @param id
     * @return
     */
    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DatabaseContract.TableUsers.TABLE_NAME, new String[] {DatabaseContract.TableUsers.COLUMN_ID,
                        DatabaseContract.TableUsers.COLUMN_USERNAME, DatabaseContract.TableUsers.COLUMN_PASSWORD}, DatabaseContract.TableUsers.COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return user;
    }

    /**
     * get a list of all the users from the database
     * @return
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseContract.TableUsers.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUserName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                // Adding contact to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return userList;
    }

    /**
     * update a single user listing
     * @param contact
     * @return
     */
    public int updateUser(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TableUsers.COLUMN_USERNAME, contact.getUserName());
        values.put(DatabaseContract.TableUsers.COLUMN_PASSWORD, contact.getPassword());

        // updating row
        return db.update(DatabaseContract.TableUsers.COLUMN_ID, values, DatabaseContract.TableUsers.COLUMN_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
    }

    /**
     * delete a single user from the database
     * @param contact
     */
    public void deleteUser(User contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DatabaseContract.TableUsers.TABLE_NAME, DatabaseContract.TableUsers.COLUMN_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }


    /**
     * get the number of users in the database
     * @return
     */
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + DatabaseContract.TableUsers.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}