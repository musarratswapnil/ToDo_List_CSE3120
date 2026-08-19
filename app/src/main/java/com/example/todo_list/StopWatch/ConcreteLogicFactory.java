package com.example.todo_list.StopWatch;

public class ConcreteLogicFactory implements LogicFactory {

    @Override
    public TimerLogic createTimerLogic() {
        return new TimerLogic();
    }

    @Override
    public StopwatchLogic createStopwatchLogic() {
        return new StopwatchLogic();
    }
}
