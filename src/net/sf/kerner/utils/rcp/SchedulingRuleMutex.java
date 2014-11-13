package net.sf.kerner.utils.rcp;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class SchedulingRuleMutex implements ISchedulingRule {

    @Override
    public boolean contains(final ISchedulingRule rule) {
        return (rule == this);
    }

    @Override
    public boolean isConflicting(final ISchedulingRule rule) {
        return (rule == this);
    }

}
