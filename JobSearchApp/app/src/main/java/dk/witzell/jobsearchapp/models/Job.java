package dk.witzell.jobsearchapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Job implements Parcelable
{

    //region Fields / Variables
    private String  appTitle;
    private int     logo;               // need to assign some kind of ID to each logo image
    private String  companyName;
    private String  jobTitle;
    private String  coolScore;
    private String  location;
    private String  jobDescription;
    private String  notes;
    private boolean status;
    private String  statusResult;
    private boolean coolnessScore;
    private boolean userNotes;
    private boolean applied;
    //endregion

    //region Getters
    public String   getAppTitle()       {return appTitle;}
    public String   getCompanyName()    {return companyName;}
    public String   getJobTitle()       {return jobTitle;}
    public String   getCoolScore()      {return coolScore;}
    public int      getLogo()           {return logo;}
    public String   getLocation()       {return location;}
    public String   getjobDescription() {return jobDescription;}
    public String   getNotes()          {return notes;}
    public Boolean  getStatus()         {return status;}
    //endregion

    //region Setters
    void setAppTitle(String appTitle)                   { this.appTitle = appTitle; }
    void setCompanyName(String companyName)             { this.companyName = companyName; }
    void setJobTitle(String jobTitle)                   { this.jobTitle = jobTitle; }
    public void setCoolScore(String coolScore)          { this.coolScore = coolScore; }
    void setLogo()                                      { this.logo = logo; }
    void setLocation(String location)                   { this.location = location; }
    void setJobDescription(String jobDescription)       { this.jobDescription = jobDescription; }
    public void setNotes(String notes)                  { this.notes = notes; }
    public void setStatus(boolean applied)              { this.status = applied; }
    public void setStatusResult(String applied)         { this.statusResult = applied; }
    public void setHasCoolnessScore(boolean hasCoolnessScore) { this.coolnessScore = hasCoolnessScore; }
    public void setHasUserNotes(boolean hasUserNotes)   { this.userNotes = hasUserNotes; }
    //endregion


    public boolean hasCoolnessScore()   {return coolnessScore;}
    public boolean hasUserNotes()       {return userNotes;}
    public boolean hasApplied()         {return applied;}

    public Job(String companyName, String jobTitle, String coolScore, String jobDescription)
    {
        this.companyName    = companyName;
        this.jobTitle       = jobTitle;
        this.coolScore      = coolScore;
        this.jobDescription = jobDescription;
        this.statusResult   = "Not-Applied";
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.appTitle);
        dest.writeString(this.companyName);
        dest.writeString(this.jobTitle);
        dest.writeString(this.coolScore);
        dest.writeString(this.location);
        dest.writeString(this.jobDescription);
        dest.writeString(this.notes);
        dest.writeByte(this.status              ? (byte) 1 : (byte) 0);
        dest.writeByte(this.applied          ? (byte) 1 : (byte) 0);
        dest.writeByte(this.coolnessScore    ? (byte) 1 : (byte) 0);
        dest.writeByte(this.userNotes        ? (byte) 1 : (byte) 0);
    }

    protected Job(Parcel input)
    {
        this.appTitle           = input.readString();
        this.companyName        = input.readString();
        this.jobTitle           = input.readString();
        this.coolScore          = input.readString();
        this.location           = input.readString();
        this.jobDescription     = input.readString();
        this.notes              = input.readString();
        this.status             = input.readByte() != 0;
        this.applied         = input.readByte() != 0;
        this.coolnessScore   = input.readByte() != 0;
        this.userNotes       = input.readByte() != 0;
    }

    @Override
    public int describeContents() {return 0;}

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel parcel) { return new Job(parcel); }

        @Override
        public Job[] newArray(int size) { return new Job[size];}
    };
}
