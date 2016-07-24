package com.rabriel.commons.numericspeech.impl;

import com.rabriel.commons.numericspeech.LanguageNumericInterpreter;
import com.rabriel.commons.numericspeech.NumericTranslator;

/**
 * Created by Rabriel on 7/22/2016.
 * Handles numeric to text conversions. Subclasses will specify language specific implementation through LanguageNumericInterpreter.
 */
abstract class AbstractNumericTranslator implements NumericTranslator {

    private LanguageNumericInterpreter languageNumericInterpreter;

    public AbstractNumericTranslator(LanguageNumericInterpreter languageNumericInterpreter){
        this.languageNumericInterpreter = languageNumericInterpreter;
    }

    /**
     * Translates a number into its language representation
     *
     * @param number must be between MIN_TRANSLATABLE_NUMBER and MAX_TRANSLATABLE_NUMBER99999999)
     * @return language representation of the number
     */
    @Override
    public String translate(int number) {

        if(number < MIN_TRANSLATABLE_NUMBER || number > MAX_TRANSLATABLE_NUMBER){
            throw new IllegalArgumentException(String.format("expected number %d should be between %d and %d", number, MIN_TRANSLATABLE_NUMBER, MAX_TRANSLATABLE_NUMBER));
        }

        StringBuilder result = new StringBuilder();
        int currentNumber = number;
        int currentOrder = 0;
        //go through the number from right to left in chunks of 3 digits and interpret the chunks accordingly
        do {
            int localNumber = currentNumber % 1000;
            //depending on the language, connectors may be used between intermediary chunks
            //hasGreatestOrder is needed if order to apply the connectors.
            boolean hasGreatestOrder = localNumber == currentNumber;
            result.insert(0, languageNumericInterpreter.getNumberInterpretationByOrder(localNumber, currentOrder, hasGreatestOrder));
            currentNumber /= 1000;
            currentOrder += 3;
        } while(currentNumber > 0);

        return result.toString().trim();
    }

}
