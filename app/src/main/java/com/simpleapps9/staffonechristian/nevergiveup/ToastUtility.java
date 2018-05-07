package com.simpleapps9.staffonechristian.nevergiveup;

import android.content.Context;
import android.widget.Toast;



/**
 * Created by staffonechristian on 2018-05-03.
 */

public class ToastUtility {

    public static void ToastCreateLong(Context context,String message) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void ToastCreateShort(Context context,String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

}
