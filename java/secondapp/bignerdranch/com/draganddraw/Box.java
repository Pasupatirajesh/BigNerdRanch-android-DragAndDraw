package secondapp.bignerdranch.com.draganddraw;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by SSubra27 on 5/24/16.
 */
public class Box implements Parcelable {

    private static final String TAG ="boxclass";
    private PointF mOrigin;
    private PointF mCurrent;
    private PointF mPointerStart;
    private PointF mPointerEnd;
    private int mPointerId;
    private float mRotation;

    public Box(PointF origin)
    {
        mOrigin = origin;
        mCurrent= origin;

    }

    public Box (Parcel input)
    {
        mOrigin =(PointF) input.readValue(PointF.class.getClassLoader());
        mCurrent =input.readParcelable(PointF.class.getClassLoader());
    }

    public PointF getCurrent()
    {
        return mCurrent;
    }
    public void setCurrent(PointF current)
    {
        mCurrent=current;
    }

    public PointF getOrigin()
    {
        return mOrigin;
    }

    public int getPointerId() {
        return mPointerId;
    }

    public void setPointerId(int pointerId) {
        mPointerId = pointerId;
    }

    public float getRotation() {
        return mRotation;
    }

    public void setRotation(float rotation) {
        mRotation = rotation;
    }

    public PointF getPointerStart() {
        return mPointerStart;
    }

    public void setPointerStart(PointF pointerStart) {
        mPointerStart = pointerStart;
    }

    public PointF getPointerEnd() {
        return mPointerEnd;
    }

    public void setPointerEnd(PointF pointerEnd) {
        mPointerEnd = pointerEnd;
    }

    public int  describeContents()
    {
        Log.i(TAG, "describe contents of the box");
        return 0;

    }
    public void writeToParcel(Parcel dest, int flags)
    {
        Log.i(TAG, "writeToParcel Box");
        dest.writeValue(mOrigin);
        dest.writeValue(mCurrent);
    }


    public static final Parcelable.Creator<Box> CREATOR
                = new Parcelable.Creator<Box>() {
            public Box createFromParcel(Parcel in) {
                Log.i(TAG, "Created from Parcel : Box");
                return new Box(in);
            }

            public Box[] newArray(int size) {
                return new Box[size];
            }
        };




}
