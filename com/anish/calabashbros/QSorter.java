package com.anish.calabashbros;

public class QSorter<T extends Comparable<T>> implements Sorter<T>{


    private T[] a;

    @Override
    public void load(T[] a) {
        this.a=a;
        /*for(int i=0;i<a.length;i++){
            this.a[a[i]]=b[i];
        }*/
    }

    private void swap(int i, int j) {
        T temp;
        temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        plan += "" + a[i] + "<->" + a[j] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {
        qsort(0, a.length-1);
    }

    public void qsort(int lf,int rt){
        if(lf<rt){
            int q=part(lf, rt);
            qsort(lf, q-1);
            qsort(q+1, rt);
        }
    }
    public int part(int lf,int rt){
        T pivot=a[rt];
        int i=lf-1;
        for(int j=lf;j<rt;j++){
            if(pivot.compareTo(a[j]) > 0){
                i++;
                swap(i, j);
            }
        }
        swap(i+1,rt);
        return i+1;
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}