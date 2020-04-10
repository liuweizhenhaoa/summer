package com.summer.java.lsh;

import info.debatty.java.lsh.LSHMinHash;
import info.debatty.java.lsh.MinHash;

import java.io.*;
import java.util.Random;

public class SimpleLSHMinHashExample {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        SimpleLSHMinHashExample.test();
        SimpleLSHMinHashExample.test3();
    }

    public static void test2(){
        // proportion of 0's in the vectors
        // if the vectors are dense (lots of 1's), the average jaccard similarity
        // will be very high (especially for large vectors), and LSH
        // won't be able to distinguish them
        // as a result, all vectors will be binned in the same bucket...
        double sparsity = 0.75;

        // Number of sets
        int count = 10000;

        // Size of vectors
        int n = 100;

        // LSH parameters
        // the number of stages is also sometimes called thge number of bands
        int stages = 2;

        // Attention: to get relevant results, the number of elements per bucket
        // should be at least 100
        int buckets = 10;

        // Let's generate some random sets
        boolean[][] vectors = new boolean[count][n];
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < n; j++) {
                vectors[i][j] = rand.nextDouble() > sparsity;
            }
        }

        // Create and configure LSH algorithm
        LSHMinHash lsh = new LSHMinHash(stages, buckets, n);

        int[][] counts = new int[stages][buckets];

        // Perform hashing
        for (boolean[] vector : vectors) {
            int[] hash = lsh.hash(vector);

            for (int i = 0; i < hash.length; i++) {
                counts[i][hash[i]]++;
            }

            print(vector);
            System.out.print(" : ");
            print(hash);
            System.out.print("\n");
        }

        System.out.println("Number of elements per bucket at each stage:");
        for (int i = 0; i < stages; i++) {
            print(counts[i]);
            System.out.print("\n");
        }
    }
    
    static void print(int[] array) {
        System.out.print("[");
        for (int v : array) {
            System.out.print("" + v + " ");
        }
        System.out.print("]");
    }
    
    static void print(boolean[] array) {
        System.out.print("[");
        for (boolean v : array) {
            System.out.print(v ? "1" : "0");
        }
        System.out.print("]");
    }

    static void println(int[] array) {
        System.out.print("[");
        for (int v : array) {
            System.out.print("" + v + " ");
        }
        System.out.println("]");
    }

    public static void test3() throws IOException, ClassNotFoundException {
        // Create a single random boolean vector
        int n = 100;
        double sparsity = 0.75;
        boolean[] vector = new boolean[n];
        Random rand = new Random();
        for (int j = 0; j < n; j++) {
            vector[j] = rand.nextDouble() > sparsity;
        }

        // Create and configure LSH
        int stages = 2;
        int buckets = 10;
        LSHMinHash lsh = new LSHMinHash(stages, buckets, n);
        println(lsh.hash(vector));

        // Create another LSH object
        // as the parameters of the hashing function are randomly initialized
        // these two LSH objects will produce different hashes for the same
        // input vector!
        LSHMinHash other_lsh = new LSHMinHash(stages, buckets, n);
        println(other_lsh.hash(vector));

        // Moreover, signatures produced by different LSH objects cannot
        // be used to compute estimated similarity!
        // The solution is to serialize and save the object, so it can be
        // reused later...
        File tempfile = File.createTempFile("lshobject", ".ser");
        FileOutputStream fout = new FileOutputStream(tempfile);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(lsh);
        oos.close();
        System.out.println(
                "LSH object serialized to " + tempfile.getAbsolutePath());

        FileInputStream fin = new FileInputStream(tempfile);
        ObjectInputStream ois = new ObjectInputStream(fin);
        LSHMinHash saved_lsh = (LSHMinHash) ois.readObject();
        println(saved_lsh.hash(vector));
    }



    public static void test(){
        // Number of sets
        int count = 2000;

        // Size of dictionary
        int n = 100;

        // Number of buckets
        // Attention: to get relevant results, the number of elements per bucket
        // should be at least 100
        int buckets = 10;

        // Let's generate some random sets
        boolean[][] vectors = new boolean[count][];
        Random r = new Random();

        // To get some interesting measures, we first generate a single
        // sparse random vector
        vectors[0] = new boolean[n];
        for (int j = 0; j < n; j++) {
            vectors[0][j] = (r.nextInt(10) == 0);
        }

        // Then we generate the other vectors, which have a reasonable chance
        // to look like the first one...
        for (int i = 1; i < count; i++) {
            vectors[i] = new boolean[n];

            for (int j = 0; j < n; j++) {
                vectors[i][j] = (r.nextDouble() <= 0.7 ? vectors[0][j] : (r.nextInt(10) == 0));
            }
        }

        // Now we can proceed to LSH binning
        // We will test multiple stages
        for (int stages = 1; stages <= 10; stages++) {

            // Compute the LSH hash of each vector
            LSHMinHash lsh = new LSHMinHash(stages, buckets, n);
            int[][] hashes = new int[count][];
            for (int i = 0; i < count; i++) {
                boolean[] vector = vectors[i];
                hashes[i] = lsh.hash(vector);
            }

            // We now have the LSH hash for each input set
            // Let's have a look at how similar sets (according to Jaccard
            // index) were binned...
            int[][] results = new int[11][2];
            for (int i = 0; i < vectors.length; i++) {
                boolean[] vector1 = vectors[i];
                int[] hash1 = hashes[i];

                for (int j = 0; j < i; j++) {
                    boolean[] vector2 = vectors[j];
                    int[] hash2 = hashes[j];

                    // We compute the similarity between each pair of sets
                    double similarity = MinHash.jaccardIndex(vector1, vector2);

                    // We count the number of pairs with similarity 0.1, 0.2,
                    // 0.3, etc.
                    results[(int) (10 * similarity)][0]++;

                    // Do they fall in the same bucket for one of the stages?
                    for (int stage = 0; stage < stages; stage++) {
                        if (hash1[stage] == hash2[stage]) {
                            results[(int) (10 * similarity)][1]++;
                            break;
                        }
                    }
                }
            }

            // Now we can display (and plot in Gnuplot) the result:
            // For pairs that have a similarity x, the probability of falling
            // in the same bucket for at least one of the stages is y
            for (int i = 0; i < results.length; i++) {
                double similarity = (double) i / 10;

                double probability = 0;
                if (results[i][0] != 0) {
                    probability = (double) results[i][1] / results[i][0];
                }
                System.out.println("" + similarity + "\t" + probability + "\t" + stages);
            }

            // Separate the series for Gnuplot...
            System.out.print("\n");
        }
    }
}