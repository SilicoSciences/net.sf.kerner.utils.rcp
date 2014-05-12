/**
 * Copyright 2009 Diamond Light Source Ltd.
 *
 * This file is part of GDA.
 *
 * GDA is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License version 3 as published by the Free
 * Software Foundation.
 *
 * GDA is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along
 * with GDA. If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.kerner.utils.rcp;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class LabelFieldEditor extends FieldEditor {

    private static int counter = 0;

    private Label label;

    public LabelFieldEditor(final String labelText, final Composite parent) {
        init("LabelFieldEditor" + counter, labelText);
        counter++;
        createControl(parent);
    }

    @Override
    protected void adjustForNumColumns(final int numColumns) {
        final GridData gd = (GridData) label.getLayoutData();
        gd.horizontalSpan = numColumns;
    }

    @Override
    protected void doFillIntoGrid(final Composite parent, final int numColumns) {
        // the label is actually created by the superclass, we just need
        // to modify it here so that we can set numColumns later
        label = getLabelControl(parent);
        label.setLayoutData(new GridData());
    }

    @Override
    protected void doLoad() {
    }

    @Override
    protected void doLoadDefault() {
    }

    @Override
    protected void doStore() {
    }

    @Override
    public int getNumberOfControls() {
        return 1;
    }
}
