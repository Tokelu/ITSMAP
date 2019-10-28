package dk.witzell.jobsearchapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import dk.witzell.jobsearchapp.adaptors.ListElementAdaptor;
import dk.witzell.jobsearchapp.models.Job;
import dk.witzell.jobsearchapp.utils.DrawableGenerator;

public class JobActivity extends AppCompatActivity
{
    private ImageView   logo;
    private TextView    companyName;
    private TextView    jobTitle;
    private TextView    location;
    private TextView    description;
    private TextView    notes;
    private TextView    status;
    private TextView    coolnessScore;

    private Button      ok_Btn;

    private DrawableGenerator drawableGenerator;

    private TextView    statusResult;

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
            clickedJob = (Job) savedInstanceState.getSerializable(JOB_OBJECT_KEY);
            updateUI(Objects.requireNonNull(clickedJob));
        }
        else
        {
            clickedJob = (Job) Objects.requireNonNull(getIntent().getExtras()).getSerializable(ListElementAdaptor.JOB_FROM_ADAPTOR);
            updateUI(Objects.requireNonNull(clickedJob));
        }
        ok_Btn.setOnClickListener(v -> finish());
    }


    private void initializeUI()
    {
        companyName = findViewById(R.id.activityJobTextViewCompany);
        jobTitle = findViewById(R.id.activityJobTextViewJobtitle);
        location = findViewById(R.id.activityJobTextViewLocation);
        logo = findViewById(R.id.activityJobImageView);
        description = findViewById(R.id.activityJobEditTextDescription);
        notes = findViewById(R.id.activityJobEditTextNotes);
        status = findViewById(R.id.activityJobTextViewStatus);
        statusResult = findViewById(R.id.activityJobTextViewStatusResult);
        ok_Btn = findViewById(R.id.activityJobButton);
        coolnessScore = findViewById(R.id.activityJobTextViewScore);
        description.setMovementMethod(new ScrollingMovementMethod());
    }

    private void updateUI(Job job)
    {
        //this.setTitle(job.getCompanyName());
        jobTitle.setText(job.getJobTitle());
        companyName.setText(job.getCompanyName());
        location.setText(job.getLocation());
        description.setText(job.getJobDescription());
        status.setText((job.hasApplied()? R.string.jobStatusTextApplied : R.string.jobStatusTextNotApplied));
        notes.setText(job.getNotes());
        logo.setImageDrawable(drawableGenerator.getDrawableByName(job));
        if (job.hasCoolnessScore())
        {
            coolnessScore.setText(job.getCoolScore());
        }
        else
        {
            coolnessScore.setText("-");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(JOB_OBJECT_KEY, clickedJob);
    }


}
