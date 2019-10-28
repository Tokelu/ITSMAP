package dk.witzell.jobsearchonline.models;

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

    private boolean favoriteMark;
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



    public String   getCompanyName()    {return companyName;}
    public String   getJobDescription() {return jobDescription;}
    public String   getJobTitle()       {return jobTitle;}
    public String   getLocation()       {return location;}
    public String   getNotes()          {return notes;}
    public boolean  hasCoolnessScore()  {return coolnessScore;}
    public boolean  hasUserNotes()      {return userNotes;}
    public boolean  hasApplied()        {return applied;}
    public boolean  isFavoriteMarked()  {return favoriteMark;}
    public String   getAppTitle()       {return appTitle;}
    public String   getCoolScore()      {return coolScore;}
    public int      getLogo()           {return logo;}



    void setCompanyName(String companyName)             { this.companyName = companyName; }
    void setJobDescription(String jobDescription)       { this.jobDescription = jobDescription; }
    void setJobTitle(String jobTitle)                   { this.jobTitle = jobTitle; }
    void setLocation(String location)                   { this.location = location; }
    public void setNotes(String notes)                  { this.notes = notes; }
    public void setStatus(boolean applied)              { this.applied = applied; }
    public void setFavoriteMarked(boolean favoriteMark) { this.favoriteMark = favoriteMark; }
    public void setCoolScore(String coolScore)          { this.coolScore = coolScore; }
    public void setHasCoolnessScore(boolean hasCoolnessScore) { this.coolnessScore = hasCoolnessScore; }
    public void setHasUserNotes(boolean hasUserNotes)   { this.userNotes = hasUserNotes; }


    public void setStatusResult(String applied)         { this.statusResult = applied; }
    void setAppTitle(String appTitle)                   { this.appTitle = appTitle; }
    void setLogo()                                      { this.logo = logo; }

}
