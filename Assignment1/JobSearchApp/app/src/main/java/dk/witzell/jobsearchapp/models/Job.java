package dk.witzell.jobsearchapp.models;

public class Job
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
    private Boolean status;
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
    void setAppTitle(){}
    void setCompanyName(){}
    void setJobTitle(){}
    void setCoolScore(){}
    void setLogo(){}
    void setLocation(){}
    void setJobDescription(){}
    void setNotes(){}
    void setStatus(){}
    //endregion


    public Job(String companyName, String jobTitle, String coolScore, int logo)
    {
        this.companyName    = companyName;
        this.jobTitle       = jobTitle;
        this.coolScore      = coolScore;
        this.logo           = logo;
    }





}
