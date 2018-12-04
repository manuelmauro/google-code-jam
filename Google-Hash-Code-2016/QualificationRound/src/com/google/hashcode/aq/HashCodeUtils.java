package com.google.hashcode.aq;

/**
 *
 */
public class HashCodeUtils {

    public static int distance(int r, int c, int r2, int c2) {
        double t1, t2;
        t1 = Math.pow(r - r2, 2);
        t2 = Math.pow(c - c2, 2);
        return (int) Math.ceil(Math.sqrt(t1 + t2));
    }

    public static int distanceSquared(int r, int c, int r2, int c2) {
        double t1, t2;
        t1 = Math.pow(r - r2, 2);
        t2 = Math.pow(c - c2, 2);
        return (int) (t1 + t2);
    }

    public static int orderCost(int turn, int finalTurn) {
        double a = ((float)(finalTurn - turn) / finalTurn) * 100;
//        System.out.println("order cost:" + (finalTurn - turn) / finalTurn );
        int res = (int) Math.ceil(a);
        System.out.println("order cost:" + res);
        return res;
    }
}
