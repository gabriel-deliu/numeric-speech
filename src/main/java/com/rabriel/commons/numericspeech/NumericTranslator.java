package com.rabriel.commons.numericspeech;

/**
 * Created by Rabriel on 7/19/2016.
 * Provides numeric translation methods
 */
public interface NumericTranslator {

    int MAX_TRANSLATABLE_NUMBER = 999999999;
    int MIN_TRANSLATABLE_NUMBER = 0;

    /**
     * Translates an int into its language representation
     * @param number (number must be between MIN_TRANSLATABLE_NUMBER and MAX_TRANSLATABLE_NUMBER)
     * @return language representation of the number
     */
    String translate(int number);

}
