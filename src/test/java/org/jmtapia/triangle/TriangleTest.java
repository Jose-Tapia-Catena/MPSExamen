package org.jmtapia.triangle;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TriangleTest {

    private Triangle triangle;

    @BeforeEach
    public void setUp(){
        triangle = new Triangle();
    }

    @AfterEach
    public void finish(){
        triangle = null;
    }

    @Test
    public void shouldGetTypeReturnEQUILATERAL() {

        Triangle.TriangleType expectedValue = Triangle.TriangleType.EQUILATERAL;
        Triangle.TriangleType obtainedValue = triangle.getType(1,1,1);

        assertEquals(expectedValue,obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnIsoscelesIfSide1AndSide2AreEqualsAndSide3IsDifferent() {

        Triangle.TriangleType expectedValue = Triangle.TriangleType.ISOSCELES;
        Triangle.TriangleType obtainedValue = triangle.getType(2,2,1);

        assertEquals(expectedValue,obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnIsoscelesIfSide2AndSide3AreEqualsAndSide1IsDifferent() {

        Triangle.TriangleType expectedValue = Triangle.TriangleType.ISOSCELES;
        Triangle.TriangleType obtainedValue = triangle.getType(1,2,2);

        assertEquals(expectedValue,obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnIsoscelesIfSide1AndSide3AreEqualsAndSide2IsDifferent() {

        Triangle.TriangleType expectedValue = Triangle.TriangleType.ISOSCELES;
        Triangle.TriangleType obtainedValue = triangle.getType(2,1,2);

        assertEquals(expectedValue,obtainedValue);
    }

    @Test
    public void shouldGetTypeReturnSCALENE() {

        Triangle.TriangleType expectedValue = Triangle.TriangleType.SCALENE;
        Triangle.TriangleType obtainedValue = triangle.getType(3,5,4);

        assertEquals(expectedValue,obtainedValue);
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfSide1IsNegative(){

        assertThrows(RuntimeException.class, () -> triangle.getType(-1,2,3));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfSide2IsNegative(){

        assertThrows(RuntimeException.class, () -> triangle.getType(1,-2,3));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfSide3IsNegative(){

        assertThrows(RuntimeException.class, () -> triangle.getType(1,2,-7));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfSide1IsZero(){

        assertThrows(RuntimeException.class, () -> triangle.getType(0,2,3));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfSide2IsZero(){

        assertThrows(RuntimeException.class, () -> triangle.getType(5,0,3));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfSide3IsZero(){

        assertThrows(RuntimeException.class, () -> triangle.getType(5,4,0));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfTheSumOfSide2AndSide3IsSmallerOrEqualThanSide1(){

        assertThrows(RuntimeException.class, () -> triangle.getType(2,1,1));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfTheSumOfSide1AndSide3IsSmallerOrEqualThanSide2(){

        assertThrows(RuntimeException.class, () -> triangle.getType(3,5,2));
    }

    @Test
    public void shouldGetTypeThrowAnExceptionIfTheSumOfSide1AndSide2IsSmallerOrEqualThanSide3(){

        assertThrows(RuntimeException.class, () -> triangle.getType(2,5,2));
    }
}
