package com.example.newcoursework.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newcoursework.Dao.HikeDao;
import com.example.newcoursework.Models.Hike;

@Database(entities = {Hike.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}
