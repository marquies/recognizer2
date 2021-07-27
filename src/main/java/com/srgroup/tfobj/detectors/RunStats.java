package com.srgroup.tfobj.detectors;

/**
 * Accumulate and analyze stats from metadata obtained from Session.Runner.run.
 */
public class RunStats implements AutoCloseable {

    // Hack: This is what a serialized RunOptions protocol buffer with trace_level: FULL_TRACE ends
    // up as.
    private static byte[] fullTraceRunOptions = new byte[]{0x08, 0x03};
    private long nativeHandle;

    public RunStats() {
        nativeHandle = allocate();
    }

    /**
     * Options to be provided to a {@link org.tensorflow.Session.Runner} to enable stats accumulation.
     *
     * @return byteArray
     */
    public static byte[] runOptions() {
        return fullTraceRunOptions;
    }

    private static native long allocate();

    private static native void delete(long handle);

    private static native void add(long handle, byte[] runMetadata);

    private static native String summary(long handle);

    @Override
    public void close() {
        if (nativeHandle != 0) {
            delete(nativeHandle);
        }
        nativeHandle = 0;
    }

    /**
     * Accumulate stats obtained when executing a graph.
     *
     * @param runMetadata the arguments.
     */
    public synchronized void add(byte[] runMetadata) {
        add(nativeHandle, runMetadata);
    }

    /**
     * Summary of the accumulated runtime stats.
     *
     * @return string with summary.
     */
    public synchronized String summary() {
        return summary(nativeHandle);
    }
}