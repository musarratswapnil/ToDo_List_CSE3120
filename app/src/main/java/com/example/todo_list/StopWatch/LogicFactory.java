package com.example.todo_list.StopWatch;

/**
 * Factory class for creating instances of logic classes.
 */
public class LogicFactory {

    // Method to get TimerLogic instance
    public static TimerLogic getTimerLogic() {
        return TimerLogic.getInstance();
    }

    // Method to get StopwatchLogic instance
    public static StopwatchLogic getStopwatchLogic() {
        return StopwatchLogic.getInstance();
    }
}
