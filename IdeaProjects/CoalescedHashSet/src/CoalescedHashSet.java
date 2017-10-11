import java.util.*;

public class CoalescedHashSet<E> extends AbstractCollection<E> implements Set<E> {
    private int currentSize;
    private int occupied;
    private int modCount;
    private HashEntry [] array;

    private static final int DEFAULT_TABLE_SIZE = 101;

    public CoalescedHashSet() {
        allocateArray(101);
        clear();
    }

    public CoalescedHashSet(Collection<? extends E> other) {
        allocateArray(nextPrime(other.size()*2));
        clear();

        for(E element : other) {
            add(element);
        }
    }

    public int size() {
        return occupied;
    }

    public Iterator<E> iterator() {
        return new HashSetIterator();
    }
    @Override
    public boolean contains(Object x) {
        return isActive(array, findPos(x));
    }

    private static boolean isActive(HashEntry [] arr, int pos) {
        return arr[pos] != null && arr[pos].isActive;
    }

    public E getMatch(E x) {
        int currentPos = findPos(x);
        if(isActive(array, currentPos))
            return (E) array[currentPos].element;
        return null;
    }
    @Override
    public boolean remove(Object x) {
        int currentPos = findPos(x);
        if(!isActive(array,currentPos))
            return false;
        array[currentPos].isActive = false;
        currentSize--;
        modCount++;

        if(currentSize < array.length / 8)
            rehash();
        return true;
    }
    @Override
    public void clear() {
        currentSize = occupied = 0;
        modCount++;
        for(int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }
    @Override
    public boolean add(E x) {
        int lastPos = findLastPos(x);
        int currentPos = findPos(x);
        if(isActive(array, currentPos))
            return false;
        if(array[currentPos] == null)
            occupied++;
        array[lastPos].nextPos = currentPos;
        array[currentPos] = new HashEntry(x, -1, true);
        currentSize++;
        modCount++;
        if(occupied > array.length / 2)
            rehash();
        return true;
    }

    private void rehash() {
        HashEntry [] oldArray = array;

        allocateArray(nextPrime(4*size()));
        currentSize = occupied = 0;
        for(int i = 0; i < oldArray.length; i++)
            if(isActive(oldArray, i))
                add((E) oldArray[i].element);
    }

    private static boolean isPrime(int n) {
        for (int m = 2; m <= n / 2; m++)
            if (n % m == 0)
                return false;
        return true;
    }

    private static int nextPrime(int n) {
        if (n % 2 == 0)
            n++;
        for (; !isPrime(n); n += 2)
            ;
        return n;
    }

    private int findLastPos(Object x) {
        int currentPos = (x == null) ?
                0 : Math.abs(x.hashCode() % array.length);
        while (array[currentPos].nextPos != -1) {
            currentPos = array[currentPos].nextPos;
        }
        return currentPos;
    }

    private void allocateArray(int arraySize) {
        array = new HashEntry[arraySize];
    }

    private int findPos(Object x) {
        int offset = 1;
        int currentPos = (x == null) ?
                0 : Math.abs(x.hashCode() % array.length);

        if (array[currentPos].nextPos != -1)
            while (array[currentPos].nextPos != -1)
                currentPos = array[currentPos].nextPos;
        else if (array[currentPos] != null)
            currentPos = 0;

        while(array[currentPos] != null) {
            if(x == null) {
                if (array[currentPos].element == null)
                    break;
            }
            else if(x.equals(array[currentPos].element))
                break;

            currentPos += offset;
            offset += 2;
            if(currentPos >= array.length)
                currentPos -= array.length;
        }
        return currentPos;
    }

    private class HashSetIterator implements Iterator<E> {
        private int expectedModCount = modCount;
        private int currentPos = -1;
        private int visited = 0;

        public boolean hasNext() {
            if(expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return visited != size();
        }

        public E next() {
            if(!hasNext())
                throw new NoSuchElementException();

            do {
                currentPos++;
            } while(currentPos < array.length &&
                    !isActive(array, currentPos));

            visited++;
            return (E) array[currentPos].element;
        }

        public void remove() {
            if(expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if(currentPos != -1 || !isActive(array, currentPos))
                //throw new IllegalAccessException();

            array[currentPos].isActive = false;
            currentSize--;
            visited--;
            modCount++;
            expectedModCount++;
        }
    }

    private static class HashEntry implements java.io.Serializable {
        Object element;
        int nextPos;
        boolean isActive;

        HashEntry(Object element) {
            this(element, -1, true);
        }

        HashEntry(Object element, boolean isActive) {
            this(element, -1, isActive);
        }

        HashEntry(Object element, int nextPos, boolean isActive) {
            this.element = element;
            this.nextPos = nextPos;
            this.isActive = isActive;

        }
    }
}