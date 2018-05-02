package com.example.android.privedu;

/**
 * Created by Abay on 3/24/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder>{
    Context context;
    ArrayList<Subject> subjects;
    DatabaseHelper databaseHelper;

    public SubjectAdapter(Context context, ArrayList<Subject> subjects) {
        this.context = context;
        this.subjects = subjects;
        databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SubjectViewHolder(LayoutInflater.from(context).inflate(R.layout.list_subject, parent, false));
    }

    @Override
    public void onBindViewHolder(SubjectViewHolder holder, int position) {
        Subject currentSubject = subjects.get(position);
        holder.title.setText(currentSubject.getName());
        holder.lecture.setText(databaseHelper.getUserName(currentSubject.getId_teacher()));
        holder.price.setText("Rp. "+currentSubject.getPrice()+"/org");
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, price, lecture;
        public SubjectViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.subject_name);
            price = itemView.findViewById(R.id.price_subject);
            lecture = itemView.findViewById(R.id.lecture_name);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Subject subject = subjects.get(getAdapterPosition());
            Intent intent = null;
            SharedPreferences sharedPreferences = context.getSharedPreferences("Account", 0);

            User user = databaseHelper.getInfo(sharedPreferences.getString("email", null));

            if (user.getRole().equals("Teacher")) intent = new Intent(context, DetailTeacherSubject.class);
            else intent = new Intent(context, DetailSubjectActivity.class);

            intent.putExtra("teacher_name", databaseHelper.getUserName(subject.getId_teacher()));
            intent.putExtra("name", subject.getName());
            intent.putExtra("price", Integer.toString(subject.getPrice()));
            context.startActivity(intent);
        }
    }
}
