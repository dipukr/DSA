package maven;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomBreaker {

    // Actual constants used by java.util.Random
    private static final long MULTIPLIER = 0x5DEECE66DL;
    private static final long ADDEND = 0xBL;
    private static final long MASK = (1L << 48) - 1;

    public static void main(String[] args) {

        // ============================================
        // VICTIM: Uses the real java.util.Random
        // ============================================

        Random victim = new Random();

        // Attacker observes these two consecutive outputs
        int observed1 = victim.nextInt();
        int observed2 = victim.nextInt();

        System.out.println("Observed: " +
                observed1 + ", " + observed2);

        // ============================================
        // ATTACKER: Recover the internal 48-bit state
        // ============================================

        List<Long> candidates =
                recoverState(observed1, observed2);

        System.out.println("Matching states: " +
                candidates.size());

        if (candidates.size() != 1) {
            throw new IllegalStateException(
                    "Need another observed output to disambiguate"
            );
        }

        long recoveredState = candidates.get(0);

        System.out.printf(
                "Recovered 48-bit state: 0x%012X%n",
                recoveredState
        );

        // ============================================
        // ATTACKER: Predict future outputs
        // ============================================

        System.out.println("Predicting the next 10 integers:");

        for (int i = 1; i <= 10; i++) {

            recoveredState = nextState(recoveredState);

            int predicted = (int) (recoveredState >>> 16);
            int actual = victim.nextInt();

            System.out.printf(
                    "%2d: predicted=%11d actual=%11d match=%s%n",
                    i, predicted, actual, predicted == actual
            );
        }
    }

    // ================================================
    // Recover state using two observed integers
    // ================================================

    private static List<Long> recoverState(
            int first,
            int second
    ) {

        // First output contains upper 32 bits of state
        long knownUpperBits =
                Integer.toUnsignedLong(first) << 16;

        List<Long> matches = new ArrayList<>();

        // Guess the missing lower 16 bits
        for (int lowBits = 0;
             lowBits < (1 << 16);
             lowBits++) {

            long candidate = knownUpperBits | lowBits;

            // Advance the candidate state
            long next = nextState(candidate);

            // Does it generate the second observed value?
            if ((int) (next >>> 16) == second) {

                // Store state after second nextInt()
                matches.add(next);
            }
        }

        return matches;
    }

    // ================================================
    // Original LCG state transition
    // ================================================

    private static long nextState(long state) {

        return (state * MULTIPLIER + ADDEND) & MASK;
    }
}
