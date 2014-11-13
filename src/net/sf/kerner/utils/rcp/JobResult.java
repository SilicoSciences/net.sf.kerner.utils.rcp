package net.sf.kerner.utils.rcp;

public abstract class JobResult<T> extends Job2 {

    protected T result;

    public JobResult(final String name, final String pluginId) {
        super(name, pluginId);
    }

    public T getResultObject() {
        try {
            join();
            return result;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setResultObject(final T result) {
        this.result = result;
    }
}
