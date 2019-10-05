package com.android.nishchalraj.passwordstrength;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by nishchalraj on 10.10.2018.
 */

public class PasswordStrengthBar extends LinearLayout{

    //UI
    protected LinearLayout plb;
    protected ProgressBar pb1;
    protected ProgressBar pb2;
    protected ProgressBar pb3;
    protected ProgressBar pb4;

    protected LayoutInflater mInflater;

    //Attributes
    private int mMax = 100;
    private int mMin = 0;
    int mNoStrengthColor = Color.LTGRAY;
    int mStrengthColor1 = Color.RED;
    int mStrengthColor2 = Color.YELLOW;
    int mStrengthColor3 = Color.GREEN;
    int mStrengthColor4 = Color.DKGRAY;

    public PasswordStrengthBar(Context context) {
        super(context,null);
        init(context);
    }

    public PasswordStrengthBar(Context context, AttributeSet attrs) {
        super(context, attrs,0);
        init(context);
    }

    public PasswordStrengthBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**initiate views*/
    protected void init(Context context){
        mInflater = LayoutInflater.from(context);

        //load view from xml
        View view = mInflater.inflate(R.layout.password_strength_bar,this,true);

        //init UI
        plb = view.findViewById(R.id.progressLinearBar);
        pb1 = view.findViewById(R.id.pBar1);
        pb2 = view.findViewById(R.id.pBar2);
        pb3 = view.findViewById(R.id.pBar3);
        pb4 = view.findViewById(R.id.pBar4);
        setMaxStrength(mMax);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setMinStrength(mMin);
        }

    }

    /**
     * Implement the below method to set the password strength to default chosen color
     */
    public void setStrengthColor(int noStrengthColor, int color1, int color2, int color3, int color4){

        this.mNoStrengthColor = noStrengthColor;
        this.mStrengthColor1 = color1;
        this.mStrengthColor2 = color2;
        this.mStrengthColor3 = color3;
        this.mStrengthColor4 = color4;

        Drawable backgroundDr = new ColorDrawable(noStrengthColor);

        //color layers for password bar 1
        Drawable progressDr1 = new ScaleDrawable(new ColorDrawable(color1), Gravity.START, 1, -1);
        LayerDrawable resultDr1 = new LayerDrawable(new Drawable[] { backgroundDr, progressDr1 });
        pb1.setProgressDrawable(resultDr1);

        //color layers for password bar 2
        Drawable progressDr2 = new ScaleDrawable(new ColorDrawable(color2), Gravity.START, 1, -1);
        LayerDrawable resultDr2 = new LayerDrawable(new Drawable[] { backgroundDr, progressDr2 });
        pb2.setProgressDrawable(resultDr2);

        //color layers for password bar 3
        Drawable progressDr3 = new ScaleDrawable(new ColorDrawable(color3), Gravity.START, 1, -1);
        LayerDrawable resultDr3 = new LayerDrawable(new Drawable[] { backgroundDr, progressDr3 });
        pb3.setProgressDrawable(resultDr3);

        //color layers for password bar 4
        Drawable progressDr4 = new ScaleDrawable(new ColorDrawable(color4), Gravity.START, 1, -1);
        LayerDrawable resultDr4 = new LayerDrawable(new Drawable[] { backgroundDr, progressDr4 });
        pb4.setProgressDrawable(resultDr4);

    }


    /**
     * Implement the below two methods to get the maximum
     * and minimum value to which the password strength can be calculated
     */
    public int getMaxStrength(){
        return mMax;
    }
    public int getMinStrength(){
        return mMin;
    }


    /**
     * implement the below two methods to set the maximum
     * and minimum value to which the password strength can
     * be calculated
     */
    public void setMaxStrength(int max){
        this.mMax = max;

        max /= 4;
        pb1.setMax(max);
        pb2.setMax(max);
        pb3.setMax(max);
        pb4.setMax(max);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setMinStrength(int min){
        this.mMin = min;

        min /=4;
        pb1.setMin(min);
        pb2.setMin(min);
        pb3.setMin(min);
        pb4.setMin(min);
    }


    /**
     * implement the below method to get the strength of the bar
     */
    public int getStrength(){
        return pb1.getProgress()+pb2.getProgress()+pb3.getProgress()+pb4.getProgress() ;
    }


    /**
     * Don't want complex ways to set the strength then use simple calculations
     * Below method to set the strength of the bar
     */
    public void setStrength(int strength){
        if(strength <= mMin){
            //set all the progress bar to its minimum value
            pb1.setProgress(mMin/4);
            pb2.setProgress(mMin/4);
            pb3.setProgress(mMin/4);
            pb4.setProgress(mMin/4);
        }
        else if(strength >= mMax){
            //set all the progress bar to its maximum value
            pb1.setProgress(mMax/4);
            pb2.setProgress(mMax/4);
            pb3.setProgress(mMax/4);
            pb4.setProgress(mMax/4);
        }
        else{
            //set the progress bar accordingly
            if(strength-mMax/4 >= mMin/4 ){
                pb1.setProgress(mMax/4);
                pb2.setProgress(mMin);
                pb3.setProgress(mMin);
                pb4.setProgress(mMin);
                strength -= (mMax/4);
                if(strength-mMax/4 >= mMin/4 ){
                    pb2.setProgress(mMax/4);
                    pb3.setProgress(mMin);
                    pb4.setProgress(mMin);
                    strength -= (mMax/4);
                    if(strength-mMax/4 >= mMin/4 ){
                        pb3.setProgress(mMax/4);
                        pb4.setProgress(mMin);
                        strength -= (mMax/4);
                        if(strength-mMax/4 >= mMin/4 ){
                            pb4.setProgress(mMax/4);
                        }else{
                            pb4.setProgress(strength);
                        }
                    }else{
                        pb3.setProgress(strength);
                        pb4.setProgress(mMin);
                    }
                }else{
                    pb2.setProgress(strength);
                    pb3.setProgress(mMin);
                    pb4.setProgress(mMin);
                }
            }else{
                pb1.setProgress(strength);
                pb2.setProgress(mMin);
                pb3.setProgress(mMin);
                pb4.setProgress(mMin);
            }
        }
    }
}
