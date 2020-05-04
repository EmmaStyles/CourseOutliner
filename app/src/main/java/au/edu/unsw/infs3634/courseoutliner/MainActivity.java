package au.edu.unsw.infs3634.courseoutliner;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CourseAdapter mAdapter;
    private CourseDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ExtendedFloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fab.isExtended()) {
                    fab.shrink();
                    fab.setIcon(getDrawable(R.drawable.ic_clear));
                    new GetCoursesTask().execute(true);
                } else {
                    fab.extend();
                    fab.setIcon(getDrawable(R.drawable.ic_filter));
                    new GetCoursesTask().execute(false);
                }
            }
        });

        final RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);

        mDb = Room.databaseBuilder(this, CourseDatabase.class, "courses.db").createFromAsset("courses.db").build();

        mAdapter = new CourseAdapter(new ArrayList<Course>(), new CourseAdapter.CourseClickListener() {
            @Override
            public void onClick(int id) {
                // Create an Intent object that launches the DetailActivity using an explicit intent
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.CODE_EXTRA, id);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        //Implement code that executes the AsyncTask implemented below without filtering the data
        new GetCoursesTask().execute();

    }

    private class GetCoursesTask extends AsyncTask<Boolean, Void, List<Course>> {

        @Override
        protected List<Course> doInBackground(Boolean... filtered) {
            if(filtered[0]) {
                return mDb.courseDao().getCourse("Info Systems & Tech Mgmt");
            } else {
                return mDb.courseDao().getCourses();
            }
        }

        // Implement an onPostExecute method which updates the CourseAdapter's data
        protected void onPostExecute(List<Course> courses) {
            mAdapter.setCourses(courses);

        }
    }
}
