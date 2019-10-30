package dk.witzell.jobsearchonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import java.util.Objects;

import dk.witzell.jobsearchonline.adaptors.ListElementAdaptor;
import dk.witzell.jobsearchonline.models.Job;
import dk.witzell.jobsearchonline.utils.DrawableGenerator;
import dk.witzell.jobsearchonline.rooms.JobApp;

public class JobActivity extends AppCompatActivity
{
    private ImageView           logo;
    private TextView            companyName;
    private TextView            jobTitle;
    private TextView            location;
    private TextView            description;
    private TextView            notes;
    private TextView            status;
    private TextView            coolnessScore;
    private CheckBox            favoriteMark;
    private Button              ok_Btn, remove_Btn;
    private DrawableGenerator   drawableGenerator;
    //private TextView          statusResult;
    private Job                 clickedJob;
    private String              JOB_OBJECT_KEY = "Job";


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

        remove_Btn.setOnClickListener(v ->
        {
            deleteJobFromDatabase(clickedJob);
            finish();
        });
    }

    private void deleteJobFromDatabase(Job job){
        ((JobApp) getApplicationContext()).getJobDb().JobDbDAO().delete(job);
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
        favoriteMark = findViewById(R.id.favoritedMark);
        //statusResult = findViewById(R.id.activityJobTextViewStatusResult);
        ok_Btn = findViewById(R.id.activityJobButtonOK);
        coolnessScore = findViewById(R.id.activityJobTextViewScore);
        description.setMovementMethod(new ScrollingMovementMethod());
    }

    private void updateUI(Job job)
    {
        //this.setTitle(job.getCompanyName());
        jobTitle.setText(job.getTitle());
        companyName.setText(job.getCompanyName());
        location.setText(job.getLocation());
        description.setText(job.getDescription());
        status.setText((job.getHasApplied()? R.string.jobStatusTextApplied : R.string.jobStatusTextNotApplied));
        favoriteMark.setChecked(job.getIsFavoriteMarked() ? true : false);
        notes.setText(job.getNotes());
        logo.setImageDrawable(drawableGenerator.getDrawableByName(job));
        if (job.getHasCoolnessScore())
        {
            coolnessScore.setText(job.getcoolnessScore());
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
