package nieuwdorp.luuk.eftelingstats;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Luuk Nieuwdorp on 21-6-2017.
 */

public class EftelingItem {
    private String heading;
    private int pathToImage;
    private int count;
    private Context mContext;
    private int waitingTime;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public EftelingItem(String heading, Context context, int waitingTime) {
        this.heading = heading;
        this.mContext = context;
        this.waitingTime = waitingTime;

        sharedPref = context.getSharedPreferences("MyPrefs", mContext.MODE_PRIVATE);
        editor = context.getSharedPreferences("MyPrefs", mContext.MODE_PRIVATE).edit();
        if (sharedPref.getInt(heading, -2) != -2){
            this.count =  sharedPref.getInt(heading, -2);
        }

        switch(heading){
            case "Baron 1898":
                pathToImage = R.drawable.baron;
                break;
            case "De Bobbaan":
                pathToImage = R.drawable.bob;
                break;
            case "De Vliegende Hollander":
                pathToImage = R.drawable.hollander;
                break;
            case "Joris en de Draak":
                pathToImage = R.drawable.joris;
                break;
            case "PandaDroom":
                pathToImage = R.drawable.pandadroom;
                break;
            case "Python":
                pathToImage = R.drawable.python;
                break;
            case "Vogel Rok":
                pathToImage = R.drawable.vogelrok;
                break;
        }

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        editor.putInt(heading, getCount());
        editor.apply();
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public String getHeading() {
        return heading;
    }

    public int getPathToImage() {
        return pathToImage;
    }
}
