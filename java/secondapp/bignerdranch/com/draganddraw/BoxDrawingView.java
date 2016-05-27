package secondapp.bignerdranch.com.draganddraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SSubra27 on 5/24/16.
 */
public class BoxDrawingView extends View {


    private static final String TAG = "BoxDrawingView";
    private static final String VIEW_STUFF = "stuff";
    private static final String BUNDLE_BOXEN = "bundle_of_box_components";

    private Box mCurrentBox;
    private Paint mBoxPaint; // Defines the characteristics shapes, whether they are filled, color, font text
    private Paint mBackgroundPaint;
    private Paint mTestPaint;
    private List<Box> mBoxen = new ArrayList<>();
    private int viewId = generateViewId();
    private int mPointerId = -1;

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(BUNDLE_BOXEN, (ArrayList<? extends Parcelable>) mBoxen);
        bundle.putParcelable(VIEW_STUFF, super.onSaveInstanceState());
        bundle.putInt("myId", this.viewId);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            this.viewId = bundle.getInt("myId");
            Parcelable superViewState = bundle.getParcelable(VIEW_STUFF);
            mBoxen = bundle.getParcelableArrayList(BUNDLE_BOXEN);
            super.onRestoreInstanceState(superViewState);
        }
    }

    // Used when creating the View in code
    public BoxDrawingView(Context context) {
        this(context, null);

    }

    // Used when creating the View from XML
    public BoxDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "The view id is :" + viewId);

        // Paint the boxes a nice semitransparent red (ARG B)

        mBoxPaint = new Paint();
        mBoxPaint.setColor(0x22ff0000);

        // Paint the background off-white

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);

        mTestPaint = new Paint();
        mTestPaint.setColor(0xff283593);
        mTestPaint.setStrokeWidth(10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        String action = "";
        int pointerIdx = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIdx);
        if (mPointerId >= 2) {
            pointerIdx = event.findPointerIndex(mPointerId);
        }

        PointF pointer = new PointF(event.getX(pointerIdx), event.getY(pointerIdx));


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                action = "ACTION_DOWN";
                mCurrentBox = new Box(current);
                mBoxen.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    invalidate();

                    if (mPointerId >= 0) {
                        mCurrentBox.setPointerEnd(pointer);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                mCurrentBox = null;
                mPointerId = -1;
                break;

            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                mCurrentBox = null;
                mPointerId = -1;
                break;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                action = "ACTION_POINTER_DOWN id = " + pointerId;
                Log.i(TAG, action);
                if (mPointerId < 0 && mCurrentBox != null) {
                    mPointerId = pointerId;
                    mCurrentBox.setPointerStart(pointer);
                    mCurrentBox.setPointerEnd(pointer);
                }
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP id = " + pointerId;
                Log.i(TAG, action);
                if (mPointerId == pointerId) {
                    mPointerId = -1;
                }
//                mCurrentBox = null;
                break;
        }

        Log.i(TAG, action + "at X=" + current.x + ", Y=" + current.y);

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Fill the background
        canvas.drawPaint(mBackgroundPaint);

        for (Box box : mBoxen) {
            float left = Math.min(box.getOrigin().x, box.getCurrent().x);
            float right = Math.max(box.getOrigin().x, box.getCurrent().x);
            float top = Math.min(box.getOrigin().y, box.getCurrent().y);
            float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

            PointF pivot = new PointF(
                    (float) ((left + right) * 0.5), (float) ((top + bottom) * 0.5));

            canvas.drawPoint(pivot.x, pivot.y, mTestPaint);

            if (box.getPointerEnd() != null && box.getPointerStart() != null) {

                PointF pS = box.getPointerStart();
                PointF pE = box.getPointerEnd();

                canvas.drawPoint(pS.x, pS.y, mTestPaint);
                canvas.drawPoint(pE.x, pE.y, mTestPaint);
// doing dot product to get angle
                double pivotPs = Math.abs(Math.sqrt(
                        (pivot.x - pS.x) * (pivot.x - pS.x) + (pivot.y - pS.y) * (pivot.y - pS.y)));
                double pivotPe = Math.abs(Math.sqrt(
                        (pivot.x - pE.x) * (pivot.x - pE.x) + (pivot.y - pE.y) * (pivot.y - pE.y)));
                double PePs = Math.abs(Math.sqrt(
                        (pE.x - pS.x) * (pE.x - pS.x) + (pE.y - pS.y) * (pE.y - pS.y)));
                double alfa =
                        (pivotPs * pivotPs + pivotPe * pivotPe - PePs * PePs)
                                / (2 * pivotPs * pivotPe);


                alfa = Math.toDegrees(Math.acos(alfa));

                canvas.drawText(String.valueOf(alfa), right, bottom, mTestPaint);

                canvas.save();

               canvas.rotate((float) alfa, pivot.x, pivot.y);


                canvas.drawRect(left, top, right, bottom, mBoxPaint);

                canvas.restore();

            } else {


                {
                    canvas.drawRect(left, top, right, bottom, mBoxPaint);
                }
            }
        }

    }
}
