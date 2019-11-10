package dk.au543236Jobs.jobsearchonline;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import dk.au543236Jobs.jobsearchonline.Services.AsyncJobService;
import dk.au543236Jobs.jobsearchonline.adaptors.ListElementAdaptor;
import dk.au543236Jobs.jobsearchonline.models.Job;

/*TODO    ##################### THIS IS DONE ######################################*/

public class NotesActivity extends AppCompatActivity
{
    private TextView    companyName;
    private TextView    jobTitle;
    private TextView    coolScore;
    private SeekBar     seekBarRating;
    private EditText    notes;
    private Button      save_Btn;
    private CheckBox    favoriteStatus;
    private Button      cancel_Btn;
    private float       seekBarValue;
    private CheckBox    appliedCheckBox;
    private Job         clickedJob;

    private String      SEEKBAR_VALUE_KEY = "seekBarValue";
    private String      APPLIED_STATUS_KEY = "appliedStatus";
    private String      FAVORITE_STATUS_KEY = "favoriteStatus";
    private String      NOTES_KEY = "notesText";
    private String      USER_COOLNESS_RATING_KEY = "userCoolnessRating";
//    private String      JOB_TITLE_KEY = "jobTitle";
//    private String      COMPANY_NAME_KEY = "companyName";
//    private int ADAPTER_POSITION_FROM_RECYCLE_VIEW;


    private boolean isBound = false;
    private AsyncJobService jobService;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        initializeIU();
        Intent intent = getIntent();
        clickedJob = (Job) intent.getExtras().getSerializable(ListElementAdaptor.JOB_FROM_ADAPTOR);
        if (savedInstanceState != null)
        {
            int oldSeekBarValue = savedInstanceState.getInt(SEEKBAR_VALUE_KEY);
            boolean OldAppliedStatus = savedInstanceState.getBoolean(APPLIED_STATUS_KEY);
            boolean oldFavoriteStatus = savedInstanceState.getBoolean(FAVORITE_STATUS_KEY);
            boolean hasCoolnessScore = savedInstanceState.getBoolean(USER_COOLNESS_RATING_KEY);
            String oldNotes = savedInstanceState.getString(NOTES_KEY);

            clickedJob.setHasCoolnessScore(hasCoolnessScore);
            seekBarRating.setProgress(oldSeekBarValue);
            appliedCheckBox.setChecked(OldAppliedStatus);
            favoriteStatus.setChecked(oldFavoriteStatus);
            notes.setText(oldNotes);
            if (!hasCoolnessScore)
            {
                coolScore.setText("Set your Coolness Score");
            }
            else
            {
                seekBarValue = ((float) oldSeekBarValue / 10);
                coolScore.setText("Coolness Score: ");
                coolScore.append("" + seekBarValue);
            }
        }
        else
        {
            updateIU(Objects.requireNonNull(clickedJob));
        }



        save_Btn.setOnClickListener(v -> {
            if (isBound) {
                clickedJob.setHasApplied(appliedCheckBox.isChecked());
                clickedJob.setNotes(notes.getText().toString());
                clickedJob.setCoolnessScore(coolScore.getText().toString());
                jobService.updateJob(clickedJob);
            }
            setResult(RESULT_OK);
            finish();
        });

        cancel_Btn.setOnClickListener(View -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                seekBarValue =((float) progress /10);
                coolScore.setText("Coolness Rate: ");
                coolScore.append(String.valueOf(seekBarValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                clickedJob.setHasCoolnessScore(true);
            }
        });
    }

    private void initializeIU()
    {
        companyName     = findViewById(R.id.activityNotesTextViewCompany);
        jobTitle        = findViewById(R.id.activityJobTextViewJobtitle );
        coolScore       = findViewById(R.id.activityNotesTextViewScore);
        seekBarRating   = findViewById(R.id.activityNotesSeek);
        notes           = findViewById(R.id.activityNotesEditTextNote);
        appliedCheckBox = findViewById(R.id.activityNotesCheckBox);
        save_Btn        = findViewById(R.id.activityNotesTextViewSaveBtn);
        cancel_Btn      = findViewById(R.id.activityNotesTextViewCancelBtn);
        favoriteStatus  = findViewById(R.id.favoritedMark);


    }

    @SuppressLint("ClickableViewAccessibility")
    private void updateIU(Job job)
    {
        companyName.setText(job.getCompanyName());
        jobTitle.setText(job.getTitle());

        if (job.getHasCoolnessScore())
        {
            coolScore.setText(job.getcoolnessScore());
            float coolnessScore = Float.valueOf(job.getcoolnessScore());
            seekBarRating.setProgress(Math.round(coolnessScore * 10));
            seekBarValue = ((float) seekBarRating.getProgress() / 10);
        }
        else { coolScore.setText(R.string.setCoolnessScoreText); }

        if (job.getHasApplied())        { appliedCheckBox.setChecked(true); }
        if (job.getIsFavoriteMarked())  { favoriteStatus.setChecked(true); }
        if (job.getHasUserNotes())      { notes.append(job.getNotes()); }
        else                            { notes.setText(" "); }
    }

    private ServiceConnection jobServiceConnection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            AsyncJobService.JobServiceBinder binder = (AsyncJobService.JobServiceBinder) iBinder;
            jobService = binder.getService();
            isBound = true;
            clickedJob = jobService.getCurrentJob();
            if (clickedJob != null) {
                updateIU(clickedJob);
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


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        clickedJob.setCoolnessScore(coolScore.getText().toString());
        clickedJob.setNotes(notes.getText().toString());
        clickedJob.setHasApplied(appliedCheckBox.isChecked());
        savedInstanceState.putSerializable(getString(R.string.jobSaved), clickedJob);
    }
}
