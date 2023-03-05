package ru.msm.edu.lesson_1;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PellTest {

    @ParameterizedTest
    @ArgumentsSource(PellArgsProvider.class)
    public void testSearchPellNumber(Integer input, Long res) {
        assertEquals(res, Pell.searchPellNumber(input));
    }

    static class PellArgsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(0, 0L),
                    Arguments.of(1, 1L),
                    Arguments.of(2, 2L),
                    Arguments.of(3, 5L),
                    Arguments.of(4, 12L),
                    Arguments.of(5, 29L),
                    Arguments.of(6, 70L),
                    Arguments.of(7, 169L),
                    Arguments.of(8, 408L),
                    Arguments.of(9, 985L),
                    Arguments.of(10, 2378L),
                    Arguments.of(11, 5741L),
                    Arguments.of(12, 13860L),
                    Arguments.of(13, 33461L),
                    Arguments.of(14, 80782L),
                    Arguments.of(15, 195025L),
                    Arguments.of(16, 470832L),
                    Arguments.of(17, 1136689L),
                    Arguments.of(18, 2744210L),
                    Arguments.of(19, 6625109L),
                    Arguments.of(20, 15994428L),
                    Arguments.of(21, 38613965L),
                    Arguments.of(22, 93222358L),
                    Arguments.of(23, 225058681L),
                    Arguments.of(24, 543339720L),
                    Arguments.of(25, 1311738121L),
                    Arguments.of(26, 3166815962L),
                    Arguments.of(27, 7645370045L),
                    Arguments.of(28, 18457556052L),
                    Arguments.of(29, 44560482149L),
                    Arguments.of(30, 107578520350L)
            );
        }
    }

}
