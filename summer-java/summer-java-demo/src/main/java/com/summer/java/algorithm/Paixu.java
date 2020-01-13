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

        if (array.length == 0 ){
            return array;
        }
        for(int i=0;i<array.length;i++){
            for(int j=0;j<array.length-1-i;j++){
                if(array[j]>array[j+1]){
                    int temp = array[j];
                    array[j]  =array[j+1];
                    array[j+1] = temp;
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

        for (int i = 0; i < array.length; i++) {
            int minIndex = i ;
            for (int j = i; j < array.length; j++) {
                if (array[j]< array [minIndex]){
                    minIndex=j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
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

    public static  int[] c(int [] array){


        return array;

    }

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

    /**
     * 快速排序（Merge Sort）
     *1、基本思想：
     * 快速排序是我们之前学习的冒泡排序的升级，他们都属于交换类排序，都是采用不断的比较和移动来实现排序的。快速排序是一种非常高效的排序算法，它的实现，增大了记录的比较和移动的距离，将关键字较大的记录从前面直接移动到后面，关键字较小的记录从后面直接移动到前面，从而减少了总的比较次数和移动次数。同时采用“分而治之”的思想，把大的拆分为小的，小的拆分为更小的，其原理如下：对于给定的一组记录，选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，将待排序列分成两部分,一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,然后再用同样的方法递归地排序划分的两部分，直到序列中的所有记录均有序为止。
     *
     * 2、复杂度分析：
     * （1）最坏时间复杂度
     * 最坏情况是指每次区间划分的结果都是基准关键字的左边（或右边）序列为空，而另一边区间中的记录仅比排序前少了一项，即选择的关键字是待排序记录的最小值或最大值。最坏情况下快速排序的时间复杂度为。
     * （2）最好时间复杂度
     * 最好情况是指每次区间划分的结果都是基准关键字的左右两边长度相等或者相差为1，即选择的基准关键字为待排序的记录的中间值。此时进行比较次数总共为 nlogn，所以最好情况下快速排序的时间复杂度为。
     * （3）平均时间复杂度
     * 快速排序的平均时间复杂度为。在所有平均时间复杂度为O(nlogn)的算法中，快速排序的平均性能是最好的。
     * （4）空间复杂度
     * 快速排序的过程中需要一个栈空间来实现递归。最好情况，递归树的深度为，其空间复杂度也就是O(nlogn)；最坏情况下，需要进行 n-1次递归，其空间复杂度为O(n)；平均情况，空间复杂度为O(nlogn).
     * （5）基准关键字的选取，基准关键字的选取是决定快速排序算法的关键，常用的基准关键字的选取方式如下：
     * 第一种：三者取中。将序列首、尾和中间位置上的记录进行比较，选择三者中值作为基准关键字。
     * 第二种：取left和right之间的一个随机数，用n[m]作为基准关键字。采用这种方法得到的快速排序一般称为随机的快速排序。
     * @param array
     * @return
     */
    public static void quickSort(int [] array) {
        sort(array, 0, array.length -1);
    }


    public static void sort(int a[],int low, int hight){
        int i,j,index;
        if (low > hight){
            return;
        }
        i = low;
        j = hight;
        index = a [i];// 用子表的第一个记录做基准
        while (i < j) {
            while (i < j && a[j] >=index) { // 从表的两端交替向中间扫描
                j --;
            }
            if (i < j) {
                a[i++] = a[j];// 用比基准小的记录替换低位记录
            }

        }
    }



    /**
     * 斐波那契额
     * 斐波那契数列指的是这样一个数列 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233，377，610，987，1597，2584，4181，6765，10946，
     *                             17711，28657，46368……
     *
     * 特别指出：第0项是0，第1项是第一个1。
     *
     * 这个数列从第三项开始，每一项都等于前两项之和。
     * @param number
     * @return
     */
    public static long fibonacci(long number){
        if ((number==0) || (number ==1)){
            return number;
        }else {
            return fibonacci(number-1) + fibonacci(number -2);
        }
    }





    public static void main(String[] args) {
//        int [] array = {1,5,10,20,3,2,15,8,9,20,7,0};
//
////        array = Paixu.insertSort(array);
////        array = Paixu.selectionSort(array);
////        array = Paixu.shellSort(array);
//        array = Paixu.maopaoSort(array);
//
//
//        for (int i : array) {
//            System.out.println(i);
//        }

        for (int counter = 0; counter <= 10; counter++) {
            System.out.printf("Fibonacci of %d is: %d\n",
                    counter, fibonacci(counter));
        }
    }


}
