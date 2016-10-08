package com.nulabinc.zxcvbn;

import com.nulabinc.zxcvbn.guesses.*;
import com.nulabinc.zxcvbn.matchers.Match;
import com.nulabinc.zxcvbn.matchers.MatchFactory;

import java.util.*;

public class Scoring {

    public static final int REFERENCE_YEAR = 2016;

    public static final int MIN_GUESSES_BEFORE_GROWING_SEQUENCE = 10000;

    public static double log10(double n) {
        return Math.log(n) / Math.log(10);
    }

    public static Strength mostGuessableMatchSequence(String password, List<Match> matches) {
        return mostGuessableMatchSequence(password, matches, false);
    }

    public static Strength mostGuessableMatchSequence(String password, List<Match> matches, boolean excludeAdditive) {
        final int n = password.length();
        final List<List<Match>> matchesByJ = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matchesByJ.add(new ArrayList<Match>());
        }
        for (Match m : matches) {
            matchesByJ.get(m.j).add(m);
        }
        final Optimal optimal = new Optimal(n);
        for (int k = 0; k < n; k++) {
            for(Match m :matchesByJ.get(k)) {
                if (m.i > 0) {
                    for(Map.Entry<Integer, Match> entry : optimal.m.get(m.i - 1).entrySet()) {
                        int l = entry.getKey();
                        update(password, m, l + 1, optimal, excludeAdditive);
                    }
                } else {
                    update(password, m, 1, optimal, excludeAdditive);
                }
            }
            bruteforceUpdate(password, k, optimal, excludeAdditive);
        }
        List<Match> optimalMatchSequence = unwind(n, optimal);
        double guesses = password.length() == 0 ? 1 : optimal.g.get(n - 1);
        Strength strength = new Strength();
        strength.setPassword(password);
        strength.setGuesses(guesses);
        strength.setGuessesLog10(log10(guesses));
        strength.setSequence(optimalMatchSequence);
        return strength;
    }

    private static void update(String password, Match m, int l, Optimal optimal, boolean excludeAdditive) {
        int k = m.j;
        double pi = new EstimateGuess(password).exec(m);
        if (l > 1) {
            pi *= optimal.pi.get(m.i - 1).get(l - 1);
        }
        double g = factorial(l) * pi;
        if (!excludeAdditive) {
            g += Math.pow(MIN_GUESSES_BEFORE_GROWING_SEQUENCE, l - 1);
        }
        if (g < optimal.g.get(k)) {
            optimal.g.set(k, g);
            optimal.l.set(k, l);
            optimal.m.get(k).put(l, m);
            optimal.pi.get(k).put(l, pi);
        }
    }

    private static void bruteforceUpdate(String password, int k, Optimal optimal, boolean excludeAdditive) {
        Match m = makeBruteforceMatch(password, 0, k);
        update(password, m, 1, optimal, excludeAdditive);
        if (k == 0) {
            return;
        }
        for (Map.Entry<Integer, Match> entry : optimal.m.get(k - 1).entrySet()) {
            int l = entry.getKey();
            Match last_m = entry.getValue();
            if (last_m.pattern == Pattern.Bruteforce) {
                m = makeBruteforceMatch(password, last_m.i, k);
                update(password, m, l, optimal, excludeAdditive);
            } else {
                m = makeBruteforceMatch(password, k, k);
                update(password, m, l + 1, optimal, excludeAdditive);
            }
        }
    }

    private static List<Match> unwind(int n, Optimal optimal) {
        List<Match> optimalMatchSequence = new ArrayList<>();
        int k = n - 1;
        if (0 <= k) {
            int l = optimal.l.get(k);
            while (k >= 0) {
                Match m = optimal.m.get(k).get(l);
                optimalMatchSequence.add(0, m);
                k = m.i - 1;
                l--;
            }
        }
        return optimalMatchSequence;
    }

    private static Match makeBruteforceMatch(String password, int i, int j) {
        return MatchFactory.createBruteforceMatch(i, j, password.substring(i, j + 1));
    }

    private static int factorial(int n) {
        if (n < 2) return 1;
        int f = 1;
        for (int i = 2; i <= n; i++) f *= i;
        return f;
    }

    private static class Optimal {

        public final List<Map<Integer, Match>> m = new ArrayList<>();

        public final List<Map<Integer, Double>> pi = new ArrayList<>();

        public final List<Double> g = new ArrayList<>();

        public final List<Integer> l = new ArrayList<>();

        public Optimal(int n) {
            for (int i = 0; i < n; i++) {
                m.add(new HashMap<Integer, Match>());
                pi.add(new HashMap<Integer, Double>());
                g.add(Double.POSITIVE_INFINITY);
                l.add(0);
            }
        }
    }

}
