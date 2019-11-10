package dk.au543236Jobs.jobsearchonline.models;



import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@Entity
public class Job implements Parcelable
{
//    private boolean hasCoolnessScore;
//    private boolean hasUserNotes;

//    public String companySearch = "company";

    @PrimaryKey     @SerializedName("id")   @Expose @NonNull                            private String id;
    @SerializedName("company")              @Expose @ColumnInfo(name = "company")       private String companyName;
    @SerializedName("type")                 @Expose @ColumnInfo(name = "type")          private String type;
    @SerializedName("url")                  @Expose @ColumnInfo(name = "url")           private String url;
    @SerializedName("created_at")           @Expose @ColumnInfo(name = "created_at")    private String createdAt;
    @SerializedName("company_url")          @Expose @ColumnInfo(name = "company_url")   private String companyUrl;
    @SerializedName("location")             @Expose @ColumnInfo(name = "location")      private String location;
    @SerializedName("title")                @Expose @ColumnInfo(name = "title")         private String title;
    @SerializedName("description")          @Expose @ColumnInfo(name = "description")   private String description;
    @SerializedName("how_to_apply")         @Expose @ColumnInfo(name = "how_to_apply")  private String howToApply;
    @SerializedName("company_logo")         @Expose @ColumnInfo(name = "company_logo")  private String companyLogo;

    @ColumnInfo(name = "position")                                                      private int position;
    @ColumnInfo(name = "coolness_score")                                                private String coolnessScore;
    @ColumnInfo(name = "user_notes")                                                    private String notes;
    @ColumnInfo(name = "is_marked_favorite")                                            private boolean isFavoriteMarked;
    @ColumnInfo(name = "is_marked_applied")                                             private boolean isMarkedApplied;
    @ColumnInfo(name = "has_coolness_score")                                            private boolean hasCoolnessScore;
    @ColumnInfo(name = "has_user_notes")                                                private boolean hasUserNotes;




    public Job() {
    }

    public Job(Parcel input) {
        id                  = input.readString();
        companyName         = input.readString();
        description         = input.readString();
        location            = input.readString();
        notes               = input.readString();
        coolnessScore       = input.readString();
        title               = input.readString();
        companyLogo         = input.readString();
        isFavoriteMarked    = input.readInt() == 1;
        isMarkedApplied     = input.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags){
        destination.writeString(id);
        destination.writeString(getCompanyName());
        destination.writeString(getDescription());
        destination.writeString(getNotes());
        destination.writeString(getLocation());
        destination.writeString(getCoolnessScore());
        destination.writeString(getTitle());
        destination.writeString(getCompanyLogo());
        destination.writeInt(getIsMarkedApplied() ? 1 :0 );
        destination.writeInt(getIsFavoriteMarked() ? 1: 0);
    }

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
    public String getCoolnessScore()        {return coolnessScore;}
    public boolean getIsMarkedApplied()     {return isMarkedApplied;}
    public boolean getHasUserNotes()        {return hasUserNotes;}
    public boolean getHasCoolnessScore()    {return hasCoolnessScore;}
    public int     getPosition()            {return position;}


    public void setIsFavoriteMarked(boolean isFavoriteMarked)   {this.isFavoriteMarked = isFavoriteMarked;}
    public void setId(String id)                                {this.id = id;}
    public void setType(String type)                            {this.type = type;}
    public void setUrl(String url)                              {this.url = url;}
    public void setCreatedAt(String createdAt)                  {this.createdAt = createdAt;}
    public void setCompanyName(String companyName)              {this.companyName = companyName;}
    public void setCompanyUrl(String companyUrl)                {this.companyUrl = companyUrl;}
    public void setLocation(String location)                    {this.location = location;}
    public void setTitle(String title)                          {this.title = title;}
    public void setDescription(String description)              {this.description = description;}
    public void setHowToApply(String howToApply)                {this.howToApply = howToApply;}
    public void setCompanyLogo(String companyLogo)              {this.companyLogo = companyLogo;}
//    public void setCompanySearch(String companySearch)          {this.companySearch = companySearch;}
    public void setNotes(String notes)                          {this.notes = notes;}
    public void setIsMarkedApplied(boolean isMarkedApplied)     {this.isMarkedApplied = isMarkedApplied;}
    public void setHasUserNotes(boolean hasUserNotes)           {this.hasUserNotes = hasUserNotes;}
    public void setHasCoolnessScore(boolean hasCoolnessScore)   {this.hasCoolnessScore = hasCoolnessScore;}
    public void setCoolnessScore(String coolnessScore)          {this.coolnessScore = coolnessScore;}
    public void setPosition(int position)                       {this.position = position;}

    @Override
    public boolean equals(Object job) {return job instanceof Job && id.equals(((Job) job).id);}

    @Override
    public int describeContents() {return 0;}

    public static final Parcelable.Creator<Job> CREATOR = new Parcelable.Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };
}
