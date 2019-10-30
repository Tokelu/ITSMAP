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
    public static final String ADAPTOR_POSITION = "Adaptor Position";
    private static final Integer JOB_FROM_ADAPTOR_CODE = 100;
    public static String JOB_FROM_ADAPTOR = "Adaptor Job";
    private DrawableGenerator drawableGenerator;
    private List<Job> jobList;
    private Context context;


    public ListElementAdaptor(List<Job> jobs, Context context)
    {
        jobList = jobs;
        this.context = context;
        drawableGenerator = new DrawableGenerator(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.job_list_element, parent,false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        Job currentJob = jobList.get(i);
        viewHolder.txtViewCompanyName.setText(currentJob.getCompanyName());
        viewHolder.txtViewJobTitle.setText(currentJob.getTitle());
        viewHolder.txtViewApplied.setText(currentJob.getHasApplied() ? R.string.jobStatusTextApplied : R.string.jobStatusTextNotApplied);
        viewHolder.favoriteMark.setChecked(currentJob.getIsFavoriteMarked() ? true : false );
        viewHolder.txtViewCoolScore.setText(currentJob.getcoolnessScore());
        viewHolder.imgViewLogo.setImageDrawable(drawableGenerator.getDrawableByName(currentJob));

        // TODO: Load Logo image into each currentJob object.
        //Picasso.with(context).load(currentJob.getLogo()).into(ViewHolder.imgViewLogo);


    }

    @Override
    public int getItemCount(){return jobList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtViewCompanyName;
        TextView txtViewJobTitle;
        TextView txtViewCoolScore;
        TextView txtViewApplied;
        CheckBox favoriteMark;
        ImageView imgViewLogo;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtViewCompanyName = itemView.findViewById(R.id.jobListElementTextViewCompany);
            txtViewJobTitle = itemView.findViewById(R.id.jobListElementTextViewJobTitle);
            txtViewCoolScore = itemView.findViewById(R.id.jobListElementTextViewCoolnessScore);
            txtViewApplied = itemView.findViewById(R.id.jobListElementTextViewApplied);
            imgViewLogo = itemView.findViewById(R.id.jobListElementLogo);
            favoriteMark = itemView.findViewById(R.id.favoritedMark);

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
        }
    }

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
}
