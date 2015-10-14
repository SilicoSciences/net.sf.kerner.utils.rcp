/*******************************************************************************
 * Copyright (c) 2015 Alexander Kerner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Alexander Kerner - initial API and implementation
 ******************************************************************************/
package net.sf.kerner.utils.rcp;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

public abstract class ViewProtoListener extends ViewProto implements IPartListener {

    protected IWorkbenchPart activePart;

    protected Job runningJob;

    public ViewProtoListener() {
    }

    @Override
    public void createPartControl(final Composite parent) {
        super.createPartControl(parent);
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addPartListener(this);
    }

    @Override
    public void dispose() {
        activePart = null;
        super.dispose();
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .removePartListener(this);
    }

    public Job getActiveJob() {
        return runningJob;
    }

    protected boolean isVisible() {
        if (activePart == null) {
            return false;
        }
        return UtilRCP.isVisible(this, activePart.getSite());
    }

    @Override
    public void partActivated(final IWorkbenchPart part) {
        activePart = part;
    }

    @Override
    public void partBroughtToTop(final IWorkbenchPart part) {

    }

    @Override
    public void partClosed(final IWorkbenchPart part) {

    }

    @Override
    public void partDeactivated(final IWorkbenchPart part) {

    }

    @Override
    public void partOpened(final IWorkbenchPart part) {

    }

    public void setActiveJob(final Job job) {
        if (runningJob != null) {
            runningJob.cancel();
        }
        runningJob = job;
    }

}
