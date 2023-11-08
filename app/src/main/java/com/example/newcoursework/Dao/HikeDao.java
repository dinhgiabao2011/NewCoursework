package com.example.newcoursework.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newcoursework.Models.Hike;

import java.util.List;

@Dao
public interface HikeDao {
    @Query("SELECT * FROM hikes where hikeName like '%' || :name || '%'")
    List<Hike> findByName(String name);
    @Query("SELECT * FROM hikes ORDER BY hikeName")
    List<Hike> getAllHike();
    @Insert
    Long insertHike(Hike hike);
    @Update
    void update(Hike hike);
    @Delete
    void deleteHike(Hike hike);
    @Query("DELETE FROM hikes")
    void deleteAllHike();
}
