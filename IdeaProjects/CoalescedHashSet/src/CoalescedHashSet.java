import java.util.*;


public class CoalescedHashSet<E> extends AbstractCollection<E> implements Set<E> {
    private static final int DEFAULT_TABLE_SIZE = 17;
    private int currentSize; //occupied not including the deleted items
    private int occupied; // current size of the stored items
    private int modCount; // number of changes to the array
    private HashEntry[] array; //hash table

    /**
     * Default constructor
     */
    public CoalescedHashSet() {
        allocateArray(DEFAULT_TABLE_SIZE);
        clear();
    }

    /**
     * @param other: the other array to set equal to the hashset
     */
    public CoalescedHashSet(Collection<? extends E> other) {
        allocateArray(nextPrime(other.size()*2));
        clear();

        for(E element : other) {
            add(element);
        }
    }

    /**
     *
     * @param arr - the array to reference from
     * @param pos - the position of the object
     * @return whether the given item in the array is active(not deleted)
     */
    private static boolean isActive(HashEntry[] arr, int pos) {
        return arr[pos] != null && arr[pos].isActive;
    }

    /**
     * @return the number of occupied slots in the hash table
     */
    public int size() {
        return occupied;
    }

    /**
     *
     * @return the iterator of the hash table
     */
    public Iterator<E> iterator() {
        return new HashSetIterator();
    }

    /**
     * return if the hash table contains the given x object
     */
    @Override
    public boolean contains(Object x) {
        return isActive(array, findPos(x));
    }

    /**
     *
     * @param x the object to compare
     * @return the match of the object
     */
    public E getMatch(E x) {
        int currentPos = findPos(x);
        if(isActive(array, currentPos))
            return (E) array[currentPos].element;
        return null;
    }

    /**
     *
     * @param x the object to remove
     * @return whether the object was successfully removed or not
     */
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

    /**
     * clears the array
     */
    @Override
    public void clear() {
        currentSize = occupied = 0;
        modCount++;
        for(int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    /**
     *
     * @param x the item to add
     * @return whether the item was successfully added or not
     */
    @Override
    public boolean add(E x) {
        int lastPos = findLastPos(x);
        int currentPos = findPos(x);
        if(isActive(array, currentPos))
            return false;
        if(array[currentPos] == null)
            occupied++;
        if(lastPos != -1)
            array[lastPos].nextPos = currentPos;
        array[currentPos] = new HashEntry(x, -1, true);
        currentSize++;
        modCount++;

        //System.out.println(toString());

        if (occupied > array.length / 2)
            rehash();
        return true;
    }

    /**
     * resize the array
     */
    private void rehash() {
        HashEntry [] oldArray = array;

        allocateArray(4 * size());
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

    /**
     *
     * @param x the item given to find the last part of the linked list of
     * @return the position of the last object of the linked list, or -1 if no linked list is present
     */
    private int findLastPos(Object x) {
        int lastPos = (x == null) ?
                0 : Math.abs(x.hashCode() % array.length);
        if(array[lastPos] != null) {
            while (array[lastPos].nextPos != -1)
                lastPos = array[lastPos].nextPos;
            return lastPos;
        }
        return -1;
    }

    /**
     *
     * @param x the item to find the position in the hash table for
     * @return the position of where the item should be inserted into the hash table
     */
    private int findPos(Object x) {
        int offset = 1;
        int currentPos = (x == null) ?
                0 : Math.abs(x.hashCode() % array.length);
        //System.out.println("hash to: " + currentPos);
        if (array[currentPos] != null && array[currentPos].nextPos != -1)
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
            currentPos++;
//            currentPos += offset;
//            offset += 2;
            if(currentPos >= array.length)
                currentPos -= array.length;
        }
        //System.out.println("insert to: " + currentPos);
        return currentPos;
    }

    /**
     *
     * @param arraySize the size of the array to instantiate
     */
    private void allocateArray(int arraySize) {
        array = new HashEntry[arraySize];
    }

    /**
     * @return the string representation of the hash table, used mostly for debugging purposes
     */
    public String toString() {
        String result = "";
        for (int i = 0; i < array.length; i++)
            result += i + " : " + (array[i] == null ? "null" : array[i].element + " : " + array[i].nextPos) + "\n";
        return result;
    }

    private static class HashEntry implements java.io.Serializable {
        Object element;
        int nextPos;
        boolean isActive;

        HashEntry() {
            this(null, -1, true);
        }

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
            if(currentPos == -1 || !isActive(array, currentPos))
                throw new IllegalStateException();

            array[currentPos].isActive = false;
            currentSize--;
            visited--;
            modCount++;
            expectedModCount++;
        }
    }
}