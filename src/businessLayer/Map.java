package businessLayer;


import java.io.Serializable;
import java.util.ArrayList;

/**
 *A node in the hashmap
 */
class HashNode<K, V> implements Serializable
{
    K key;
    V value;

    HashNode<K, V> next;

    public HashNode(K key, V value)
    {
        this.key = key;
        this.value = value;
    }
}

/**
 * @class Map - a class that implements the logic for a hashtable with chaining method
 * for colision detection
 */
public class Map<K, V> implements Serializable {
    //the bucketArray stores arrays of chains
    private ArrayList<HashNode<K, V>> bucketArray;

    //the chapacity of the hashtable
    private int numBuckets;

    //size of the hash table
    private int size;

    public Map()
    {
        bucketArray = new ArrayList<>();
        numBuckets = 20;
        size = 0;

        // Create empty chains
        for (int i = 0; i < numBuckets; i++)
            bucketArray.add(null);
    }

    public int size() { return size; }

    /**
     * getBucketIndex method - this method uses the hascode of the key to find
     * the appropiate bucket for the requested value V.
     */
    private int getBucketIndex(K key)
    {
        int hashCode = key.hashCode();
        int index = hashCode % numBuckets;
        return index;
    }

    /**
     * remove method - used to remove the key-value pair with the key passed as argument
     * @param key - the desired key to be removed along with the value
     * @return - the value of the key
     */
    public V remove(K key)
    {

        int bucketIndex = getBucketIndex(key);

        HashNode<K, V> head = bucketArray.get(bucketIndex);

        HashNode<K, V> prev = null;
        while (head != null)
        {

            if (head.key.equals(key))// key is found
                break;

            prev = head;
            head = head.next;
        }

        // if key was not there
        if (head == null)
            return null;

        // reduce size
        size--;

        // remove key
        if (prev != null)
            prev.next = head.next;
        else
            bucketArray.set(bucketIndex, head.next);

        return head.value;
    }

    public V get(K key)
    {

        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);


        while (head != null)
        {
            if (head.key.equals(key))
                return head.value;
            head = head.next;
        }

        // If key not found
        return null;
    }

    /**
     * put method - this method is used to put a value in the hashtable at a
     * possition indicated by the computation of the key passed as parameter
     * @param key
     * @param value
     */
    public void put(K key, V value)
    {

        int bucketIndex = getBucketIndex(key);
        HashNode<K, V> head = bucketArray.get(bucketIndex);


        while (head != null)
        {
            if (head.key.equals(key))
            {
                head.value = value;
                return;
            }
            head = head.next;
        }


        size++;
        head = bucketArray.get(bucketIndex);
        HashNode<K, V> newNode = new HashNode<K, V>(key, value);
        newNode.next = head;
        bucketArray.set(bucketIndex, newNode);

        /*
         * if the loads factor exceeds 0.7 double the capacity
         */
        if ((1.0*size)/numBuckets >= 0.7)
        {
            ArrayList<HashNode<K, V>> temp = bucketArray;
            bucketArray = new ArrayList<>();
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray.add(null);

            for (HashNode<K, V> headNode : temp)
            {
                while (headNode != null)
                {
                    put(headNode.key, headNode.value);
                    headNode = headNode.next;
                }
            }
        }
    }


}
