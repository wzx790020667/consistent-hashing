package com.company.consistenthashing;

public class Server {
    private String ip;
    private String name;

    public Server(String ip, String name) {
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getServerName() {
        return name;
    }
}
