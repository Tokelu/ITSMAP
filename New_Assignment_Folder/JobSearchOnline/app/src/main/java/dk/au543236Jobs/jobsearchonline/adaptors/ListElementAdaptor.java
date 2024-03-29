package dk.au543236Jobs.jobsearchonline.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dk.au543236Jobs.jobsearchonline.R;
import dk.au543236Jobs.jobsearchonline.models.Job;

import com.squareup.picasso.Picasso;

public class ListElementAdaptor extends RecyclerView.Adapter<ListElementAdaptor.ViewHolder>{
    private List<Job> jobList;
    private Context context;
    private OnClickedJobListener onClickedJobListener;
    public static String JOB_FROM_ADAPTOR = "Adaptor Job";
    private ImageView companyLogo;

    public ListElementAdaptor(List<Job> jobs, OnClickedJobListener onClickedJobListener) {
        jobList = jobs;
        this.onClickedJobListener = onClickedJobListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        context = parent.getContext();
        View listView = LayoutInflater.from(context).inflate(R.layout.job_list_element, parent,false);
        return new ViewHolder(listView, onClickedJobListener );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Job currentJob = jobList.get(i);
        viewHolder.txtViewCompanyName.setText(currentJob.getCompanyName());
        viewHolder.txtViewJobTitle.setText(currentJob.getTitle());
        viewHolder.txtViewApplied.setText(currentJob.getIsMarkedApplied() ? R.string.jobStatusTextApplied : R.string.jobStatusTextNotApplied);
        viewHolder.favoriteMark.setChecked(currentJob.getIsFavoriteMarked());
        viewHolder.txtViewCoolScore.setText(currentJob.getHasCoolnessScore() ? currentJob.getCoolnessScore() : "-.-");
        Picasso.with(context).load(currentJob.getCompanyLogo()).into(companyLogo);
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
}
