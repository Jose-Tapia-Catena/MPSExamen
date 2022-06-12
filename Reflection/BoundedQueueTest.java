package org.example.reflection;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BoundedQueueTest {

    @Test
    public void put_addOneElementEmptyBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(4);
        int nextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");
        int nextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int capacity = (int) ReflectionTestUtils.getField(cola, "capacity");

        cola.put(1);
        int newNextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int newNextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");

        assertThat(newNextFreeSlot).isEqualTo((nextFreeSlot + 1) % capacity);
        assertThat(nextItem).isEqualTo(newNextItem);
        assertThat(cola.getNumberOfItems()).isEqualTo(1);
    }

    @Test
    public void put_addNLessOneElementBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(3);
        int nextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");
        int nextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int capacity = (int) ReflectionTestUtils.getField(cola, "capacity");

        cola.put(1);
        cola.put(2);
        int newNextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int newNextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");

        assertThat(newNextFreeSlot).isEqualTo((nextFreeSlot + 2) % capacity);
        assertThat(nextItem).isEqualTo(newNextItem);
        assertThat(cola.getNumberOfItems()).isEqualTo(2);
    }

    @Test
    public void put_addNElementBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(3);
        int nextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");
        int nextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int capacity = (int) ReflectionTestUtils.getField(cola, "capacity");

        cola.put(1);
        cola.put(2);
        cola.put(3);
        int newNextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int newNextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");

        assertThat(newNextFreeSlot).isEqualTo((nextFreeSlot + 3) % capacity);
        assertThat(nextItem).isEqualTo(newNextItem);
        assertThat(cola.getNumberOfItems()).isEqualTo(capacity);
    }

    @Test
    public void put_addNPlusOneElementBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(3);
        int nextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");
        int nextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int capacity = (int) ReflectionTestUtils.getField(cola, "capacity");

        cola.put(1);
        cola.put(2);
        cola.put(3);

        assertThrows(FullQueueException.class, () -> cola.put(4));
    }

    @Test
    public void get_OneElementFullBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(3);
        int nextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");
        int nextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int capacity = (int) ReflectionTestUtils.getField(cola, "capacity");

        cola.put(1);
        cola.put(2);
        cola.put(3);
        cola.get();
        int newNextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int newNextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");

        assertThat(newNextFreeSlot).isEqualTo(nextFreeSlot);
        assertThat(newNextItem).isEqualTo((nextItem+1)%capacity);
        assertThat(cola.getNumberOfItems()).isEqualTo(capacity-1);
    }

    @Test
    public void get_OneElementInANElementBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(3);
        int nextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");
        int nextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int capacity = (int) ReflectionTestUtils.getField(cola, "capacity");

        cola.put(1);
        int numberOfElements = cola.getNumberOfItems();

        cola.get();
        int newNextItem = (int) ReflectionTestUtils.getField(cola, "nextItem");
        int newNextFreeSlot = (int) ReflectionTestUtils.getField(cola, "nextFreeSlot");

        assertThat(newNextFreeSlot).isEqualTo(nextFreeSlot + 1);
        assertThat(newNextItem).isEqualTo((nextItem+1)%capacity);
        assertThat(cola.getNumberOfItems()).isEqualTo(numberOfElements-1);
    }

    @Test
    public void get_EmptyBoundedQueue(){
        BoundedQueue<Integer> cola = new BoundedQueue<>(3);
        assertThrows(EmptyQueueException.class, () -> cola.get());
    }

}