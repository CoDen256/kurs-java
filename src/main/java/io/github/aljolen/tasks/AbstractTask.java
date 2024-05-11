package io.github.aljolen.tasks;

import io.github.aljolen.data.Config;
import io.github.aljolen.data.SharedResources;
import io.github.aljolen.data.SyncPoints;
import java.util.concurrent.BrokenBarrierException;

public abstract class AbstractTask extends Thread{

    protected final Config config;
    protected final SharedResources r;
    protected final SyncPoints s;

    public AbstractTask(Config config, SharedResources r, SyncPoints sync) {
        this.config = config;
        this.r = r;
        this.s = sync;
    }

    @Override
    public void run() {
        try {
            tryRun();
        } catch (InterruptedException | BrokenBarrierException ex) {
            throw new RuntimeException(ex);
        }
    }

    protected abstract void tryRun() throws InterruptedException, BrokenBarrierException;
}
