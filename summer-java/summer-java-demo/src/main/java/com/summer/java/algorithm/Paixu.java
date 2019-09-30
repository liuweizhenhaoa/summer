package com.summer.java.algorithm;

public class Paixu {


    /**
     *冒泡排序（Bubble Sort）
     * 冒泡排序是一种简单的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。走访数列的工作是重复地进行直到没有再需要交换，
     * 也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。
     */
    public static int[] maopaoSort(int [] array){
        if (array.length == 0) return array;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length-1-i; j++) {
                if (array [j] > array [j+1]) {
                    int temp = array [j+1];
                    array [j+1] = array [j];
                    array [j] = temp;
                }
            }
        }
        return array;
    }

    public int [] b(int[] array){

        for(int i=0;i<array.length; i++){
            for(int j=0;j<array.length-1-i;j++){
                if(array[j]>array[j+1]){
                    int temp = array[j+1];
                    array[j+1]=array[j];
                    array [j] = temp;
                }
            }
        }
        return array;
    }


    /**
     * 选择排序
     *
     * 2、选择排序（Selection Sort）
     * 表现最稳定的排序算法之一，因为无论什么数据进去都是O(n2)的时间复杂度，所以用到它的时候，数据规模越小越好。唯一的好处可能就是不占用额外的内存空间了吧。理论上讲
     * ，选择排序可能也是平时排序一般人想到的最多的排序方法了吧。
     * 选择排序(Selection-sort)是一种简单直观的排序算法。它的工作原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，再从剩余未排序元素中
     * 继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
     * @return
     */
    public static int[] selectionSort(int[] array){
        if (array.length == 0)
            return array;

        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array [j]< array [minIndex]) {//找到最小的数
                    minIndex = j;//将最小数的索引保存
                }
            }
            int temp = array [minIndex];
            array [minIndex] = array [i];
            array [i] = temp;
        }

        return array;
    }

    public int[] a(int [] array){

        for(int i=0;i<array.length;i++){

            int minIndex = i;
            for(int j=i;j<array.length; j++){
                if (array[j]<array [minIndex]){
                    minIndex = j;
                }
            }
            int temp = array [minIndex];
            array [minIndex] = array [i];
            array [i] = temp;
        }


        return array;
    }

    /**
     * 插入排序（Insertion-Sort）
     * 的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。插入排序在实现上，
     * 通常采用in-place排序（即只需用到O(1)的额外空间的排序），因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，为最新元素提供插入空间。
     * @param array
     * @return
     */
    public static int [] insertSort(int [] array) {
        int current ;
        for (int i = 0; i < array.length-1; i++){
            current = array [i+1];
            int preIndex = i;
            while (preIndex>=0 && current < array [preIndex]){
                array[preIndex+1] = array [preIndex];
                preIndex--;
            }
            array [preIndex+1] = current;
        }
        return array;
    }

//    public static  int[] c(int [] array){
//
//    }

    /**
     * 希尔排序（Shell Sort）
     * 希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过改进之后的一个更高效的版本，
     * 也称为缩小增量排序，同时该算法是冲破O(n2）的第一批算法之一。它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。
     */
    public static int[] shellSort(int[] array){

        int len = array.length;
        int temp,gap = len / 2;

        while (gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array [i];
                int preIndex = i-gap;
                while (preIndex>=0 && array [preIndex]> temp){
                    array [preIndex+gap] = array [preIndex];
                    preIndex -=gap;
                }
                array [preIndex+gap] = temp;
            }
            gap /=2;
        }

        return array;
    }



    /**
     * 归并排序（Merge Sort）
     *和选择排序一样，归并排序的性能不受输入数据的影响，但表现比选择排序好的多，因为始终都是O(n log n）的时间复杂度。代价是需要额外的内存空间。
     *
     * 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。归并排序是一种稳定的排序方法。将已有序的子序列合并，
     * 得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。
     * @param array
     * @return
     */
    public static int [] mergeSort(int [] array) {


        return array;
    }


    public static void main(String[] args) {
        int [] array = {1,5,10,20,3,2,15,8,9,20,7,0};

//        array = Paixu.insertSort(array);
//        array = Paixu.selectionSort(array);
//        array = Paixu.shellSort(array);
        array = Paixu.maopaoSort(array);


        for (int i : array) {
            System.out.println(i);
        }
    }


}
