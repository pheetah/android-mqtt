package com.example.a1bitirmeprofil2;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    //@Query("SELECT count(*) FROM User LIMIT 1")
    //List<User> ifEmpty();

    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE email AND password IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM User WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Insert
    void insertAll(List<User> user);

    @Insert
    void insertone(User user);

    @Delete
    void delete(User user);

    @Delete
    void deleteAll (List<User> users);
}