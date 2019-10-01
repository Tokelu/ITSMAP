package dk.witzell.jobsearchapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import dk.witzell.jobsearchapp.R;
import dk.witzell.jobsearchapp.models.Job;

public class DrawableGenerator
{
    private Context context;

    public DrawableGenerator(Context context) {this.context = context;}

    public Drawable getDrawableByName(Job currentJob)
    {
        String companyNames = currentJob.getCompanyName();
        return (companyNames.contains("avochato")       ? context.getResources().getDrawable(R.drawable.avochato) :
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
                );
    }
}
