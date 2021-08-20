package pl.czekaj.springsocial.controller.controllerHelper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.Sort;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SortDirectionHelperTest {

    @ParameterizedTest
    @MethodSource("data")
    void getSortDirection_withData_returnOk(Sort.Direction sortDirection,Sort.Direction expectedResult) {
        Sort.Direction result = SortDirectionHelper.getSortDirection(sortDirection);
        assertEquals(expectedResult,result);
    }

    @ParameterizedTest
    @MethodSource("badData")
    void getSortDirection_withBadData_assertNotEquals_returnOk(Sort.Direction sortDirection,Sort.Direction expectedResult) {
        Sort.Direction result = SortDirectionHelper.getSortDirection(sortDirection);
        assertNotEquals(expectedResult,result);
    }

    private static Stream<Arguments> data() {
        return Stream.of(Arguments.of(null, Sort.Direction.DESC),
                Arguments.of("DESC",Sort.Direction.DESC),
                Arguments.of("ASC",Sort.Direction.ASC));
    }

    private static Stream<Arguments> badData() {
        return Stream.of(Arguments.of(null, Sort.Direction.ASC),
                Arguments.of("DESC",Sort.Direction.ASC),
                Arguments.of("ASC",Sort.Direction.DESC));
    }
}