package com.yiwen.mobike.bean;

import android.os.Parcel;
import android.os.Parcelable;



public class RoutePoint implements Parcelable {

    public int id;
    double routeLat, routeLng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRouteLat() {
        return routeLat;
    }

    public void setRouteLat(double routeLat) {
        this.routeLat = routeLat;
    }

    public double getRouteLng() {
        return routeLng;
    }

    public void setRouteLng(double routeLng) {
        this.routeLng = routeLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(routeLat);
        parcel.writeDouble(routeLng);
        parcel.writeInt(id);

    }

    public static final Creator<RoutePoint> CREATOR = new Creator<RoutePoint>() {
        @Override
        public RoutePoint createFromParcel(Parcel source) {
            RoutePoint routePoint = new RoutePoint();
            routePoint.id = source.readInt();
            routePoint.routeLat = source.readDouble();
            routePoint.routeLng = source.readDouble();
            return routePoint;
        }

        @Override
        public RoutePoint[] newArray(int size) {

            return new RoutePoint[size];
        }
    };
}
