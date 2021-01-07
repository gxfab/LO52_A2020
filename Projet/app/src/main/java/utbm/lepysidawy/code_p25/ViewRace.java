package utbm.lepysidawy.code_p25;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import utbm.lepysidawy.code_p25.entity.Race;

/**
 * Specific layout for a race, used for the history activity
 */
public class ViewRace extends LinearLayout implements View.OnClickListener {

    private Race race;

    public Race getRace(){
        return race;
    }

    public ViewRace(Context context, @Nullable AttributeSet attrs)
    {
        super(context,attrs);
        init(attrs,context);
    }

    public ViewRace(Context context, Race race, @Nullable AttributeSet attrs)
    {
        super(context,attrs);
        this.race = race;
        init(attrs,context);
    }

    public ViewRace(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        init(attrs,context);

    }

    /**
     * Initialization of the view
     * @param set
     * @param context
     */
    private void init(@Nullable AttributeSet set, final Context context){

        Typeface face = Typeface.create("monospace", Typeface.NORMAL);
        LinearLayout globalLayout = new LinearLayout(getContext());
        globalLayout.setPadding(0,0,0,20);
        globalLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        globalLayout.setGravity(Gravity.CENTER);
        globalLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView tvRaceName = new TextView(new ContextThemeWrapper(getContext(), R.style.textView), null, 0);
        tvRaceName.setTextAppearance(R.style.textView);
        tvRaceName.setLayoutParams(new LinearLayout.LayoutParams(600, LinearLayout.LayoutParams.MATCH_PARENT));
        tvRaceName.setText(race.getName());
        tvRaceName.setGravity(Gravity.CENTER_VERTICAL);
        tvRaceName.setTypeface(face);
        globalLayout.addView(tvRaceName);

        Button btnRace = new Button(new ContextThemeWrapper(getContext(), R.style.mainActivityButton), null, 0);
        btnRace.setTextAppearance(R.style.mainActivityButton);
        btnRace.setLayoutParams(new LinearLayout.LayoutParams(450, 75));
        btnRace.setText(R.string.course_Stats);
        btnRace.setOnClickListener(this);
        btnRace.setTag(race.getIdRace());
        btnRace.setTypeface(face);
        globalLayout.addView(btnRace);

        addView(globalLayout);
    }

    /**
     * OnClick method of the view
     * @param v
     */
    public void onClick(View v) {
        int raceId = Integer.parseInt(v.getTag().toString());
        Intent intent = new Intent(getContext(), StatsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle b = new Bundle();
        b.putInt("raceId", raceId);
        intent.putExtras(b);
        getContext().startActivity(intent);
    }

}
