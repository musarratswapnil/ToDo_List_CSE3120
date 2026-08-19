package com.example.todo_list.StopWatch;

/**
 * A factory interface for creating timer and stopwatch logic instances.
 */
public interface LogicFactory {

    /**
     * Creates a new instance of TimerLogic.
     *
     * @return A new instance of TimerLogic.
     */
    TimerLogic createTimerLogic();

    /**
     * Creates a new instance of StopwatchLogic.
     *
     * @return A new instance of StopwatchLogic.
     */
    StopwatchLogic createStopwatchLogic();
}
