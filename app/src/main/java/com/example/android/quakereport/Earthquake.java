package com.example.android.quakereport;

public class Earthquake {
    private String mMagnitude;
    private String mLocationOffset;
    private String mPrimaryLocation;
    private String mDate;
    private String mTime;
    private String mUrl;

    public Earthquake(String Magnitude,String LocationOffset,String PrimaryLocation,String Date,String Time,String url)
    {
        mMagnitude=Magnitude;
        mLocationOffset= LocationOffset;
        mPrimaryLocation=PrimaryLocation;
        mDate=Date;
        mTime=Time;
        mUrl=url;

    }

    public String getmDate() {
        return mDate;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocationOffset() {
        return mLocationOffset;
    }

    public String getmPrimaryLocation() {
        return mPrimaryLocation;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmUrl() {
        return mUrl;
    }
}
