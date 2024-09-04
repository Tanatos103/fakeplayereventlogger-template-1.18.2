package com.fakeplayereventlogger.aurum;

import com.google.gson.Gson;

public class PlayerInfo {
    private final String name;
    private final String uuid;
    private final double[] coords;
    private final String action;
    private final String dimension;
    private final String tagserver;
    private final String name_player_executor;
    private final String uuid_player_executor;
    private final String reason_kill;
    // Constructor completo
    public PlayerInfo(String name, String uuid, double[] coords, String action, String dimension, String tagserver, String name_player_executor, String uuid_player_executor) 
    {
        this.name = name;
        this.uuid = uuid;
        this.coords = coords;
        this.action = action;
        this.dimension = dimension; 
        this.tagserver = tagserver;
        this.name_player_executor = name_player_executor;
        this.uuid_player_executor = uuid_player_executor;
        this.reason_kill = null;
    }

    // constructor para el caso de que el jugador muera
    public PlayerInfo(String name, String uuid, double[] coords, String action, String dimension, String tagserver, String name_player_executor, String uuid_player_executor, String reason_kill) 
    {
        this.name = name;
        this.uuid = uuid;
        this.coords = coords;
        this.action = action;
        this.dimension = dimension; 
        this.tagserver = tagserver;
        this.name_player_executor = name_player_executor;
        this.uuid_player_executor = uuid_player_executor;
        this.reason_kill = reason_kill;
    }



    public String toJson() 
    {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Getters
    public String getName() 
    {
        return name;
    }

    public String getUuid() 
    {
        return uuid;
    }

    public double[] getCoords() 
    {
        return coords;
    }

    public String getAction() 
    {
        return action;
    }

    public String getDimension() 
    {
        return dimension;
    }

    public String getTagserver() 
    {
        return tagserver;
    }

    public String getName_player_executor()
     {
        return name_player_executor;
    }

    public String getUuid_player_executor() 
    {
        return uuid_player_executor;
    }

    public String getReason_kill() 
    {
        return reason_kill;
    }
}