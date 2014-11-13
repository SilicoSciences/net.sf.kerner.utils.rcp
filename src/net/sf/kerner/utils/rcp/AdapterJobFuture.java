package net.sf.kerner.utils.rcp;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sf.kerner.utils.Util;

import org.eclipse.core.runtime.jobs.Job;

public class AdapterJobFuture<T> implements Future<T> {

    protected final JobResult<? extends T> job;

    private boolean isCanceled;

    public AdapterJobFuture(final JobResult<? extends T> job) {
        this.job = job;
        Util.checkForNull(job);
    }

    @Override
    public synchronized boolean cancel(final boolean mayInterruptIfRunning) {
        isCanceled = true;
        return job.cancel();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        // if (job.getState() == Job.NONE) {
        // synchronized (lock) {
        // lock.wait();
        // }
        // }
        job.join();
        final T result = job.getResultObject();
        if (result == null) {
            final int i = 0;
        }
        return result;
    }

    @Override
    public T get(final long timeout, final TimeUnit unit) throws InterruptedException,
            ExecutionException, TimeoutException {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean isCancelled() {
        return isCanceled;
    }

    @Override
    public synchronized boolean isDone() {
        return job.getState() != Job.RUNNING && job.getState() != Job.WAITING;
    }

}
