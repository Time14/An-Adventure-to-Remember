package aatr.engine.debug;

public class Debug {
	
	public static void log(Object out) { 
        StackTraceElement st = Thread.currentThread().getStackTrace()[2];
        System.out.println("[" + st.getFileName().split("[.]")[0] + ":" + st.getLineNumber() + "] " + out.toString());
	}
	
	public static void error(Object out) { 
        StackTraceElement st = Thread.currentThread().getStackTrace()[2];
        System.err.println("[" + st.getFileName().split("[.]")[0] + ":" + st.getLineNumber() + "] " + out.toString());
	}
}
