package net.sf.kerner.utils.rcp;

/*******************************************************************************
 * Copyright (c) 2011 Google, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Google, Inc. - initial API and implementation
 *******************************************************************************/

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A field editor for a double type preference.
 * <p>
 * This class may be freely distributed as part of any application or plugin.
 * <p>
 * 
 * @author scheglov_ke
 */
public class DoubleFieldEditor extends StringFieldEditor {
    private static final int DEFAULT_TEXT_LIMIT = 10;

    private static String getMessage_invalidRange(final double min, final double max) {
        final String message = JFaceResources.format("IntegerFieldEditor.errorMessageRange", //$NON-NLS-1$
                new Object[] { new Double(min), new Double(max) });
        return replaceInteger_withDouble(message);
    }

    // //////////////////////////////////////////////////////////////////////////
    //
    // Messages
    //
    // //////////////////////////////////////////////////////////////////////////
    private static String getMessage_notDouble() {
        final String message = JFaceResources.getString("IntegerFieldEditor.errorMessage"); //$NON-NLS-1$
        return replaceInteger_withDouble(message);
    }

    private static String replaceInteger_withDouble(String message) {
        final int index = message.indexOf("Integer"); //$NON-NLS-1$
        if (index != -1) {
            final String prefix = message.substring(0, index);
            final String suffix = message.substring(index + "Integer".length()); //$NON-NLS-1$
            message = prefix + "Double" + suffix; //$NON-NLS-1$
        }
        return message;
    }

    private double m_minValidValue;
    private double m_maxValidValue = Double.MAX_VALUE;

    // //////////////////////////////////////////////////////////////////////////
    //
    // Constructors
    //
    // //////////////////////////////////////////////////////////////////////////
    /**
     * Creates a new double field editor
     */
    protected DoubleFieldEditor() {
    }

    /**
     * Creates a double field editor.
     * 
     * @param name
     *            the name of the preference this field editor works on
     * @param labelText
     *            the label text of the field editor
     * @param parent
     *            the parent of the field editor's control
     */
    public DoubleFieldEditor(final String name, final String labelText, final Composite parent) {
        this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
    }

    /**
     * Creates a double field editor.
     * 
     * @param name
     *            the name of the preference this field editor works on
     * @param labelText
     *            the label text of the field editor
     * @param parent
     *            the parent of the field editor's control
     * @param textLimit
     *            the maximum number of characters in the text.
     */
    public DoubleFieldEditor(final String name, final String labelText, final Composite parent, final int textLimit) {
        init(name, labelText);
        setTextLimit(textLimit);
        setEmptyStringAllowed(false);
        setErrorMessage(getMessage_notDouble());
        createControl(parent);
    }

    // //////////////////////////////////////////////////////////////////////////
    //
    // FieldEditor
    //
    // //////////////////////////////////////////////////////////////////////////
    @Override
    protected boolean checkState() {
        final Text text = getTextControl();
        if (text == null) {
            return false;
        }
        final String numberString = text.getText();
        try {
            final double number = Double.valueOf(numberString).doubleValue();
            if ((number >= m_minValidValue) && (number <= m_maxValidValue)) {
                clearErrorMessage();
                return true;
            }
            showErrorMessage();
            return false;
        } catch (final NumberFormatException e) {
            showErrorMessage();
        }
        return false;
    }

    @Override
    protected void doLoad() {
        final Text text = getTextControl();
        if (text != null) {
            final double value = getPreferenceStore().getDouble(getPreferenceName());
            text.setText("" + value); //$NON-NLS-1$
        }
    }

    @Override
    protected void doLoadDefault() {
        final Text text = getTextControl();
        if (text != null) {
            final double value = getPreferenceStore().getDefaultDouble(getPreferenceName());
            text.setText("" + value); //$NON-NLS-1$
        }
        valueChanged();
    }

    @Override
    protected void doStore() {
        final Text text = getTextControl();
        if (text != null) {
            final Double i = new Double(text.getText());
            getPreferenceStore().setValue(getPreferenceName(), i.doubleValue());
        }
    }

    // //////////////////////////////////////////////////////////////////////////
    //
    // Access
    //
    // //////////////////////////////////////////////////////////////////////////
    /**
     * Returns this field editor's current value as a double.
     * 
     * @return the value
     * @exception NumberFormatException
     *                if the <code>String</code> does not contain a parsable
     *                double
     */
    public double getDoubleValue() throws NumberFormatException {
        return new Double(getStringValue()).doubleValue();
    }

    /**
     * Sets the tool tip text of the label and text control.
     * 
     * @param text
     *            the text to set
     */
    public void setToolTipText(final String text) {
        getLabelControl().setToolTipText(text);
        getTextControl().setToolTipText(text);
    }

    /**
     * Sets the range of valid values for this field.
     * 
     * @param min
     *            the minimum allowed value (inclusive)
     * @param max
     *            the maximum allowed value (inclusive)
     */
    public void setValidRange(final double min, final double max) {
        m_minValidValue = min;
        m_maxValidValue = max;
        setErrorMessage(getMessage_invalidRange(min, max));
    }
}
