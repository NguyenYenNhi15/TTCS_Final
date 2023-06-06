package com.example.ttcs_final.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ttcs_final.Model.CongViec;
import com.example.ttcs_final.Model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ViecLam.db";
    private static int DATABASE_VERSION = 1;

    public SQLHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String usersSql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "email TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(usersSql);

        String congviecsql = "CREATE TABLE CongViecs(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, content TEXT, date TEXT, status TEXT," +
                "together INTEGER)";
        sqLiteDatabase.execSQL(congviecsql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //    Users
    public Boolean insertData(String name, String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("Users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from Users where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from Users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public User getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"id", "name", "email", "password"};
        String selection = "email = ?" + " AND " + "password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query("Users", columns, selection, selectionArgs, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String userEmail = cursor.getString(2);
            String userPassword = cursor.getString(3);
            user = new User(id, name, userEmail, userPassword);
        }
        cursor.close();
        return user;
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = getReadableDatabase();
        User user = null;

        Cursor cursor = db.query(
                "Users",
                new String[]{"id", "name", "email", "password"},
                "id = ?",
                new String[]{String.valueOf(userId)},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
            cursor.close();
        }
        return user;
    }

    public void changePassword(int userId, String newPassword) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        String[] args = {String.valueOf(userId)};
        db.update("Users", values, "id = ?", args);
    }

    // Cong Viec
    public List<CongViec> getAllCV() {
        List<CongViec> list = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("CongViecs", null, null, null, null, null, order);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int together = rs.getInt(5);
            list.add(new CongViec(id, name, content, date, status, together));
        }
        return list;
    }

    public long addItem(CongViec i) {
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("content", i.getContent());
        values.put("date", i.getDate());
        values.put("status", i.getStatus());
        values.put("together", i.getTogether());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("CongViecs", null, values);
    }

    //    Lay các item theo date:
    public List<CongViec> getByDate(String date) {
        List<CongViec> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("CongViecs", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String status = rs.getString(4);
            int together = rs.getInt(5);
            list.add(new CongViec(id, name, content, date, status, together));
        }
        return list;
    }

    public List<CongViec> searchByTitle(String key) {
        List<CongViec> list = new ArrayList<>();
        String whereClause = "name LIKE ? OR content LIKE ?";
        String[] whereArgs = {"%" + key + "%", "%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("CongViecs", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int together = rs.getInt(5);
            list.add(new CongViec(id, name, content, date, status, together));
        }
        return list;
    }

    public String countByStatus() {
        String result = "";
        String query = "SELECT status, COUNT(*) FROM CongViecs GROUP BY status ORDER BY COUNT(*) DESC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor != null && cursor.moveToNext()) {
            String status = cursor.getString(0);
            int count = cursor.getInt(1);
            result += status + " : " + String.valueOf(count) + "\n";
        }
        return result;
    }

    public List<CongViec> searchByCategory(String category) {
        List<CongViec> list = new ArrayList<>();
        String whereClause = "status like ?";
        String[] whereArgs = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("CongViecs", null, whereClause, whereArgs, null, null, null);
        while (rs != null && rs.moveToNext()) {
            int id = rs.getInt(0);
            String name = rs.getString(1);
            String content = rs.getString(2);
            String date = rs.getString(3);
            String status = rs.getString(4);
            int together = rs.getInt(5);
            list.add(new CongViec(id, name, content, date, status, together));
        }
        return list;
    }

    //    Update:
    public int update(CongViec i) {
        ContentValues values = new ContentValues();
        values.put("name", i.getName());
        values.put("content", i.getContent());
        values.put("date", i.getDate());
        values.put("status", i.getStatus());
        values.put("together", i.getTogether());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClase = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("CongViecs", values, whereClase, whereArgs);
    }

    //    Delete:
    public int delete(int id) {
        String whereClase = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("CongViecs", whereClase, whereArgs);

    }
}

