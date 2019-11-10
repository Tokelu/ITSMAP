package dk.witzell.jobsearchonline.models;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;



@Entity
public class Job implements Serializable
{
    private boolean hasApplied;
    private boolean hasCoolnessScore;
    private boolean hasUserNotes;
    private boolean isFavoriteMarked;

    public String companySearch = "company";

    @PrimaryKey                         @SerializedName("company")  @NonNull    @Expose     private String companyName;
    @SerializedName("coolnessScore")    @Expose     public String coolnessScore;
    @SerializedName("userNotes")        @Expose     public String notes;
    @SerializedName("id")               @Expose     private String id;
    @SerializedName("type")             @Expose     private String type;
    @SerializedName("url")              @Expose     private String url;
    @SerializedName("createdAt")        @Expose     private String createdAt;
    @SerializedName("companyUrl")       @Expose     private String companyUrl;
    @SerializedName("location")         @Expose     private String location;
    @SerializedName("title")            @Expose     private String title;
    @SerializedName("description")      @Expose     private String description;
    @SerializedName("howToApply")       @Expose     private String howToApply;
    @SerializedName("companyLogo")      @Expose     private String companyLogo;

    //public Job(){    }

    public Job(String companyName)                              {this.companyName = companyName;}

    public boolean getIsFavoriteMarked()    {return isFavoriteMarked;}
    public String getId()                   {return id;}
    public String getType()                 {return type;}
    public String getUrl()                  {return url;}
    public String getCreatedAt()            {return createdAt;}
    public String getCompanyName()          {return companyName;}
    public String getCompanyUrl()           {return companyUrl;}
    public String getLocation()             {return location;}
    public String getTitle()                {return title;}
    public String getDescription()          {return description;}
    public String getHowToApply()           {return howToApply;}
    public String getCompanyLogo()          {return companyLogo;}
    public String getNotes()                {return notes;}
    public boolean getHasApplied()          {return hasApplied;}
    public boolean getHasUserNotes()        {return hasUserNotes;}
    public boolean getHasCoolnessScore()    {return hasCoolnessScore;}
    public String getcoolnessScore()        {return coolnessScore;}


    public void setIsFavoriteMarked(boolean isFavoriteMarked)   {this.isFavoriteMarked = isFavoriteMarked;}
    public void setId(String id)                                {this.id = id;}
    public void setType(String type)                            {this.type = type;}
    public void setUrl(String url)                              {this.url = url;}
    public void setCreatedAt(String createdAt)                  {this.createdAt = createdAt;}
    public void setCompany(String company)                      {this.companyName = companyName;}
    public void setCompanyUrl(String companyUrl)                {this.companyUrl = companyUrl;}
    public void setLocation(String location)                    {this.location = location;}
    public void setTitle(String title)                          {this.title = title;}
    public void setDescription(String description)              {this.description = description;}
    public void setHowToApply(String howToApply)                {this.howToApply = howToApply;}
    public void setCompanyLogo(String companyLogo)              {this.companyLogo = companyLogo;}
    public void setCompanySearch(String companySearch)          {this.companySearch = companySearch;}
    public void setNotes(String notes)                          {this.notes = notes;}
    public void setHasApplied(boolean hasApplied)               {this.hasApplied = hasApplied;}
    public void setHasUserNotes(boolean hasUserNotes)           {this.hasUserNotes = hasUserNotes;}
    public void setHasCoolnessScore(boolean hasCoolnessScore)   {this.hasCoolnessScore = hasCoolnessScore;}
    public void setCoolnessScore(String coolnessScore)          {this.coolnessScore = coolnessScore;}


    /*
    public void update(JSONObject JSONsource) {
        try {
            this.companyName = JSONsource.getString("company");
            this.description = JSONsource.getString("description");
            this.id = JSONsource.getString("id");
            this.location = JSONsource.getString("location");
            this.companyLogo = JSONsource.getString("companyLogo");
            this.hasUserNotes = JSONsource.getBoolean("hasUserNotes");
            this.hasCoolnessScore = JSONsource.getBoolean("hasCoolnessScore");
            this.isFavoriteMarked = JSONsource.getBoolean("isFavoriteMarked");

        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }






    private String  notes;
    private String  companyName;
    private String  jobTitle;
    private String  location;
    private String  jobDescription;
    private String  coolScore;


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
*/
}
