package com.example.todo_list.KeepNote.Operation;

public class CommandInvoker {
    private Command command;

    /**
     * Sets the command to be executed.
     * @param command The command to execute.
     */
    public void setCommand(Command command) {
        this.command = command;
    }
    /**
     * Executes the currently set dynamic command.
     */
    public void executeCommand() {
        if (command != null) {
            command.executeNote();
        }
    }
}