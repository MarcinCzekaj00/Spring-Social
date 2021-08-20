package pl.czekaj.springsocial.controller.controllerHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PageHelperTest {

    @ParameterizedTest
    @MethodSource("data")
    void getPageNumberGreaterThenZeroAndNotNull_withData_returnOk(Integer page,Integer expectedResult) {
        Integer result = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        assertEquals(expectedResult,result);
    }

    @ParameterizedTest
    @MethodSource("badData")
    void getPageNumberGreaterThenZeroAndNotNull_withBadData_assertNotEquals_returnOk(Integer page,Integer expectedResult) {
        Integer result = PageHelper.getPageNumberGreaterThenZeroAndNotNull(page);
        assertNotEquals(expectedResult,result);
    }

    private static Stream<Arguments> data() {
        return Stream.of(Arguments.of(null,0),
                        Arguments.of(0,0),
                        Arguments.of(-1,0),
                        Arguments.of(5,5));
    }

    private static Stream<Arguments> badData() {
        return Stream.of(Arguments.of(null,1),
                Arguments.of(0,5),
                Arguments.of(-1,1),
                Arguments.of(5,null),
                Arguments.of(null,-2));
    }
}