package com.nibado.example.java8command;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Our command interface. It is a functional interface that makes good use of all the new Java 8 goodies, mainly
 * lambda's and static methods in interfaces.
 */
public interface Command {
    Command VOID_COMMAND = i -> {};

    /**
     * Applies the command to the model.
     * @param model the model
     */
    void apply(AtomicInteger model);

    /**
     * Parses a string into a command. Returns VOID_COMMAND for invalid commands.
     *
     * @param cmd the command to parse
     * @return the command which can be applied to the model
     */
    static Command parse(String cmd) {
        if(cmd == null || cmd.trim().equals("")) {
            return VOID_COMMAND;
        }
        String[] parts = cmd.split(" ");

        switch(parts[0]) {
            case "add":
                return i ->  i.addAndGet(Integer.parseInt(parts[1]));
            case "sub":
                return i -> i.addAndGet(-Integer.parseInt(parts[1]));
            case "set":
                return i -> i.set(Integer.parseInt(parts[1]));
            case "rev":
                return new ReverseCommand();
            default:
                return VOID_COMMAND;
        }
    }
}
