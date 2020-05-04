package au.edu.unsw.infs3634.courseoutliner;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailActivity extends AppCompatActivity {
    //changed CODE_EXTRA to public so it can be accessed from other classes
    public static final String CODE_EXTRA = "CODE_EXTRA";
    private CourseDatabase mDb;

    private TextView mCode, mName, mDegree, mSchool, mYear, mTerm;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCode = findViewById(R.id.tvCode);
        mName = findViewById(R.id.tvName);
        mDegree = findViewById(R.id.tvSchool);
        mSchool = findViewById(R.id.tvDegree);
        mYear = findViewById(R.id.tvYear);
        mTerm = findViewById(R.id.tvTerm);
        mFab = findViewById(R.id.fab);

        mDb = Room.databaseBuilder(this, CourseDatabase.class, "courses.db").build();

        Intent intent = getIntent();
        //TODO BELOW
        Bundle id = intent.getExtras("CODE_EXTRA", 1);

        new GetCourseTask().execute();
    }

    private void updateUi(Course param) {
        //changed to final so onCLick method can access course
        final Course course = param;
        mCode.setText(course.getCode());
        mName.setText(course.getName());
        mDegree.setText(course.getLevel());
        mSchool.setText(course.getSchool());
        mYear.setText(course.getYear());
        mTerm.setText(course.getTerm());
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(String.valueOf(DetailActivity.this), Uri.parse("https://www.google.com/search?q=" + course.getName()));
                intent.setData(course.getUrl());
                startActivity(intent);
            }
        });
    }

    private class GetCourseTask extends AsyncTask<Integer, Void, Course> {

        @Override
        protected Course doInBackground(Integer... id) {
            return mDb.courseDao().getCourse(id[0]);
        }

        @Override
        protected void onPostExecute(Course course) {
            updateUi(getCourse(id));
        }
    }
}
