package com.nibado.example.java8command;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void testCommands() {
        //Our model is represented by a mutable integer. In reality this would typically be a more complex object tree.
        AtomicInteger model = new AtomicInteger(0);

        //Create a few reusable commands.
        Command add10 = Command.parse("add 10");
        Command set20 = Command.parse("set 20");
        Command sub30 = Command.parse("sub 30");

        assertThat(model.get(), equalTo(0));

        add10.apply(model);
        add10.apply(model);

        assertThat(model.get(), equalTo(20));

        sub30.apply(model);

        assertThat(model.get(), equalTo(-10));

        set20.apply(model);

        assertThat(model.get(), equalTo(20));

        //Commands can also be used in a more fluent manner when reusability
        //is not a concern.
        Command.parse("set 1234").apply(model);
        assertThat(model.get(), equalTo(1234));

        //Nonsensical commands return a 'void' command; it doesn't do anything.
        Command.parse(null).apply(model);
        Command.parse("").apply(model);
        Command.parse("foo").apply(model);

        assertThat(model.get(), equalTo(1234));

        //More complex commands, as opposed to the simple add/set/substract ones have implementations. These are hidden
        //and work exactly the same way as the anonymous ones.
        Command.parse("rev").apply(model);
        assertThat(model.get(), equalTo(4321));
    }

    @Test
    public void testReverse() {
        AtomicInteger model = new AtomicInteger();
        ReverseCommand rev = new ReverseCommand();

        model.set(123456789);
        rev.apply(model);
        assertThat(model.get(), equalTo(987654321));
        rev.apply(model);
        assertThat(model.get(), equalTo(123456789));

        model.set(-123456789);
        rev.apply(model);
        assertThat(model.get(), equalTo(-987654321));
        rev.apply(model);
        assertThat(model.get(), equalTo(-123456789));

        model.set(1000);
        rev.apply(model);
        assertThat(model.get(), equalTo(1));
    }
}