package net.sf.kerner.utils.rcp;

import net.sf.kerner.utils.progress.ProgressMonitor;

import org.eclipse.core.runtime.IProgressMonitor;

public class ProgressMonitorAdapter implements ProgressMonitor {

    private final IProgressMonitor monitor;
    private String name;

    public ProgressMonitorAdapter(final IProgressMonitor monitor) {
        if (monitor == null) {
            throw new NullPointerException();
        }
        this.monitor = monitor;
    }

    @Override
    public synchronized void finished() {
        monitor.done();
    }

    public synchronized String getName() {
        return name;
    }

    @Override
    public synchronized boolean isCancelled() {
        return monitor.isCanceled();
    }

    @Override
    public synchronized void notifySubtask(final String name) {
        monitor.subTask(name);

    }

    @Override
    public synchronized void setCancelled(final boolean cancelled) {
        monitor.setCanceled(cancelled);
    }

    public synchronized void setName(final String name) {
        this.name = name;
    }

    @Override
    public synchronized void setTaskName(final String name) {
        monitor.setTaskName(name);
    }

    @Override
    public synchronized void started(final int totalWorkload) {
        monitor.beginTask(getName(), totalWorkload);
    }

    @Override
    public synchronized void worked() {
        monitor.worked(1);
    }

    @Override
    public synchronized void worked(final int i) {
        monitor.worked(i);
    }
}
