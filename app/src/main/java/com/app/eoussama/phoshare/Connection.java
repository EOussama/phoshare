package com.app.eoussama.phoshare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Connection extends SQLiteOpenHelper {
    private final String TABLE_USERS = "users", TABLE_POSTS = "posts", TABLE_COMMENTS = "comments", TABLE_FAVORITES = "favorites";
    private final String COL_USERS_ID = "u_id", COL_USERS_USERNAME = "u_username", COL_USERS_PASSWORD = "u_password", COL_USERS_SALT = "u_salt", COL_USERS_SECURITY_QUESTION = "u_security_question", COL_USERS_SECURITY_ANSWER = "u_security_answer", COL_USERS_AVATAR = "u_avatar", COL_USERS_JOIN_DATE = "u_join_date";
    private final String COL_POSTS_ID = "p_id", COL_POSTS_POSTER = "p_poster", COL_POSTS_IMAGE = "p_image", COL_POSTS_DESCRIPTION = "p_description", COL_POSTS_POST_DATE = "p_post_date";
    private final String COL_COMMENTS_ID = "c_id", COL_COMMENTS_COMMENTER = "c_commenter", COL_COMMENTS_POST = "c_post", COL_COMMENTS_TEXT = "c_text", COL_COMMENTS_DATE = "c_date";
    private final String COL_FAVOURITES_ID = "f_id", COL_FAVOURITES_ISSUER = "f_issuer", COL_FAVOURITES_POST = "f_post";

    public Connection(Context context) {
        super(context, "photo_share_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s BLOB DEFAULT NULL, %s TEXT NOT NULL)", TABLE_USERS, COL_USERS_ID, COL_USERS_USERNAME, COL_USERS_PASSWORD, COL_USERS_SALT, COL_USERS_SECURITY_QUESTION, COL_USERS_SECURITY_ANSWER, COL_USERS_AVATAR, COL_USERS_JOIN_DATE));
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s BLOB DEFAULT NULL, %s TEXT DEFAULT NULL, %s TEXT NOT NULL, FOREIGN KEY(%s) REFERENCES %s(%s))", TABLE_POSTS, COL_POSTS_ID, COL_POSTS_POSTER, COL_POSTS_IMAGE, COL_POSTS_DESCRIPTION, COL_POSTS_POST_DATE, COL_POSTS_POSTER, TABLE_USERS, COL_USERS_ID));
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, %s TEXT NOT NULL, %s TEXT NOT NULL, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s))", TABLE_COMMENTS, COL_COMMENTS_ID, COL_COMMENTS_COMMENTER, COL_COMMENTS_POST, COL_COMMENTS_TEXT, COL_COMMENTS_DATE, COL_COMMENTS_COMMENTER, TABLE_POSTS, COL_POSTS_ID, COL_COMMENTS_COMMENTER, TABLE_USERS, COL_USERS_ID));
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER, %s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s), FOREIGN KEY(%s) REFERENCES %s(%s))", TABLE_FAVORITES, COL_FAVOURITES_ID, COL_FAVOURITES_ISSUER, COL_FAVOURITES_POST, COL_FAVOURITES_ISSUER, TABLE_USERS, COL_USERS_ID, COL_FAVOURITES_POST, TABLE_POSTS, COL_POSTS_ID));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE %s", TABLE_USERS));

        db.execSQL(String.format("DROP TABLE %s", TABLE_POSTS));
        db.execSQL(String.format("DROP TABLE %s", TABLE_COMMENTS));
        db.execSQL(String.format("DROP TABLE %s", TABLE_FAVORITES));
    }

    public boolean isValidUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT %s FROM %s WHERE %s = '%s'", COL_USERS_ID, TABLE_USERS, COL_USERS_USERNAME, username), null);
        boolean valid = cursor.moveToFirst();
        cursor.close();

        return valid;
    }

    public boolean isPasswordCorrect(String password, String username) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT %s FROM %s WHERE %s = '%s' AND %s = '%s'", COL_USERS_ID, TABLE_USERS, COL_USERS_USERNAME, username, COL_USERS_PASSWORD, jPasswords.Hash(password, getUserSalt(username), -1)), null);
        boolean valid = cursor.moveToFirst();
        cursor.close();

        return valid;
    }

    private String getUserSalt(String username) {
        String salt = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT %s FROM %s WHERE %s = '%s'", COL_USERS_SALT, TABLE_USERS, COL_USERS_USERNAME, username), null);

        if(cursor.moveToFirst()) salt = cursor.getString(0);
        cursor.close();

        return salt;
    }

    public String getUserSecurityQuestion(String username) {
        String securityQuestion = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT %s FROM %s WHERE %s = '%s'", COL_USERS_SECURITY_QUESTION, TABLE_USERS, COL_USERS_USERNAME, username), null);

        if(cursor.moveToFirst()) securityQuestion = cursor.getString(0);
        cursor.close();

        return securityQuestion;
    }

    public String getUserSecurityAnswer(String username) {
        String securityAnswer = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT %s FROM %s WHERE %s = '%s'", COL_USERS_SECURITY_ANSWER, TABLE_USERS, COL_USERS_USERNAME, username), null);

        if(cursor.moveToFirst()) securityAnswer = cursor.getString(0);
        cursor.close();

        return securityAnswer;
    }

    public void RegisterUser(HashMap<String, String> user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        String salt = jPasswords.Generate(128, null);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale.UK);

        values.put(COL_USERS_USERNAME, user.get("username"));
        values.put(COL_USERS_PASSWORD, jPasswords.Hash(user.get("password"), salt, -1));
        values.put(COL_USERS_SALT, salt);
        values.put(COL_USERS_SECURITY_QUESTION, user.get("security_question"));
        values.put(COL_USERS_SECURITY_ANSWER, user.get("security_answer"));
        values.put(COL_USERS_JOIN_DATE, sdf.format(date));

        db.insert(TABLE_USERS, null, values);
        db.close();
    }
}