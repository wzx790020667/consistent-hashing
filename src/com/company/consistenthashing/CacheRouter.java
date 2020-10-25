package com.company.consistenthashing;

import java.util.*;

/**
 * Cache router by using consistent hashing algorithm
 */
public class CacheRouter {
    // Number of virtual nodes
    private static int NUM_OF_V_NODES = 150;
    // Servers to be added to hash circle
    private static ArrayList<Server> servers = new ArrayList<Server>();
    private SortedMap<Integer, Server> realNodes = new TreeMap<>();
    private SortedMap<Integer, String> virtualNodes = new TreeMap<>();

    static {
        servers.add(new Server("192.168.1.1", "server01"));
        servers.add(new Server("192.168.1.2", "server02"));
        servers.add(new Server("192.168.1.3", "server03"));
        servers.add(new Server("192.168.1.4", "server04"));
    }

    public CacheRouter() {
        initServers();
    }

    private void initServers() {
        servers.forEach(this::addServer);
    }

    public void addServer(Server newServer) {
        // Add the new server to real server list
        int realHashKey = getHash(newServer.getServerName());
        realNodes.put(realHashKey, newServer);
        System.out.println("Real server " + newServer.getServerName() + " added, hash key: " + realHashKey);
        // Add virtual nodes for this new server
        for (int i = 0; i < NUM_OF_V_NODES; i++) {
            String virtualName = (newServer.getServerName() + "#vn" + String.valueOf(i) + Math.random());
            int virtualHashKey = getHash(virtualName);
            virtualNodes.put(virtualHashKey, virtualName);
            System.out.println("Virtual node " + virtualName + " added, virtual hash key: " + virtualHashKey);
        }
    }

    /**
     * @param reqData - Server request
     * */
    public Server getRouteServer(String reqData) {
        int reqDataHash = getHash(reqData);
        SortedMap<Integer, String> clockwiseServers = virtualNodes.tailMap(reqDataHash);
        System.out.println(clockwiseServers.isEmpty() + " req data hash " + reqDataHash);
        int vServerKey = clockwiseServers.isEmpty() ? realNodes.firstKey() : clockwiseServers.firstKey();
        String vNodeName = virtualNodes.get(vServerKey);
        String rNodeName = vNodeName.substring(0, vNodeName.indexOf("#vn"));
        return realNodes.get(getHash(rNodeName));
    }

    private int getHash(String str) {
        int hash = str.hashCode();
        return hash > 0 ? hash : Math.abs(hash);
    }

}
