package dk.witzell.jobsearchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import dk.witzell.jobsearchapp.adaptors.ListElementAdaptor;
import dk.witzell.jobsearchapp.models.Job;
import dk.witzell.jobsearchapp.utils.DrawableGenerator;

public class JobActivity extends AppCompatActivity
{
    private TextView    companyName;
    private TextView    location;
    private ImageView   logo;
    private TextView    jobTitle;
    private TextView    description;
    private TextView    notes;
    private TextView    status;
    private TextView    statusResult;
    private Button      ok_Btn;
    private DrawableGenerator drawableGenerator;
    private Job         clickedJob;
    private String JOB_OBJECT_KEY = "Job";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        drawableGenerator = new DrawableGenerator(this);
        initializeUI();
        if (savedInstanceState != null)
        {
            clickedJob = savedInstanceState.getParcelable(JOB_OBJECT_KEY);
            updateUI(Objects.requireNonNull(clickedJob));
        }
        else
        {
            clickedJob = Objects.requireNonNull(getIntent().getExtras()).getParcelable(ListElementAdaptor.JOB_FROM_ADAPTOR);
            updateUI(Objects.requireNonNull(clickedJob));
        }

    }


    private void initializeUI()
    {
        companyName = findViewById(R.id.activityJobTextViewCompany);
        location = findViewById(R.id.activityJobTextViewLocation);
        logo = findViewById(R.id.activityJobImageView);
        jobTitle = findViewById(R.id.activityJobTextViewJobtitle);
        description = findViewById(R.id.activityJobTextViewDescription);
        notes = findViewById(R.id.activityJobTextViewNotes);
        status = findViewById(R.id.activityJobTextViewStatus);
        statusResult = findViewById(R.id.activityJobTextViewStatusResult);
        ok_Btn = findViewById(R.id.activityJobButton);
    }

    private void updateUI(Job job)
    {
        //this.setTitle(job.getCompanyName());
        companyName.setText(job.getCompanyName());
        location.setText(job.getLocation());
        logo.setImageDrawable(drawableGenerator.getDrawableByName(job));
        jobTitle.setText(job.getJobTitle());
        description.setText(job.getjobDescription());
        notes.setText(job.getNotes());
        status.setText((job.getStatus()? "Applied" : "Not Applied"));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable(JOB_OBJECT_KEY, clickedJob);
    }


}
