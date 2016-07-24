package com.rabriel.commons.numericspeech.impl;

import com.rabriel.commons.numericspeech.NumericTranslator;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Rabriel on 7/19/2016.
 */
public class EnglishNumericTranslatorTest {

    public static final String NUMERIC_TRANSLATION_MISMATCH = "Numeric translation mismatch";
    private NumericTranslator numericTranslator = new EnglishNumericTranslator();

    @Test
    public void testBasicUse() {
        //given a set of valid number - translation pairs
        Map<Integer, String> legalTranslations = Collections.unmodifiableMap(Stream.of(
                new SimpleEntry<>(0, "zero"),
                new SimpleEntry<>(1, "one"),
                new SimpleEntry<>(21, "twenty one"),
                new SimpleEntry<>(105, "one hundred and five"),
                new SimpleEntry<>(123, "one hundred and twenty three"),
                new SimpleEntry<>(1005, "one thousand and five"),
                new SimpleEntry<>(1042, "one thousand and forty two"),
                new SimpleEntry<>(1105, "one thousand one hundred and five"),
                new SimpleEntry<>(56945781, "fifty six million nine hundred and forty five thousand seven hundred and eighty one"),
                new SimpleEntry<>(999999999, "nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine"))
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
        //when going through each entry then the number translation should match the given one
        legalTranslations.forEach((number, translation) -> assertEquals(NUMERIC_TRANSLATION_MISMATCH, translation, numericTranslator.translate(number)));

    }

    @Test
    public void testTeens() {
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "eleven", numericTranslator.translate(11));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "twelve", numericTranslator.translate(12));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "sixteen", numericTranslator.translate(16));
    }

    @Test
    public void testTens() {
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "ten", numericTranslator.translate(10));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "forty", numericTranslator.translate(40));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "sixty", numericTranslator.translate(60));
    }

    @Test
    public void testHundreds() {
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "two hundred", numericTranslator.translate(200));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "five thousand two hundred", numericTranslator.translate(5200));
    }

    @Test
    public void testWithMissingOrderEntries() {
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "ten million and forty five", numericTranslator.translate(10000045));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "nine hundred and ninety nine million", numericTranslator.translate(999000000));
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "one hundred million", numericTranslator.translate(100000000));
    }

    @Test
    public void testMinimumNumber() {
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "zero", numericTranslator.translate(numericTranslator.MIN_TRANSLATABLE_NUMBER));
    }

    @Test
    public void testMaximumNumber() {
        assertEquals(NUMERIC_TRANSLATION_MISMATCH, "nine hundred and ninety nine million nine hundred and ninety nine thousand nine hundred and ninety nine", numericTranslator.translate(numericTranslator.MAX_TRANSLATABLE_NUMBER));
    }


    @Test(expected=IllegalArgumentException.class)
    public void testNumberTooSmall() {
        numericTranslator.translate(numericTranslator.MIN_TRANSLATABLE_NUMBER-1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNumberTooBig() {
        numericTranslator.translate(numericTranslator.MAX_TRANSLATABLE_NUMBER +1);
    }
}
