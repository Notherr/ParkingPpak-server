package com.luppy.parkingppak.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoTrans {
    public double inpt_x= 0;
    public double inpt_y= 0;
    public double outpt_y= 0;
    public double outpt_x= 0;

    double ind = 0;
    double src = 0;
    double dst = 0;

    double EPSLN;
    double eMajor;
    double eMinor;
    double g_scaleFactor = 1;
    double g_lonCenter = 0.0;
    double g_latCenter = 0.0;
    double g_falseNorthing = 0.0;
    double g_falseEasting = 0.0;
    double k_scaleFactor;
    double k_lonCenter;
    double k_latCenter;
    double k_falseNorthing;
    double k_falseEasting;

    double tmp;
    double es;
    double e0;
    double e1;
    double e2;
    double e3;

    //double ml0 = eMajor * mlfn(e0, e1, e2, e3, k_latCenter);

    double esp;

    public GeoTrans(double inpt_x, double inpt_y) {
        EPSLN = 0.0000000001;

        //k_scaleFactor = 0.9999;
        k_scaleFactor = 1.0;

        //k_lonCenter = 2.23402144255274;
        k_lonCenter = 2.23398304255274;

        //k_latCenter = 0.663225115757845;
        k_latCenter = 0.663272115757845;

        k_falseNorthing = 600000.0;
        k_falseEasting = 400000.0;

        //eMajor = 6378137.0;
        //eMinor = 6356752.3142;
        //eMinor = 6356752.3146581478;
        eMajor=6377397.155 ;
        eMinor=6356078.9633422494;

        tmp = eMinor / eMajor;
        es = 1.0 - tmp * tmp;
        e0 = e0fn(es);
        e1 = e1fn(es);
        e2 = e2fn(es);
        e3 = e3fn(es);
        esp = es / (1 - es);

        if ( es < 0.00001 ) {
            ind = 1.0;
        } else {
            ind = 0.0;
        }

        // Tm표(Katec표) -> 표(WGS)
        this.inpt_x = inpt_x;
        this.inpt_y = inpt_y;

        src = eMajor * mlfn(e0, e1, e2, e3, k_latCenter);
        dst = eMajor * mlfn(e0, e1, e2, e3, g_latCenter);
    }

    public List<Double> Katec2Geo() {
        int max_iter = 6;

        if ( ind != 0 ) {
            double f = Math.exp(inpt_x / (eMajor * k_scaleFactor));
            double g = 0.5 * (f - 1.0 / f);
            double temp = k_latCenter + inpt_y / (eMajor * k_scaleFactor);
            double h = Math.cos(temp);
            double con = Math.sqrt((1.0 - h * h) / (1.0 + g * g));

            outpt_y = asinz(con);

            if (temp < 0) {
                outpt_y *= -1;
            }

            if((g == 0) && (h == 0)) {
                outpt_x = k_lonCenter;
            } else {
                outpt_x = Math.atan(g / h) + k_lonCenter;
            }
        }

        inpt_x -= k_falseEasting;
        inpt_y -= k_falseNorthing;

        double con = (src + inpt_y / k_scaleFactor) / eMajor;
        double phi = con;

        int i = 0;
        while(true) {
            double deltaPhi = ((con + e1 * Math.sin(2.0 * phi)
                    - e2 * Math.sin(4.0 * phi)
                    + e3 * Math.sin(6.0 * phi))
                    / e0) - phi;
            phi = phi + deltaPhi;

            if (Math.abs(deltaPhi) <= EPSLN) {
                break;
            }
            if(i >= max_iter) {
                break;
            }
            i++;
        }

        if (Math.abs(phi) < (Math.PI / 2)) {
            double sin_phi = Math.sin(phi);
            double cos_phi = Math.cos(phi);
            double tan_phi = Math.tan(phi);
            double c = esp * cos_phi * cos_phi;
            double cs = c * c;
            double t = tan_phi * tan_phi;
            double ts = t * t;
            con = 1.0 - es * sin_phi * sin_phi;

            double n = eMajor / Math.sqrt(con);
            double r = n * (1.0 - es) / con;
            double d = inpt_x / (n * k_scaleFactor);
            double ds = d * d;

            outpt_y = phi - (n * tan_phi * ds / r)
                    * (0.5 - ds / 24.0
                    * (5.0 + 3.0 * t + 10.0 * c - 4.0 * cs - 9.0 * esp - ds / 30.0
                    * (61.0 + 90.0 * t + 298.0 * c + 45.0 * ts - 252.0 * esp - 3.0 * cs)));

            outpt_x = k_lonCenter +
                    (d * (1.0 - ds / 6.0 *
                            (1.0 + 2.0 * t + c - ds / 20.0 *
                                    (5.0 - 2.0 * c + 28.0 * t - 3.0 * cs + 8.0 * esp + 24.0 * ts))) / cos_phi);
        } else {
            outpt_y = Math.PI * 0.5 * Math.sin(inpt_y);
            outpt_x = k_lonCenter;
        }
        outpt_x = r2d(outpt_x);
        outpt_y = r2d(outpt_y);
        return new ArrayList<>(Arrays.asList(outpt_x, outpt_y));
    }

    static double e0fn(double x) {
        return 1.0 - 0.25 * x * (1.0 + x / 16.0 * (3.0 + 1.25 * x));
    }

    static double e1fn(double x) {
        return 0.375 * x * (1.0 + 0.25 * x * (1.0 + 0.46875 * x));
    }

    static double e2fn(double x) {
        return 0.05859375 * x * x * (1.0 + 0.75 * x);
    }

    static double e3fn(double x) {
        return x * x * x * (35.0 / 3072.0);
    }

    static double mlfn(double e0, double e1, double e2, double e3, double phi) {
        return e0 * phi - e1 * Math.sin(2.0 * phi) + e2 *
                Math.sin(4.0 * phi) - e3 * Math.sin(6.0 * phi);
    }

    static double asinz(double value) {
        if (Math.abs(value) > 1.0)
            value = (value > 0 ? 1 : -1);
        return Math.asin(value);
    }

    static double r2d(double radian) {
        return radian * 180.0 / Math.PI;
    }

    static double d2r(double degree) {
        return degree * Math.PI / 180.0;
    }

    static double adjust_lon(double x) {
        return x = (Math.abs(x) < Math.PI) ? x: (x-(sign(x)) * (Math.PI*2));
    }

    static double sign(double x) {
        if (x < 0.0) return (-1);
        else return (1);
    }
}
