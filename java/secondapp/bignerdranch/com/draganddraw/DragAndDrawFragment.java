package secondapp.bignerdranch.com.draganddraw;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SSubra27 on 5/23/16.
 */
public class DragAndDrawFragment extends Fragment {

    public static DragAndDrawFragment newInstance()
    {
        return new DragAndDrawFragment();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = layoutInflater.inflate(R.layout.drag_and_draw_fragment, container, false);
        return v;
    }
}
