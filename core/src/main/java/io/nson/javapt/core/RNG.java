package io.nson.javapt.core;

import org.apache.logging.log4j.*;

/**
 * Java implementation of Melissa O'Neil's PCG algorithm.
 */
public class RNG {
    private static final Logger logger = LogManager.getLogger(RNG.class);

    private static final long MULTIPLIER = 6364136223846793005L;

    private static final double DOUBLE_UNIT = 0x1.0p-53;

    private static final float FLOAT_UNIT = 1.0f / (1 << 24);

    private long state;
    private long inc;

    public RNG(long initState, long initseq, boolean dummy) {
        this.state = initState;
        this.inc = initseq;
    }

    public RNG(long initState, long initseq) {
        this.state = 0;
        this.inc = (initseq << 1) | 1;
        nextInt();
        state += initState;
        nextInt();
    }

    public RNG(RNG rhs) {
        this.state = rhs.state;
        this.inc = rhs.inc;
    }

    @Override
    public String toString() {
        return "RNG{state=" + state + "; inc=" + inc + "}";
    }

    public void advance(long delta) {
        long accMult = 1;
        long accPlus = 0;

        long curPlus = inc;
        long curMult = MULTIPLIER;

        while (Long.compareUnsigned(delta, 0) > 0) {
            if ((delta & 1) == 1) {
                accMult *= curMult;
                accPlus = accPlus * curMult + curPlus;
            }
            curPlus *= curMult + 1;
            curMult *= curMult;
            delta = Long.divideUnsigned(delta, 2);
        }
        state = (accMult * state) + accPlus;
    }

    public int nextBits(int bits) {
        return nextInt() >>> (32 - bits);
    }

    public int nextInt() {
        final long oldState = state;

        // Advance internal state.
        state = oldState * MULTIPLIER + inc;

        // Calculate output function (XSH RR), uses old state for max ILP.
        final int xorShifted = (int)(((oldState >>> 18) ^ oldState) >>> 26);
        final int rot = (int)(oldState >>> 58);
        return Integer.rotateRight(xorShifted, rot);
    }

    public int nextInt(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }

        int r = nextInt();
        final int m = n - 1;

        // Is n a power of 2?
        if ((n & m) == 0) {
            return r & m;
        } else {
            // Reject over-represented candidates.
            int u = r >>> 1;
            r = u % n;
            while (u + m - r < 0) {
                u = nextInt() >>> 1;
                r = u % n;
            }
        }
        return r;
    }

    public long nextLong() {
        final long a = nextInt();
        final long b = nextInt();
        return (a << 32) + b;
    }

    public float nextFloat01() {
        return nextBits(24) * FLOAT_UNIT;
    }

    public double nextDouble01() {
        return (nextLong() >>> 11) * DOUBLE_UNIT;
    }
}