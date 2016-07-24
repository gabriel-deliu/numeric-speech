package com.rabriel.commons.numericspeech.impl;

import com.rabriel.commons.numericspeech.LanguageNumericInterpreter;

/**
 * Created by Rabriel on 7/22/2016.
 * Handles numeric implementation. Language specific implementation is required in subclasses.
 */
abstract class AbstractLanguageNumericInterpreter implements LanguageNumericInterpreter {

    /**
     * Returns number interpretation by order.
     *
     * @param number (must be between MIN_INTERPRETED_NUMBER and MAX_INTERPRETED_NUMBER)
     * @param order
     * @param hasGreatestOrder (true when the interpreted number has the greatest number in its sequence)
     * @return the number interpretation, prefixed by space, taking order into account. (e.g.: number=123, order=3 -> " one hundred and twenty three thousands")
     */
    @Override
    public String getNumberInterpretationByOrder(int number, int order, boolean hasGreatestOrder) {

        if(number < MIN_INTERPRETED_NUMBER || number > MAX_INTERPRETED_NUMBER){
            throw new IllegalArgumentException(String.format("expected number %d should be between %d and %d", number, MIN_INTERPRETED_NUMBER, MAX_INTERPRETED_NUMBER));
        }

        if(number == 0 && !hasGreatestOrder){
            return "";
        }

        return getNumberInterpretation(number, hasGreatestOrder) + getOrderSuffix(order);
    }

    /**
     * Returns number interpretation.
     * @param number (must be between MIN_INTERPRETED_NUMBER and MAX_INTERPRETED_NUMBER)
     * @param hasGreatestOrder (true when the interpreted number has the greatest number in its sequence)
     * @return the number interpretation prefixed by space. (e.g.: number=123 -> " one hundred and twenty three")
     */
    protected abstract String getNumberInterpretation(int number, boolean hasGreatestOrder);

    /**
     *
     * @param order
     * @return the order suffix prefixed by space (e.g.: order=2 -> " hundred", order=3 -> " thousand")
     */
    protected abstract String getOrderSuffix(int order);
}
