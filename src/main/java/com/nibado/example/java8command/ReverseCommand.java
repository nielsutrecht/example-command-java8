package com.nibado.example.java8command;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ReverseCommand is a rather 'complex' command. It reverses an integer and also keeps the sign. Implementhing all
 * this in Command.parse would make the method rather messy.
 */
public class ReverseCommand implements Command {
    @Override
    public void apply(AtomicInteger model) {
        int in = model.get();
        int out = 0;
        boolean neg = false;

        if(in < 0) {
            in = -in;
            neg = true;
        }

        while(true) {
            out += in % 10;
            if(in < 10) {
                break;
            }
            out *= 10;
            in /= 10;
        }
        if(neg) {
            out = -out;
        }
        model.set(out);
    }
}
