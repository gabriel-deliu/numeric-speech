package com.rabriel.commons.numericspeech.impl;

/**
 * Created by Rabriel on 7/19/2016.
 */
class EnglishLanguageNumericInterpreter extends AbstractLanguageNumericInterpreter {

    //TODO: should consider adding these to a properties file
    protected static final String[] TENS_NAMES = {
            "",
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };

    protected static final String[] NUM_NAMES = {
            "",
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };

    protected static final String[] ORDERS = {
            "",
            "",
            " hundred",
            " thousand",
            "",
            "",
            " million"
    };

    protected static final String ZERO = " zero";
    protected static final String CONNECTOR = " and";

    /**
     * Returns number interpretation.
     * @param number (must be between MIN_INTERPRETED_NUMBER and MAX_INTERPRETED_NUMBER)
     * @param hasGreatestOrder (true when the interpreted number has the greatest number in its sequence)
     * @return the number interpretation prefixed by space. (e.g.: number=123 -> " one hundred and twenty three")
     */
    @Override
    protected String getNumberInterpretation(int number, boolean hasGreatestOrder) {
        if(hasGreatestOrder && number == 0){
            return ZERO;
        }

        StringBuilder result = new StringBuilder();

        int hundreds = number / 100;
        int centime = number % 100;
        int tens = centime / 10;
        int digit = centime % 10;

        if(hundreds > 0){
            result.append(NUM_NAMES[hundreds]);
            result.append(getOrderSuffix(2));
        }

        //should add the connector if needed if the number does not have the greatest order in sequence
        if((!hasGreatestOrder || hundreds > 0) && centime > 0){
            result.append(CONNECTOR);
        }

        if(tens >= 2) {
            result.append(TENS_NAMES[tens]);
            result.append(NUM_NAMES[digit]);
        }else{
            result.append(NUM_NAMES[centime]);
        }

        return result.toString();
    }

    /**
     *
     * @param order
     * @return the order suffix prefixed by space (e.g.: order=2 -> " hundred", order=3 -> " thousand")
     */
    @Override
    protected String getOrderSuffix(int order) {
        if(order < 0 || order >= ORDERS.length){
            throw new IllegalArgumentException("Invalid order");
        }

        return ORDERS[order];
    }
}
