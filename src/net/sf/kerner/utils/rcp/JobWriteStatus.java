package net.sf.kerner.utils.rcp;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

public class JobWriteStatus extends JobUI2 {

    private final String msg;

    public JobWriteStatus(final String msg, final String pluginId) {
        super(pluginId);
        this.msg = msg;
    }

    @Override
    protected int getTotalWorkLoad() {
        return IProgressMonitor.UNKNOWN;
    }

    @Override
    protected IStatus runInUIThread2(final IProgressMonitor monitor) throws Exception {
        UtilRCP.getStatusLineForCurrentView().setMessage(msg);
        return Status.OK_STATUS;
    }
}
