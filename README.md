# numeric-speech
Provides the means to translate a number in its word equivalent.

# supported languages
For now only English is supported.

# limitations
Only numbers between 0 and 999999999 are currently supported.

# use
```
import com.rabriel.commons.numericspeech.NumericTranslator;
import com.rabriel.commons.numericspeech.impl.EnglishNumericTranslator;
...
NumericTranslator numericTranslator = new EnglishNumericTranslator();
String numericTranslation = numericTranslator.translate(1234567);
```
