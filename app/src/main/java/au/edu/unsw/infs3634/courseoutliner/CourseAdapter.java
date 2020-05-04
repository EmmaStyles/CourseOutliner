package au.edu.unsw.infs3634.courseoutliner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private CourseClickListener listener;

    public CourseAdapter(List<Course> courses, CourseClickListener listener) {
//        mParentActivity = parent;
        mCourses = courses;
        this.listener = listener;
    }

    public interface CourseClickListener {
        void onClick(int id);
    }
    public static class CourseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name, school, code;
        final CourseClickListener listener;

        CourseViewHolder(View v, CourseClickListener listener) {
            super(v);
            v.setOnClickListener(this);
            this.listener = listener;
            name = v.findViewById(R.id.tvName);
            school = v.findViewById(R.id.tvSchool);
            code = v.findViewById(R.id.tvCode);
        }

        @Override
        public void onClick (View v) {
            listener.onClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = mCourses.get(position);
        holder.name.setText(course.getName());
        holder.school.setText(course.getSchool());
        holder.code.setText(course.getCode());
        holder.itemView.setTag(course);
//        holder.itemView.setOnClickListener(listener);
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
