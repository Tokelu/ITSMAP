package dk.witzell.jobsearchapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Job implements Serializable
{
    private String  companyName;
    private String  jobTitle;
    private String  location;
    private String  jobDescription;
    private String  coolScore;
    private String  notes;

    private boolean applied;
    private boolean coolnessScore;
    private boolean userNotes;

    //region Fields / Variables
    private String  appTitle = "My Jobs";
    private int     logo;               // need to assign some kind of ID to each logo image
    private String  statusResult;
    //endregion



    public Job(String companyName, String location, String jobTitle, String jobDescription)
    {
        this.companyName    = companyName;
        this.jobDescription = jobDescription;
        this.jobTitle       = jobTitle;
        this.location       = location;
    }


//    public Job(String companyName, String jobTitle, String coolScore, String jobDescription, String location )
//    {
//        this.companyName    = companyName;
//        this.jobDescription = jobDescription;
//        this.jobTitle       = jobTitle;
//        this.location       = location;
//        this.coolScore      = coolScore;
//
//        this.statusResult   = "Not-Applied";
//    }


    public String   getCompanyName()    {return companyName;}
    public String   getJobDescription() {return jobDescription;}
    public String   getJobTitle()       {return jobTitle;}
    public String   getLocation()       {return location;}
    public String   getNotes()          {return notes;}
    public boolean  hasCoolnessScore()  {return coolnessScore;}
    public boolean  hasUserNotes()      {return userNotes;}
    public boolean  hasApplied()        {return applied;}
    public String   getAppTitle()       {return appTitle;}
    public String   getCoolScore()      {return coolScore;}
    public int      getLogo()           {return logo;}



    void setCompanyName(String companyName)             { this.companyName = companyName; }
    void setJobDescription(String jobDescription)       { this.jobDescription = jobDescription; }
    void setJobTitle(String jobTitle)                   { this.jobTitle = jobTitle; }
    void setLocation(String location)                   { this.location = location; }
    public void setNotes(String notes)                  { this.notes = notes; }
    public void setStatus(boolean applied)              { this.applied = applied; }
    public void setCoolScore(String coolScore)          { this.coolScore = coolScore; }
    public void setHasCoolnessScore(boolean hasCoolnessScore) { this.coolnessScore = hasCoolnessScore; }
    public void setHasUserNotes(boolean hasUserNotes)   { this.userNotes = hasUserNotes; }


    public void setStatusResult(String applied)         { this.statusResult = applied; }
    void setAppTitle(String appTitle)                   { this.appTitle = appTitle; }
    void setLogo()                                      { this.logo = logo; }


/*
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
    }*/

    /*protected Job(Parcel input)
    {
        this.appTitle           = input.readString();
        this.companyName        = input.readString();
        this.jobTitle           = input.readString();
        this.coolScore          = input.readString();
        this.location           = input.readString();
        this.jobDescription     = input.readString();
        this.notes              = input.readString();

//        this.status             = input.readByte() != 0;
        this.applied         = input.readByte() != 0;
        this.coolnessScore   = input.readByte() != 0;
        this.userNotes       = input.readByte() != 0;
    }*/

    /*
    @Override
    public int describeContents() {return 0;}

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel parcel) { return new Job(parcel); }

        @Override
        public Job[] newArray(int size) { return new Job[size];}
    };*/
}
