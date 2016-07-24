package com.rabriel.commons.numericspeech.impl;

import com.rabriel.commons.numericspeech.LanguageNumericInterpreter;
import com.rabriel.commons.numericspeech.NumericTranslator;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Rabriel on 7/23/2016.
 */
public class EnglishLanguageNumericInterpreterTest {

    public static final String NUMERIC_INTERPRETATION_MISMATCH = "Numeric interpretation mismatch";
    private LanguageNumericInterpreter languageNumericInterpreter = new EnglishLanguageNumericInterpreter();

    @Test
    public void testBasicUse() {

        //given a set of valid number - interpretation pairs
        Map<Integer, String> legalInterpretations = Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>(0, " zero"),
                new AbstractMap.SimpleEntry<>(1, " one"),
                new AbstractMap.SimpleEntry<>(21, " twenty one"),
                new AbstractMap.SimpleEntry<>(105, " one hundred and five"),
                new AbstractMap.SimpleEntry<>(123, " one hundred and twenty three"))
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));

        //when going through each entry then the number interpretation should match the given one
        legalInterpretations.forEach((number, translation) -> {
            assertEquals(NUMERIC_INTERPRETATION_MISMATCH, translation, languageNumericInterpreter.getNumberInterpretationByOrder(number, 0, true));
            assertEquals(NUMERIC_INTERPRETATION_MISMATCH, translation + " hundred", languageNumericInterpreter.getNumberInterpretationByOrder(number, 2, true));
            assertEquals(NUMERIC_INTERPRETATION_MISMATCH, translation + " thousand", languageNumericInterpreter.getNumberInterpretationByOrder(number, 3, true));
            assertEquals(NUMERIC_INTERPRETATION_MISMATCH, translation + " million", languageNumericInterpreter.getNumberInterpretationByOrder(number, 6, true));
        });

    }

    @Test
    public void testConnectorOutput() {
        //when not greatest order then connector should be present
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " and five", languageNumericInterpreter.getNumberInterpretationByOrder(5, 0, false));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " and seventy four", languageNumericInterpreter.getNumberInterpretationByOrder(74, 0, false));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " one hundred and seventy four", languageNumericInterpreter.getNumberInterpretationByOrder(174, 0, false));

        //no connector when greatest order
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " five", languageNumericInterpreter.getNumberInterpretationByOrder(5, 0, true));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " seventy four", languageNumericInterpreter.getNumberInterpretationByOrder(74, 0, true));

        //connector appears due too hundreds present, no matter if greatest order
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " one hundred and seventy four", languageNumericInterpreter.getNumberInterpretationByOrder(174, 0, true));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " one hundred and seventy four", languageNumericInterpreter.getNumberInterpretationByOrder(174, 0, false));
    }

    @Test
    public void testOrderParameter() {
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " five", languageNumericInterpreter.getNumberInterpretationByOrder(5, 0, true));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " five hundred", languageNumericInterpreter.getNumberInterpretationByOrder(5, 2, true));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " five thousand", languageNumericInterpreter.getNumberInterpretationByOrder(5, 3, true));
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " five million", languageNumericInterpreter.getNumberInterpretationByOrder(5, 6, true));
        //given 0 and an intermediate order and any order then it should be interpreted as an empty string
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, "",
                languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MIN_INTERPRETED_NUMBER, 3, false));
    }

    @Test
    public void testMinimumNumber() {
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " zero",
                languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MIN_INTERPRETED_NUMBER, 0, true));
        //given 0 and an intermediate order then it should be interpreted as an empty string
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, "",
                languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MIN_INTERPRETED_NUMBER, 0, false));
    }

    @Test
    public void testMaximumNumber() {
        assertEquals(NUMERIC_INTERPRETATION_MISMATCH, " nine hundred and ninety nine",
                languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MAX_INTERPRETED_NUMBER, 0, true));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNumberTooSmall() {
        languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MIN_INTERPRETED_NUMBER - 1, 0, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberTooBig() {
        languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MAX_INTERPRETED_NUMBER + 1, 0, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderTooSmall() {
        languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MIN_INTERPRETED_NUMBER, -1, true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrderTooBig() {
        languageNumericInterpreter.getNumberInterpretationByOrder(languageNumericInterpreter.MIN_INTERPRETED_NUMBER,
                (int) Math.log10(NumericTranslator.MAX_TRANSLATABLE_NUMBER) - 1, true);
    }

}
