package dk.witzell.jobsearchapp.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import dk.witzell.jobsearchapp.R;
import dk.witzell.jobsearchapp.models.Job;





public class ListElementAdaptor extends BaseAdapter {
    private Context context;
    private ArrayList<Job> jobs;
    private Job job;
    //private string jobPrefix;

    public ListElementAdaptor(Context c, ArrayList<Job> jobList) {
        this.context = c;   //  Used for inflating views
        this.jobs = jobList; //  The list of actual jobs
    }

    @Override
    public int getCount() {
        // returning job list size
        if (jobs != null) return jobs.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        //  returning the job object from the list @ the "position"
        if (jobs != null) return jobs.get(position);
        else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //First time creation of the views
        if (convertView == null) {
            LayoutInflater jobInflator = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = jobInflator.inflate(R.layout.job_list_element, null);
        }

        job = jobs.get(position);
        if(job != null)
        {
            //  Declaring widgets
            TextView txtViewCompanyName = convertView.findViewById(R.id.jobListElementTextViewCompany);
            TextView txtViewJobTitle = convertView.findViewById(R.id.jobListElementTextViewJobTitle);
            TextView txtViewCoolScore = convertView.findViewById(R.id.jobListElementTextViewCoolnessScore);
            ImageView imgViewLogo = convertView.findViewById(R.id.jobListElementLogo);

            //  creating the widgets
            txtViewJobTitle.setText(job.getJobTitle());
            txtViewCompanyName.setText(job.getCompanyName());
            txtViewCoolScore.setText(job.getCoolScore());
            imgViewLogo.setImageResource(job.getLogo());
        }
        return convertView;
    }
}
