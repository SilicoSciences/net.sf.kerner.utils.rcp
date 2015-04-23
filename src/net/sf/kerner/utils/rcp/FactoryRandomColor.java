package net.sf.kerner.utils.rcp;

import net.sf.kerner.utils.Factory;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;

public class FactoryRandomColor implements Factory<Color> {

    private final Device device;

    public FactoryRandomColor(final Device device) {
        this.device = device;
    }

    @Override
    public Color create() {
        final int red = (int) (Math.random() * 256);
        final int green = (int) (Math.random() * 256);
        final int blue = (int) (Math.random() * 256);
        return new Color(device, red, green, blue);
    }

}
