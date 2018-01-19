package com.jaba.eight.examples;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class SupplierExample {

    public int getIntSupplier(Foo foo) {
        IntSupplier intSupplier = foo::getRollNumber;
        return intSupplier.getAsInt();

    }

    public static class Foo {
        int rollNumber;
        boolean pass;

        public int getRollNumber() {
            return rollNumber;
        }

        public void setRollNumber(int rollNumber) {
            this.rollNumber = rollNumber;
        }

        public boolean isPass() {
            return pass;
        }

        public void setPass(boolean pass) {
            this.pass = pass;
        }

        public Foo(int rollNumber, boolean pass) {
            super();
            this.rollNumber = rollNumber;
            this.pass = pass;
        }

        @Override
        public String toString() {
            return "Foo [rollNumber=" + rollNumber + ", pass=" + pass + "]";
        }

    }

    public boolean getBooleanSupplier(Foo foo) {
        BooleanSupplier bs = foo::isPass;
        return bs.getAsBoolean();
    }
}
