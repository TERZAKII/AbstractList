package PA;

import java.util.Iterator;
import java.util.NoSuchElementException;

abstract class MyAbstractList<E> {

    Node<E> head;
    Node<E> tail;
    int size = 0;

    public MyAbstractList(E[] objects) {
        for (int i = 0; i < objects.length; i++)
            addLast(objects[i]);
    }

    /**
     * Return true if this list doesn't contain any elements
     */
    public abstract boolean isEmpty();

    /**
     * Return the number of elements in this list
     */
    public abstract int size();

    /**
     * Clear the list
     */
    public abstract void clear();

    /**
     * Print the list
     */
    public abstract void print();

    /**
     * Return the element from this list at the specified index
     */
    public abstract E get(int index);

    /**
     * Return the first element from this list
     */
    public abstract E getFirst();

    /**
     * Return the last element from this list
     */
    public abstract E getLast();

    /**
     * Add a new element at the beginning of this list
     */
    public abstract void addFirst(E e);

    /**
     * Add a new element at the end of this list
     */
    public abstract void addLast(E e);

    /**
     * Add a new element at the specified index in this list
     */
    public abstract void add(int index, E e);

    /**
     * Remove the element from the beginning in this list.
     * Return the element that was removed from the list.
     */
    public abstract E removeFirst();

    /**
     * Remove the element from the end in this list.
     * Return the element that was removed from the list.
     */
    public abstract E removeLast();

    /**
     * Remove the element at the specified position in this list.
     * Return the element that was removed from the list.
     */
    public abstract E remove(int index);

    /**
     * Return true if this list contains the element
     */
    public abstract boolean contains(E e);

    /**
     * Return the index of the first matching element in this list.
     * Return -1 if no match.
     */
    public abstract int indexOf(E e);

    /**
     * Return the index of the last matching element
     * in this list. Return -1 if no match.
     */
    public abstract int lastIndexOf(E e);

    /**
     * Replace the element at the specified position
     * in this list with the specified element.
     */
    public abstract E set(int index, E e);

    /**
     * Override iterator() defined in Iterable
     * @return
     */
    public Iterator<E> iterator(){
        return new LLIterator();
    }

    private class LLIterator implements Iterator<E> {
        boolean canRemove = false;
        int previousLoc = -1;
        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E data = current.data;
                current = current.next;
                previousLoc++;
                canRemove = true;
                return data;
            }
            throw new NoSuchElementException("There are no next element");
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("You can't delete element before first next() method call");
            }

            MyAbstractList.this.remove(previousLoc);
            canRemove = false;
        }
    }
}


class Node<E> {
    E data;
    Node<E> next;

    public Node(E data) {
        this.data = data;
    }
}

class MyLL<E> extends MyAbstractList<E> {

    public MyLL(E[] objects) {
        super(objects);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public void print() {
        // your code goes here
        Node<E> current = head;
        System.out.print("[");
        for (int i = 0; i < size; i++) {
            System.out.print(current.data);
            if (i < size - 1) {
                System.out.print(", ");
            }
            current = current.next;
        }
        System.out.println("]");
    }

    @Override
    public E get(int index) {
        // your code goes here
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public E getFirst() {
        // your code goes here
        if (head == null) {
            return null;
        }
        return head.data;
    }

    @Override
    public E getLast() {
        // your code goes here
        if (tail == null) {
            return null;
        }
        return tail.data;
    }

    @Override
    public void addFirst(E e) {
        // your code goes here
        Node<E> newNode = new Node<>(e);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        // your code goes here
        Node<E> newNode = new Node<>(e);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E e) {
        // your code goes here
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == 0) {
            addFirst(e);
        } else if (index == size) {
            addLast(e);
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            Node<E> newNode = new Node<>(e);
            newNode.next = current.next;
            current.next = newNode;
            size++;
        }
    }

    @Override
    public E removeFirst() {
        // your code goes here
        if (head == null) {
            return null;
        }
        E data = head.data;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
        }
        size--;
        return data;
    }

    @Override
    public E removeLast() {
        // your code goes here
        if (tail == null) {
            return null;
        }
        E data = tail.data;
        if (head == tail) {
            head = tail = null;
        } else {
            Node<E> current = head;
            while (current.next != tail) {
                current = current.next;
            }
            current.next = null;
            tail = current;
        }
        size--;
        return data;
    }

    @Override
    public E remove(int index) {
        // your code goes here
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        E removedElement;
        if (index == 0) {
            removedElement = removeFirst();
        } else if (index == size - 1) {
            removedElement = removeLast();
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            removedElement = current.next.data;
            current.next = current.next.next;
            size--;
        }
        return removedElement;
    }

    @Override
    public boolean contains(E e) {
        // your code goes here
        Node<E> current = head;
        while (current != null) {
            if (current.data.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int indexOf(E e) {
        // your code goes here
        Node<E> current = head;
        int index = 0;
        while (current != null) {
            if (current.data.equals(e)) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E e) {
        // your code goes here
        Node<E> current = head;
        int index = -1;
        int i = 0;
        while (current != null) {
            if (current.data.equals(e)) {
                index = i;
            }
            current = current.next;
            i++;
        }
        return index;
    }


    @Override
    public E set(int index, E e) {
        // your code goes here
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E replacedElement = current.data;
        current.data = e;
        return replacedElement;
    }
}

public class PA12A {
    public static void main(String[] args) {
        MyLL myLL = new MyLL(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 5, 9});
        System.out.println("myLL.size(): " + myLL.size());
        System.out.println("myLL.isEmpty(): " + myLL.isEmpty());
        myLL.print();
        System.out.println("myLL.get(2): " + myLL.get(2));
        System.out.println("myLL.getFirst(): " + myLL.getFirst());
        System.out.println("myLL.getLast(): " + myLL.getLast());

        myLL.add(2, 222);
        System.out.println("After : add(2, 222): " );
        myLL.print();
        myLL.addFirst(111);
        myLL.addFirst( 999);
        System.out.println("After : addFirst(111) and addFirst(999): " );
        myLL.print();

        System.out.println("myLL.remove(2): " + myLL.remove(2));
        System.out.println("myLL.removeFirst(): " + myLL.removeFirst());
        System.out.println("myLL.removeLast(): " + myLL.removeLast());
        myLL.print();

        System.out.println("myLL.contains(111): " + myLL.contains(111));
        System.out.println("myLL.contains(5): " + myLL.contains(5));
        System.out.println("myLL.indexOf(5): " + myLL.indexOf(5));
        System.out.println("myLL.lastIndexOf(5): " + myLL.lastIndexOf(5));
        System.out.println("myLL.lastIndexOf(88): " + myLL.lastIndexOf(88));

        System.out.println("myLL.set(2, 22): " + myLL.set(2, 22));
        System.out.println("myLL.set(1, 3333): " + myLL.set(1, 3333));

        Iterator<Integer> iterator = myLL.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        myLL.clear();
        System.out.println("myLL.isEmpty(): " + myLL.isEmpty());
        myLL.print();
    }
}
