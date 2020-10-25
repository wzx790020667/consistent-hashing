package com.company.consistenthashing;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        CacheRouter cacheRouter = new CacheRouter();

        System.out.println(cacheRouter.getRouteServer("hkjewq").getIp());
        System.out.println(cacheRouter.getRouteServer("fjjj").getIp());
        System.out.println(cacheRouter.getRouteServer("w321").getIp());
        System.out.println(cacheRouter.getRouteServer("888").getIp());

    }
}
