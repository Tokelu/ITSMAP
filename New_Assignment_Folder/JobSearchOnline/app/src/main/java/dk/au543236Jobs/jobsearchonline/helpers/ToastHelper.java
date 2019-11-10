package dk.witzell.jobsearchonline.helpers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import dk.witzell.jobsearchonline.R;

public class ToastHelper {



    public static void showToast(Context context, int stringId) {
        Toast t = Toast.makeText(context, context.getString(stringId), Toast.LENGTH_SHORT);
        View toastView = t.getView();
        toastView.setBackground(context.getResources().getDrawable(R.drawable.toast_layout));
        TextView text = toastView.findViewById(android.R.id.message);
        text.setTextColor(context.getResources().getColor(R.color.colorText));
        text.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        t.setView(toastView);
        t.show();
    }
}



