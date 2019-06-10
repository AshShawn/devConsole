package com.hll.test;

public class TestConsoleStartup {

    public static void main(String[] args) throws Exception {
        JettyStartup.startup(8090, false, false);
    }

}
