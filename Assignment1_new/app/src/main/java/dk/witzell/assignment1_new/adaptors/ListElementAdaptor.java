package dk.witzell.assignment1_new.adaptors;

        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.content.Context;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;
        import java.util.ArrayList;
        import java.util.List;

        import dk.witzell.assignment1_new.R;
        import dk.witzell.assignment1_new.models.Job;






public class ListElementAdaptor extends BaseAdapter {

    private static final Integer FROM_ADAPTOR = 100;

    private Context context;
    private List<Job> jobList;
    private Job job;
    //private string jobPrefix;

    public ListElementAdaptor(List<Job> jobs, Context context)
    {
        jobList = jobs;
        this.context = context;

    }


/*
    public ListElementAdaptor(ArrayList<Job> jobList, Context c) {
        this.context = c;   //  Used for inflating views
        this.jobs = jobList; //  The list of actual jobs
    }
*/


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //if(requestCode == FROM_ADAPTOR)
    }

    @Override
    public int getCount() {
        // returning job list size
        if (jobList != null) return jobList.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        //  returning the job object from the list @ the "position"
        if (jobList != null) return jobList.get(position);
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

        job = jobList.get(position);
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
