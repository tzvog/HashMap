/**
 * a class representing the simple hash set
 * @author Timothy Vogel
 */
public abstract class SimpleHashSet implements SimpleSet {

    /**
     * Describes the higher load factor of a newly created hash set
     */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /**
     * Describes the lower load factor of a newly created hash set
     */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;

    /**
     * Describes the capacity of a newly created hash set
     */
    protected static final int INITIAL_CAPACITY = 16;

    /**
     * the current high load factor
     */
    protected float highLoadFactor;

    /**
     * the current low load factor
     */
    protected float lowLoadFactor;

    /**
     * Constructs a new hash set with the default capacities given in
     * DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY
     */
    protected SimpleHashSet() {
        this(SimpleHashSet.DEFAULT_HIGHER_CAPACITY, SimpleHashSet.DEFAULT_LOWER_CAPACITY);
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    protected SimpleHashSet(float upperLoadFactor,
                            float lowerLoadFactor){
        // sets values
        this.lowLoadFactor = lowerLoadFactor;
        this.highLoadFactor = upperLoadFactor;
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index){
        return index & (this.capacity() - 1);
    }

    /**
     * gets the lower load factor of the table
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor(){
        return this.lowLoadFactor;
    }

    /**
     * gets the higher load factor of the table
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor(){
        return  this.highLoadFactor;
    }

    /**
     * checks if i add one i need rehashing
     * @return true if you need to rehash before adding new element
     */
    protected boolean additionNeedRehashing(){

        // gets the ration between our current size after adding a value
        return  (double)(this.size() + 1) / this.capacity() > this.getUpperLoadFactor();

    }

    /**
     * checks if you need to rehash after removing a value
     * @return checks if you need to remove a value
     */
    protected boolean removeNeedRehashing(){

        // gets the ration between our current size after removing a value
        return (double)(this.size() -1) / this.capacity() < this.getLowerLoadFactor();

    }
}
