package com.bergamota.jasperreports.executable;

public class BasePathSingleton {
    private static BasePathSingleton instance;

    private String basePath;

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }

    public static BasePathSingleton getInstance() {
        if(instance == null)
            instance = new BasePathSingleton();

        return instance;
    }
}
