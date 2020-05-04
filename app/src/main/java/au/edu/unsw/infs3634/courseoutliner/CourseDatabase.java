package au.edu.unsw.infs3634.courseoutliner;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
