package dk.witzell.jobsearchapp.adaptors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import dk.witzell.jobsearchapp.JobActivity;
import dk.witzell.jobsearchapp.NotesActivity;
import dk.witzell.jobsearchapp.R;
import dk.witzell.jobsearchapp.models.Job;
import dk.witzell.jobsearchapp.utils.DrawableGenerator;

public class ListElementAdaptor extends RecyclerView.Adapter<ListElementAdaptor.ViewHolder>
{
    private static final String ADAPTOR_POSITION = "Adaptor Position";
    private static final Integer JOB_FROM_ADAPTOR_CODE = 100;
    public static String JOB_FROM_ADAPTOR = "Adaptor Job";
    private DrawableGenerator drawableGenerator;
    private List<Job> jobList;
    private Context context;


    public ListElementAdaptor(List<Job> jobs, Context context)
    {
        this.jobList = jobs;
        this.context = context;
        this.drawableGenerator = new DrawableGenerator(context);
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
        viewHolder.txtViewJobTitle.setText(currentJob.getJobTitle());
        viewHolder.txtViewCoolScore.setText(currentJob.getCoolScore());
        viewHolder.imgViewLogo.setImageDrawable(drawableGenerator.getDrawableByName(currentJob));
    }

    @Override
    public int getItemCount(){return jobList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtViewCompanyName;
        TextView txtViewJobTitle;
        TextView txtViewCoolScore;
        ImageView imgViewLogo;
        TextView txtViewApplied;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtViewCompanyName = itemView.findViewById(R.id.jobListElementTextViewCompany);
            txtViewJobTitle = itemView.findViewById(R.id.jobListElementTextViewJobTitle);
            txtViewCoolScore = itemView.findViewById(R.id.jobListElementTextViewCoolnessScore);
            txtViewApplied = itemView.findViewById(R.id.jobListElementTextViewApplied);

            imgViewLogo = itemView.findViewById(R.id.jobListElementLogo);

            itemView.setOnClickListener(v -> { Job clickedJob = jobList.get(getAdapterPosition());
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
        if(requestCode == JOB_FROM_ADAPTOR_CODE)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                Job updatedJob = Objects.requireNonNull(data.getExtras()).getParcelable("key");
                int dataToReplace = data.getIntExtra(ADAPTOR_POSITION, 0);
                jobList.set(dataToReplace, updatedJob);
                notifyDataSetChanged();
            }
            if(resultCode == Activity.RESULT_CANCELED) {}
        }
    }
}
