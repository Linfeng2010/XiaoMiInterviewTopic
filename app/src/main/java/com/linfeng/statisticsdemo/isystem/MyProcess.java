package com.linfeng.statisticsdemo.isystem;

public class MyProcess {
    public String name;
    public long id;
    public int state;
    public long startTime;

    public MyProcess(String name, int id, int state, long startTime) {
        this.name = name;
        this.id = id;
        this.state = state;
        this.startTime = startTime;
    }

    public interface RunAction {
        int STATE_RUNNING = 1;
        int STATE_DEAD = 2;
    }
}
