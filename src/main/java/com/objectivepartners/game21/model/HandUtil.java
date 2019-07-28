package com.objectivepartners.game21.model;


import java.util.Collection;

import static com.objectivepartners.game21.model.Rank.ACE;


public final class HandUtil {
    private static final int TWENTY_ONE = 21;
    private static final int BANK_MIN = 17;
    private static final int ACE_ELEVEN = ACE.getValue();
    private static final int ACE_ONE = 1;

    public static int value(Collection<Rank> cards) {
        int value = 0;
        int numAces = 0;
        for (Rank r : cards) {
            if (isAce(r)) {
                numAces++;
            }
            value += r.getValue();
        }
        return treatAceAsLowIfNecessary(value, numAces);
    }

    public static boolean isBust(int value) {
        return value > TWENTY_ONE;
    }

    public static boolean isBust(Collection<Rank> cards) {
        return isBust(value(cards));
    }

    public static boolean isTarget(Collection<Rank> cards) {
        return value(cards) == TWENTY_ONE;
    }

    public static boolean isBelowBankMin(Collection<Rank> bank) {
        return value(bank) < BANK_MIN;
    }

    public static boolean isgame21(Collection<Rank> cards) {
        return cards.size() == 2 && isTarget(cards);
    }

    private static int treatAceAsLowIfNecessary(int value, int numAces) {
        while (numAces > 0 && isBust(value)) {
            value -= ACE_ELEVEN - ACE_ONE;
            numAces--;
        }
        return value;
    }

    private static boolean isAce(Rank r) {
        return r == ACE;
    }
}
