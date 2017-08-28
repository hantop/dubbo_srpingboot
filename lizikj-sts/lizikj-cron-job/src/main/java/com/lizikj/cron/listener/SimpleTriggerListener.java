package com.lizikj.cron.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class SimpleTriggerListener implements TriggerListener {

    @Override
    public String getName() {
        return "SimpleTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    // 在 Trigger 触发后，Job 将要被执行时由 Scheduler 调用这个方法。TriggerListener 给了一个选择去否决 Job 的执行。假如这个方法返回 true，这个 Job 将不会为此次 Trigger 触发而得到执行。
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    // Scheduler 调用这个方法是在 Trigger 错过触发时。如这个方法的 JavaDoc 所指出的，你应该关注此方法中持续时间长的逻辑：在出现许多错过触发的 Trigger 时，长逻辑会导致骨牌效应。你应当保持这上方法尽量的小。
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
