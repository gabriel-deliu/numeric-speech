package com.rabriel.commons.numericspeech.impl;

/**
 * Created by Rabriel on 7/19/2016.
 */
public class EnglishNumericTranslator extends AbstractNumericTranslator {

    public EnglishNumericTranslator(){
        super(new EnglishLanguageNumericInterpreter());
    }

}
