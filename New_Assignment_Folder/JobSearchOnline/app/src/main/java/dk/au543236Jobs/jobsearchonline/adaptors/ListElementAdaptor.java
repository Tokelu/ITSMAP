package dk.witzell.jobsearchonline.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import dk.witzell.jobsearchonline.JobActivity;
import dk.witzell.jobsearchonline.NotesActivity;
import dk.witzell.jobsearchonline.R;
import dk.witzell.jobsearchonline.models.Job;
import dk.witzell.jobsearchonline.utils.DrawableGenerator;
import com.squareup.picasso.Picasso;



public class ListElementAdaptor extends RecyclerView.Adapter<ListElementAdaptor.ViewHolder>
{
    private List<Job> jobList;
    private Context context;
    private OnClickedJobListener onClickedJobListener;
    public static String JOB_FROM_ADAPTOR = "Adaptor Job";
    private ImageView companyLogo;

    //    public static final String ADAPTOR_POSITION = "Adaptor Position";
    //    private static final Integer JOB_FROM_ADAPTOR_CODE = 100;
    //    private DrawableGenerator drawableGenerator;




    public ListElementAdaptor(List<Job> jobs, OnClickedJobListener onClickedJobListener)
    {
        jobList = jobs;
        this.onClickedJobListener = onClickedJobListener;
//        drawableGenerator = new DrawableGenerator(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        context = parent.getContext();
        View listView = LayoutInflater.from(context).inflate(R.layout.job_list_element, parent,false);
        return new ViewHolder(listView, onClickedJobListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Job currentJob = jobList.get(i);
        viewHolder.txtViewCompanyName.setText(currentJob.getCompanyName());
        viewHolder.txtViewJobTitle.setText(currentJob.getTitle());
        viewHolder.txtViewApplied.setText(currentJob.getHasApplied() ? R.string.jobStatusTextApplied : R.string.jobStatusTextNotApplied);
        viewHolder.favoriteMark.setChecked(currentJob.getIsFavoriteMarked() ? true : false );
        viewHolder.txtViewCoolScore.setText(currentJob.getHasCoolnessScore() ? currentJob.getcoolnessScore() : "-.-");
        Picasso.with(context).load(currentJob.getCompanyLogo()).into(companyLogo);
//        viewHolder.imgViewLogo.setImageDrawable(drawableGenerator.getDrawableByName(currentJob));
    }

    @Override
    public int getItemCount(){return jobList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private OnClickedJobListener onClickedJobListener;
        private TextView txtViewCompanyName;
        private TextView txtViewJobTitle;
        private TextView txtViewCoolScore;
        private TextView txtViewApplied;
        private CheckBox favoriteMark;
//        private ImageView companyLogo;
//        private ImageView imgViewLogo;

        public ViewHolder(@NonNull View view, OnClickedJobListener onClickedJobListener)
        {
            super(view);
            txtViewCompanyName = view.findViewById(R.id.jobListElementTextViewCompany);
            txtViewJobTitle = view.findViewById(R.id.jobListElementTextViewJobTitle);
            txtViewCoolScore = view.findViewById(R.id.jobListElementTextViewCoolnessScore);
            txtViewApplied = view.findViewById(R.id.jobListElementTextViewApplied);
            companyLogo = view.findViewById(R.id.jobListElementLogo);
            favoriteMark = view.findViewById(R.id.favoritedMark);
            this.onClickedJobListener = onClickedJobListener;

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

            /*

            itemView.setOnClickListener(v ->
            {
                Job clickedJob = jobList.get(getAdapterPosition());
                Intent detailsIntent = new Intent(context, JobActivity.class);
                detailsIntent.putExtra(JOB_FROM_ADAPTOR, clickedJob);
                context.startActivity(detailsIntent);
            });

            itemView.setOnLongClickListener( v ->
            {
                Job clickedJob = jobList.get(getAdapterPosition());
                Intent detailsIntent = new Intent(context, NotesActivity.class);
                detailsIntent.putExtra(JOB_FROM_ADAPTOR, clickedJob);
                detailsIntent.putExtra(ADAPTOR_POSITION, getAdapterPosition());
                ((Activity) context).startActivityForResult(detailsIntent, JOB_FROM_ADAPTOR_CODE);
                return true;
            });

             */
        }

        @Override
        public boolean onLongClick(View view) {
            onClickedJobListener.onLongClickedJob(getAdapterPosition());
            return true;
        }

        @Override
        public void onClick(View view){
            onClickedJobListener.onClickedJob(getAdapterPosition());
        }
    }


    public interface OnClickedJobListener {
        void onClickedJob(int position);
        void onLongClickedJob(int position);
    }
/*
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("ListElementAdaptor", "onActivityResult");
        if(requestCode == JOB_FROM_ADAPTOR_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Job updatedJob = (Job) data.getSerializableExtra("TEST");
                int dataToReplace = data.getIntExtra(ADAPTOR_POSITION, 0);
                jobList.set(dataToReplace, updatedJob);
                notifyDataSetChanged();
            }
            if(resultCode == Activity.RESULT_CANCELED) {}
        }
    }

 */
}
