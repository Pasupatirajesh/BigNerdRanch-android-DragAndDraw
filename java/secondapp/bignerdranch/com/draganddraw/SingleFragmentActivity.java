package secondapp.bignerdranch.com.draganddraw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by SSubra27 on 5/23/16.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity
{
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_draw);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.drag_and_draw_activity);
        if(fragment == null)
        {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.drag_and_draw_activity, fragment).commit();
        }
    }

}
