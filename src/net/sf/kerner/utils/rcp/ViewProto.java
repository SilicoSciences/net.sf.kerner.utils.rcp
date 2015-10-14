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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public abstract class ViewProto extends ViewPart {

    protected Composite parent;

    public ViewProto() {
        // all instances and child instances must be stateless since
        // instantiation is uncertain
    }

    @Override
    public void createPartControl(final Composite parent) {
        this.parent = parent;
    }

}
