package net.sf.kerner.utils.rcp;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Job2 extends Job {

    private final static Logger log = LoggerFactory.getLogger(Job2.class);

    private final String pluginId;

    public Job2(final String name, final String pluginId) {
        super(name);
        this.pluginId = pluginId;
    }

    protected int getTotalWorkLoad() {
        return IProgressMonitor.UNKNOWN;
    }

    @Override
    protected final IStatus run(final IProgressMonitor monitor) {
        monitor.beginTask(getName(), getTotalWorkLoad());
        try {
            return run2(monitor);
        } catch (final Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getLocalizedMessage(), e);
            }
            return new Status(Status.ERROR, pluginId, e.getLocalizedMessage(), e);
        } finally {
            monitor.done();
        }
    }

    protected abstract IStatus run2(IProgressMonitor monitor) throws Exception;

}
