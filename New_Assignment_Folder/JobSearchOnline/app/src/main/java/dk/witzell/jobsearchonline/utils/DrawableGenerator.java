package dk.witzell.jobsearchonline.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import dk.witzell.jobsearchonline.R;
import dk.witzell.jobsearchonline.models.Job;


public class DrawableGenerator
{
    private Context context;

    public DrawableGenerator(Context context) { this.context = context; }

    public Drawable getDrawableByName(Job currentJob)
    {
        String companyNames = currentJob.getCompanyName();

        return (companyNames.contains("Avochato")       ? context.getResources().getDrawable(R.drawable.avochato) :
                companyNames.contains("Chatterbug")     ? context.getResources().getDrawable(R.drawable.chatterbug) :
                companyNames.contains("Direct")         ? context.getResources().getDrawable(R.drawable.directsupply) :
                companyNames.contains("Favor")          ? context.getResources().getDrawable(R.drawable.favor) :
                companyNames.contains("Inno")           ? context.getResources().getDrawable(R.drawable.innogames) :
                companyNames.contains("King")           ? context.getResources().getDrawable(R.drawable.king) :
                companyNames.contains("Mindhive")       ? context.getResources().getDrawable(R.drawable.mindhive) :
                companyNames.contains("Mojo")           ? context.getResources().getDrawable(R.drawable.mojotech) :
                companyNames.contains("Pace")           ? context.getResources().getDrawable(R.drawable.pace) :
                companyNames.contains("Pulsara")        ? context.getResources().getDrawable(R.drawable.pulsara) :
                companyNames.contains("Snapfish")       ? context.getResources().getDrawable(R.drawable.snapfish) :
                null
        );
    }
}
