package au.edu.unsw.infs3634.courseoutliner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//TODO  Implement the CourseAdapter class so that it integrates into the existing code base:
//      - The CourseAdapter must be connected to the RecyclerView in the MainActivity
//      - The CourseAdapter must use the course_item.xml layout file to display the list items
//      - The CourseAdapter's list items must be clickable to launch the DetailActivity
//      - The CourseAdapter's data must be modifiable during runtime
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private MainActivity mParentActivity;
    private List<Course> mCourses;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Course course = (Course) v.getTag();
            Context context = v.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.CODE_EXTRA, course.getId());
            context.startActivity(intent);
        }
    };

    public CourseAdapter(MainActivity parent, List<Course> courses) {
        mParentActivity = parent;
        mCourses = courses;
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        public TextView name, school, code;
        public CourseViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            school = v.findViewById(R.id.tvSchool);
            code = v.findViewById(R.id.tvCode);

        }
    }

    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = mCourses.get(position);
        holder.name.setText(course.getName());
        holder.school.setText(course.getSchool());
        holder.code.setText(course.getCode());
        holder.itemView.setTag(course);
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> courses) {
        mCourses.clear();
        mCourses.addAll(courses);
        notifyDataSetChanged();
    }
}
