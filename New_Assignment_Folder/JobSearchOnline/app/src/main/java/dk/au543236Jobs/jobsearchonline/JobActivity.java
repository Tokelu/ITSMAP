package dk.au543236Jobs.jobsearchonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import dk.au543236Jobs.jobsearchonline.Services.AsyncJobService;
import dk.au543236Jobs.jobsearchonline.models.Job;
//import dk.au543236Jobs.jobsearchonline.utils.DrawableGenerator;
//import dk.witzell.jobsearchonline.rooms.JobApp;

public class JobActivity extends AppCompatActivity
{
    private ImageView           logo;
    private TextView            companyName;
    private TextView            jobTitle;
    private TextView            location;
    private TextView            description;
    private TextView            notes;
    private TextView appliedMark;
    private TextView            coolnessScore;
    private CheckBox            favoriteMark;
    private Button              ok_Btn, remove_Btn;
//    private DrawableGenerator   drawableGenerator;
    //private TextView          statusResult;
    private Job                 clickedJob;
    private String              JOB_OBJECT_KEY = "Job";

    private AsyncJobService     jobService;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        if (savedInstanceState != null)
        {
            clickedJob = savedInstanceState.getParcelable(JOB_OBJECT_KEY);
        }
        initializeUI();
    }

    private void initializeUI()
    {
        companyName     = findViewById(R.id.activityJobTextViewCompany);
        jobTitle        = findViewById(R.id.activityJobTextViewJobtitle);
        location        = findViewById(R.id.activityJobTextViewLocation);
        logo            = findViewById(R.id.activityJobImageView);
        description     = findViewById(R.id.activityJobEditTextDescription);
        notes           = findViewById(R.id.activityJobEditTextNotes);
        appliedMark = findViewById(R.id.activityJobTextViewStatus);
        favoriteMark    = findViewById(R.id.favoritedMark);
        //statusResult  = findViewById(R.id.activityJobTextViewStatusResult);
        ok_Btn          = findViewById(R.id.activityJobButtonOK);
        remove_Btn      = findViewById(R.id.activityJobButtonRemove);
        coolnessScore   = findViewById(R.id.activityJobTextViewScore);

        ok_Btn.setOnClickListener(v -> finish());

        remove_Btn.setOnClickListener(v ->
        {
            if (isBound) {
                jobService.removeJob(clickedJob);
            }
            finish();
        });

        description.setMovementMethod(new ScrollingMovementMethod());
    }

    private void updateUI(Job job)
    {
        //this.setTitle(job.getCompanyName());
        jobTitle.setText(job.getTitle());
        companyName.setText(job.getCompanyName());
        location.setText(job.getLocation());
        description.setText(job.getDescription());
        appliedMark.setText((job.getIsMarkedApplied()? R.string.jobStatusTextApplied : R.string.jobStatusTextNotApplied));
        favoriteMark.setChecked(job.getIsFavoriteMarked());
        notes.setText(job.getNotes());
        Picasso.with(this).load(clickedJob.getCompanyLogo()).into(logo);
        if (job.getHasCoolnessScore())
        {
            coolnessScore.setText(job.getCoolnessScore());
        }
        else
        {
            coolnessScore.setText("-.-");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(JOB_OBJECT_KEY, clickedJob);
    }

    private ServiceConnection jobServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AsyncJobService.JobServiceBinder binder = (AsyncJobService.JobServiceBinder) iBinder;
            jobService = binder.getService();
            isBound = true;

            clickedJob = jobService.getCurrentJob();
            if (clickedJob != null) {
                updateUI(clickedJob);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AsyncJobService.class);
        bindService(intent, jobServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(jobServiceConnection);
        }
    }
}
