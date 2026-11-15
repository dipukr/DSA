package maven;


import java.math.BigInteger;

public class LCGBreaker {

    public static void main(String[] args) {

        // =============================================
        // STEP 1: Victim creates a random number generator
        // =============================================

        LCGRandom victim = new LCGRandom(
                7,   // Initial seed
                5,   // Multiplier (a)
                3,   // Increment (c)
                16   // Modulus (m)
        );

        System.out.println("=== VICTIM GENERATES RANDOM NUMBERS ===");

        // Attacker observes three consecutive outputs
        long x0 = victim.rand();
        long x1 = victim.rand();
        long x2 = victim.rand();

        System.out.println("Observed x0: " + x0);
        System.out.println("Observed x1: " + x1);
        System.out.println("Observed x2: " + x2);

        // =============================================
        // STEP 2: Attacker knows only the modulus
        // =============================================

        long m = 16;

        // Calculate consecutive differences
        long d0 = Math.floorMod(x1 - x0, m);
        long d1 = Math.floorMod(x2 - x1, m);

        // =============================================
        // STEP 3: Recover the multiplier (a)
        // =============================================

        BigInteger inverse = BigInteger.valueOf(d0)
                .modInverse(BigInteger.valueOf(m));

        long a = BigInteger.valueOf(d1)
                .multiply(inverse)
                .mod(BigInteger.valueOf(m))
                .longValueExact();

        // =============================================
        // STEP 4: Recover the increment (c)
        // =============================================

        long c = Math.floorMod(x1 - a * x0, m);

        System.out.println("\n=== ATTACKER RECOVERS PARAMETERS ===");
        System.out.println("Recovered multiplier (a): " + a);
        System.out.println("Recovered increment  (c): " + c);

        // =============================================
        // STEP 5: Predict the victim's next output
        // =============================================

        long predicted = Math.floorMod(a * x2 + c, m);

        System.out.println("\n=== ATTACKER PREDICTS NEXT NUMBER ===");
        System.out.println("Predicted next number: " + predicted);

        // =============================================
        // STEP 6: Victim generates the actual next number
        // =============================================

        long actual = victim.rand();

        System.out.println("Actual next number   : " + actual);
        System.out.println("Prediction successful: " + (predicted == actual));
    }

    // =================================================
    // VICTIM: Custom Linear Congruential Generator
    // =================================================

    static class LCGRandom {

        private long state;

        private final long a;
        private final long c;
        private final long m;

        public LCGRandom(long seed, long a, long c, long m) {
            this.state = seed;
            this.a = a;
            this.c = c;
            this.m = m;
        }

        public long rand() {
            // LCG formula:
            // X(n+1) = (a * X(n) + c) mod m

            state = Math.floorMod(a * state + c, m);

            return state;
        }
    }
}
