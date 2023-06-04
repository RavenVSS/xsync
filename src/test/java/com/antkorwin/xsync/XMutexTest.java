package com.antkorwin.xsync;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created on 17.06.2018.
 *
 * @author Korovin Anatoliy
 */
public class XMutexTest {

    private final String FIRST_KEY = new String("111");
    private final String SECOND_KEY = new String("111");
    private final String THIRD_KEY = new String("222");

    @Test
    public void testMutexEquals() {
        // Arrange
        XMutex<String> mutex1 = new XMutex<>(FIRST_KEY);
        XMutex<String> mutex2 = new XMutex<>(SECOND_KEY);

        // Act & Assert
        Assertions.assertThat(FIRST_KEY).isNotSameAs(SECOND_KEY);
        Assertions.assertThat(mutex1).isEqualTo(mutex2);
        Assertions.assertThat(mutex1).isNotSameAs(mutex2);
    }

    @Test
    public void testMutexNotEquals() {
        // Arrange
        XMutex<String> mutex1 = new XMutex<>(FIRST_KEY);
        XMutex<String> mutex2 = new XMutex<>(THIRD_KEY);

        // Act & Assert
        Assertions.assertThat(mutex1).isNotEqualTo(mutex2);
    }

    @Test
    public void testHashCode() {
        // Arrange
        XMutex<String> mutex1 = new XMutex<>(FIRST_KEY);
        XMutex<String> mutex2 = new XMutex<>(SECOND_KEY);

        // Act & Assert
        Assertions.assertThat(mutex1.hashCode()).isEqualTo(mutex2.hashCode());
    }

    @Test
    public void testWeakMapWithTwoEqualMutexes() {
        // Arrange
        XMutex<String> mutex1 = new XMutex<>(FIRST_KEY);
        XMutex<String> mutex2 = new XMutex<>(SECOND_KEY);

        WeakHashMap<XMutex<String>, WeakReference<XMutex<String>>> map = new WeakHashMap<>();

        // Act
        map.put(mutex1, new WeakReference<>(mutex1));
        map.put(mutex2, new WeakReference<>(mutex2));

        // Asserts
        Assertions.assertThat(map.size()).isEqualTo(1);
    }


    @Test
    public void testStaticFactoryMethod() throws Exception {
        // Act
        XMutex<Integer> mutex = XMutex.of(123);
        // Assert
        Assertions.assertThat(mutex.getKey()).isEqualTo(123);
    }
}