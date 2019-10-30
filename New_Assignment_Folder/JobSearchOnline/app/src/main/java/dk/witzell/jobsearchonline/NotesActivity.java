package dk.witzell.jobsearchonline;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Objects;

import dk.witzell.jobsearchonline.adaptors.ListElementAdaptor;
import dk.witzell.jobsearchonline.models.Job;

public class NotesActivity extends AppCompatActivity
{
    private TextView    companyName;
    private TextView    jobTitle;
    private TextView    coolScore;
    private SeekBar     seekBarRating;
    private EditText    notes;
    private Button      ok_Btn;
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
    //private String      JOB_TITLE_KEY = "jobTitle";
    //private String      COMPANY_NAME_KEY = "companyName";

    private int ADAPTER_POSITION_FROM_RECYCLE_VIEW;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        initializeIU();
        Intent intent = getIntent();
        clickedJob = (Job) intent.getExtras().getSerializable(ListElementAdaptor.JOB_FROM_ADAPTOR); // clickedJob = Objects.requireNonNull(intent.getExtras()).getParcelable(ListElementAdaptor.JOB_FROM_ADAPTOR);
        ADAPTER_POSITION_FROM_RECYCLE_VIEW = intent.getIntExtra(ListElementAdaptor.ADAPTOR_POSITION, 0);
        if (savedInstanceState != null)
        {
            int oldSeekBarValue = savedInstanceState.getInt(SEEKBAR_VALUE_KEY);
            boolean OldAppliedStatus = savedInstanceState.getBoolean(APPLIED_STATUS_KEY);
            boolean oldFavoriteStatus = savedInstanceState.getBoolean(FAVORITE_STATUS_KEY);
            boolean hasCoolnessScore = savedInstanceState.getBoolean(USER_COOLNESS_RATING_KEY);
            String oldNotes = savedInstanceState.getString(NOTES_KEY);
            //String oldJobTitle = savedInstanceState.getString(JOB_TITLE_KEY);
            //String oldCompanyName = savedInstanceState.getString(COMPANY_NAME_KEY);

            clickedJob.setHasCoolnessScore(hasCoolnessScore);
            seekBarRating.setProgress(oldSeekBarValue);
            appliedCheckBox.setChecked(OldAppliedStatus);
            favoriteStatus.setChecked(oldFavoriteStatus);
            notes.setText(oldNotes);
            //jobTitle.setText(oldJobTitle);
            //companyName.setText(oldCompanyName);
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



        ok_Btn.setOnClickListener(v -> {
            Job updatedJob = updateJobValues(clickedJob);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("TEST", updatedJob);
            returnIntent.putExtra(ListElementAdaptor.ADAPTOR_POSITION, ADAPTER_POSITION_FROM_RECYCLE_VIEW);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        cancel_Btn.setOnClickListener(View -> finish());

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
        ok_Btn          = findViewById(R.id.activityNotesTextViewOKBtn);
        cancel_Btn      = findViewById(R.id.activityNotesTextViewCancelBtn);
        favoriteStatus  = findViewById(R.id.favoritedMark);


    }

    private void updateIU(Job job)
    {
        companyName.setText(job.getCompanyName());
        jobTitle.setText(job.getTitle());

        if (job.getHasCoolnessScore())
        {
            //coolScore.setText("Coolness Score: ");
            coolScore.setText(job.getcoolnessScore());
            float coolnessScore = Float.valueOf(job.getcoolnessScore());
            seekBarRating.setProgress(Math.round(coolnessScore * 10));
            seekBarValue = ((float) seekBarRating.getProgress() / 10);
        }
        else
        {
            coolScore.setText(R.string.setCoolnessScoreText);
        }

        if (job.getHasApplied())
        {
            appliedCheckBox.setChecked(true);
        }

        if (job.getIsFavoriteMarked())
        {
            favoriteStatus.setChecked(true);
        }

        if (job.getHasUserNotes())
        {
            //notes.setText(R.string.setNotesPrependText);
            notes.append(job.getNotes());
        }
        else
        {
            notes.setText(" ");
        }
    }

    private Job updateJobValues(Job job)
    {
        job.setCoolnessScore(String.valueOf(seekBarValue));
        job.setHasCoolnessScore(true);
        job.setNotes(notes.getText().toString());
        job.setHasUserNotes(true);
        if (appliedCheckBox.isChecked())
        {
            job.setHasApplied(true);
        }
        else
        {
            job.setHasApplied(false);
        }
        if (favoriteStatus.isChecked())
        {
            job.setIsFavoriteMarked(true);
        }
        else
        {
            job.setIsFavoriteMarked(false);
        }
        return job;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("", clickedJob);
//        savedInstanceState.putInt(SEEKBAR_VALUE_KEY, seekBarRating.getProgress());
//        savedInstanceState.putBoolean(APPLIED_STATUS_KEY, appliedCheckBox.isChecked());
//        savedInstanceState.putString(NOTES_KEY, notes.getText().toString());
//        savedInstanceState.putString(JOB_TITLE_KEY, jobTitle.getText().toString());
//        savedInstanceState.putString(COMPANY_NAME_KEY, companyName.getText().toString());
//        savedInstanceState.putString(USER_COOLNESS_RATING_KEY, coolScore.getText().toString());
    }
}
