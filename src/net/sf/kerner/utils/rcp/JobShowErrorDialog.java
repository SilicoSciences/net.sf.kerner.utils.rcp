package net.sf.kerner.utils.rcp;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class JobShowErrorDialog extends JobUI2 {

    private final String text;

    private final String message;

    public JobShowErrorDialog(final String text, final String message, final String pluginId) {
        super(pluginId);
        this.message = message;
        this.text = text;
    }

    @Override
    protected int getTotalWorkLoad() {
        return IProgressMonitor.UNKNOWN;
    }

    @Override
    protected IStatus runInUIThread2(final IProgressMonitor monitor) throws Exception {
        final MessageBox dialog = new MessageBox(Display.getCurrent().getActiveShell(),
                SWT.ICON_ERROR | SWT.OK);
        dialog.setText(text);
        dialog.setMessage(message);
        dialog.open();
        return Status.OK_STATUS;
    }

}
