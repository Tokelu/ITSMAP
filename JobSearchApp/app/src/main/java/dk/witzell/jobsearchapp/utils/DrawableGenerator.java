package dk.witzell.jobsearchapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import dk.witzell.jobsearchapp.R;
import dk.witzell.jobsearchapp.models.Job;

public class DrawableGenerator
{
    private Context context;

    public DrawableGenerator(Context context) { this.context = context; }

    public Drawable getDrawableByName(Job currentJob)
    {
        String companyNames = currentJob.getCompanyName();

        if (companyNames.contains("avochato"))      { return context.getResources().getDrawable(R.drawable.avochato); }
        if (companyNames.contains("chatterbug"))    { return context.getResources().getDrawable(R.drawable.chatterbug); }
        if (companyNames.contains("directsupply"))  { return context.getResources().getDrawable(R.drawable.directsupply); }
        if (companyNames.contains("favor"))         { return context.getResources().getDrawable(R.drawable.favor); }
        if (companyNames.contains("innogames"))     { return context.getResources().getDrawable(R.drawable.innogames); }
        if (companyNames.contains("king"))          { return context.getResources().getDrawable(R.drawable.king); }
        if (companyNames.contains("mindhive"))      { return context.getResources().getDrawable(R.drawable.mindhive); }
        if (companyNames.contains("mojotech"))      { return context.getResources().getDrawable(R.drawable.mojotech); }
        if (companyNames.contains("pace"))          { return context.getResources().getDrawable(R.drawable.pace); }
        if (companyNames.contains("pulsara"))       { return context.getResources().getDrawable(R.drawable.pulsara); }
        if (companyNames.contains("snapfish"))      { return context.getResources().getDrawable(R.drawable.snapfish); }

        return context.getResources().getDrawable(R.drawable.snapfish);



        /*return (companyNames.contains("avochato")       ? context.getResources().getDrawable(R.drawable.avochato) :
                companyNames.contains("chatterbug")     ? context.getResources().getDrawable(R.drawable.chatterbug) :
                companyNames.contains("directsupply")   ? context.getResources().getDrawable(R.drawable.directsupply) :
                companyNames.contains("favor")          ? context.getResources().getDrawable(R.drawable.favor) :
                companyNames.contains("innogames")      ? context.getResources().getDrawable(R.drawable.innogames) :
                companyNames.contains("king")           ? context.getResources().getDrawable(R.drawable.king) :
                companyNames.contains("mindhive")       ? context.getResources().getDrawable(R.drawable.mindhive) :
                companyNames.contains("mojotech")       ? context.getResources().getDrawable(R.drawable.mojotech) :
                companyNames.contains("pace")           ? context.getResources().getDrawable(R.drawable.pace) :
                companyNames.contains("pulsara")        ? context.getResources().getDrawable(R.drawable.pulsara) :
                companyNames.contains("snapfish")       ? context.getResources().getDrawable(R.drawable.snapfish) :
                null
                );*/
    }
}
