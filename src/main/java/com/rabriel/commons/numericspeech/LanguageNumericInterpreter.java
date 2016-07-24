package com.rabriel.commons.numericspeech;

/**
 * Created by Rabriel on 7/19/2016.
 *
 */
public interface LanguageNumericInterpreter {

    int MAX_INTERPRETED_NUMBER = 999;
    int MIN_INTERPRETED_NUMBER = 0;

    /**
     * Returns number interpretation by order.
     *
     * @param number (must be between MIN_INTERPRETED_NUMBER and MAX_INTERPRETED_NUMBER)
     * @param order
     * @param hasGreatestOrder (true when the interpreted number has the greatest number in its sequence)
     * @return the number interpretation, prefixed by space, taking order into account. (e.g.: number=123, order=3 -> " one hundred and twenty three thousands")
     */
    String getNumberInterpretationByOrder(int number, int order, boolean hasGreatestOrder);

}
