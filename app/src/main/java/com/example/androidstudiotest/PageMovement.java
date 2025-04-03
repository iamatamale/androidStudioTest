package com.example.androidstudiotest;

import android.app.Activity;
import android.widget.Button;
import android.content.Intent;


public class PageMovement {
        public static void pageMoving(Activity activity, int buttonId, Class<?> destination){
                Button button = activity.findViewById(buttonId);
                button.setOnClickListener(v -> activity.startActivity(new Intent(activity, destination)));
        }
}
