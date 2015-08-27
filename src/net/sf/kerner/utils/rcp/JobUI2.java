package net.sf.kerner.utils.rcp;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.progress.UIJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JobUI2 extends UIJob {

    private final static Logger log = LoggerFactory.getLogger(JobUI2.class);

    private final String pluginId;

    public JobUI2(final String pluginId) {
        super("update UI");
        this.pluginId = pluginId;
        setPriority(DECORATE);
        setUser(false);
        setSystem(true);
    }

    public JobUI2(final String pluginId, final int priority) {
        super("update UI");
        this.pluginId = pluginId;
        setPriority(priority);
        setUser(false);
        setSystem(true);
    }

    protected int getTotalWorkLoad() {
        return IProgressMonitor.UNKNOWN;
    };

    @Override
    public final IStatus runInUIThread(final IProgressMonitor monitor) {
        try {
            if (monitor != null)
                monitor.beginTask(getName(), getTotalWorkLoad());
            return runInUIThread2(monitor);
        } catch (final Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getLocalizedMessage(), e);
            }
            return new Status(Status.ERROR, pluginId, e.getLocalizedMessage());
        } finally {
            if (monitor != null)
                monitor.done();
        }
    }

    protected abstract IStatus runInUIThread2(IProgressMonitor monitor) throws Exception;

}
