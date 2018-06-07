package com.app.eoussama.phoshare;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL(String.format("CREATE TABLE %s (%s INT PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s BLOB DEFAULT NULL, %s TEXT NOT NULL)", TABLE_USERS, COL_USERS_ID, COL_USERS_USERNAME, COL_USERS_PASSWORD, COL_USERS_SALT, COL_USERS_SECURITY_QUESTION, COL_USERS_SECURITY_ANSWER, COL_USERS_AVATAR, COL_USERS_JOIN_DATE));
        db.execSQL(String.format("CREATE TABLE %s (%s INT PRIMARY KEY AUTOINCREMENT, %s FOREIGN KEY REFERENCES %s(%s), %s BLOB DEFAULT NULL, %s TEXT DEFAULT NULL, %s TEXT NOT NULL)", TABLE_POSTS, COL_POSTS_ID, COL_POSTS_POSTER, TABLE_USERS, COL_USERS_ID, COL_POSTS_IMAGE, COL_POSTS_DESCRIPTION, COL_POSTS_POST_DATE));
        db.execSQL(String.format("CREATE TABLE %s (%s INT PRIMARY KEY AUTOINCREMENT, %s FOREIGN KEY REFERENCES %s(%s), %s FOREIGN KEY REFERENCES %s(%s), %s TEXT NOT NULL, %s TEXT NOT NULL)", TABLE_COMMENTS, COL_COMMENTS_ID, COL_COMMENTS_COMMENTER, TABLE_USERS, COL_USERS_ID, COL_COMMENTS_POST, TABLE_POSTS, COL_POSTS_ID, COL_COMMENTS_TEXT, COL_COMMENTS_DATE));
        db.execSQL(String.format("CREATE TABLE %s (%s INT PRIMARY KEY AUTOINCREMENT, %s FOREIGN KEY REFERENCES %s(%s), %s FOREIGN KEY REFERENCES %s(%s))", TABLE_FAVORITES, COL_FAVOURITES_ID, COL_FAVOURITES_ISSUER, TABLE_USERS, COL_USERS_ID, COL_FAVOURITES_POST, TABLE_POSTS, COL_POSTS_ID));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE %s", TABLE_USERS));
        db.execSQL(String.format("DROP TABLE %s", TABLE_POSTS));
        db.execSQL(String.format("DROP TABLE %s", TABLE_COMMENTS));
        db.execSQL(String.format("DROP TABLE %s", TABLE_FAVORITES));
    }
}
