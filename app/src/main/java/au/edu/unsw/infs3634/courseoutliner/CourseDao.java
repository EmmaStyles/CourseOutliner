package au.edu.unsw.infs3634.courseoutliner;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {
    @Query("SELECT * FROM course")
    List<Course> getCourses();

    //TODO Implement a Room query that return a course with a specified id
    @Query("SELECT * FROM course WHERE id = :id")
    List<Course> getCourse(int id);

    //TODO Implement a Room query that returns all courses from a specified school
    @Query("SELECT * FROM course WHERE school = :school")
    List <Course> getCourse(String school);
}
