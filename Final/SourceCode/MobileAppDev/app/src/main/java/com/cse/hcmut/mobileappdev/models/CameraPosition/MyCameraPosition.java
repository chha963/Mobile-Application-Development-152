package com.cse.hcmut.mobileappdev.models.CameraPosition;

import java.io.Serializable;

/**
 * Created by dinhn on 4/4/2016.
 */
public class MyCameraPosition implements Serializable {
    double mLongitude = 0.0;
    double mLatitude = 0.0;
    float  mZoom = 0.0f;
    float  mBearing = 0.0f;
    float  mTilt = 0.0f;

    public MyCameraPosition(double longitude, double latitude, float zoom, float bearing, float tilt) {
        mLongitude = longitude;
        mLatitude = latitude;
        mZoom = zoom;
        mBearing = bearing;
        mTilt = tilt;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public float getZoom() {
        return mZoom;
    }

    public void setZoom(float zoom) {
        mZoom = zoom;
    }

    public float getBearing() {
        return mBearing;
    }

    public void setBearing(float bearing) {
        mBearing = bearing;
    }

    public float getTilt() {
        return mTilt;
    }

    public void setTilt(float tilt) {
        mTilt = tilt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyCameraPosition that = (MyCameraPosition) o;

        if (Double.compare(that.mLongitude, mLongitude) != 0) return false;
        if (Double.compare(that.mLatitude, mLatitude) != 0) return false;
        if (Float.compare(that.mZoom, mZoom) != 0) return false;
        if (Float.compare(that.mBearing, mBearing) != 0) return false;
        return Float.compare(that.mTilt, mTilt) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(mLongitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLatitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mZoom != 0.0f ? Float.floatToIntBits(mZoom) : 0);
        result = 31 * result + (mBearing != 0.0f ? Float.floatToIntBits(mBearing) : 0);
        result = 31 * result + (mTilt != 0.0f ? Float.floatToIntBits(mTilt) : 0);
        return result;
    }
}
