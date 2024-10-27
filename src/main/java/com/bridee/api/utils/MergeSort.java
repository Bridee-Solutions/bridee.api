package com.bridee.api.utils;

public class MergeSort {

    public static <T extends Comparable<T>> void mergeSort(ListaObj<T> lista){
        T[] vetor = lista.toArray();
        mergeSort(0, vetor.length, vetor);
    }

    private static <T extends Comparable<T>> void mergeSort(int inicio , int fim, T[] vetor){
        if (inicio < fim - 1){
            int meio = (inicio + fim) / 2;
            mergeSort(inicio, meio, vetor);
            mergeSort(meio, fim, vetor);
            intercala(inicio, meio, fim, vetor);
        }
    }

    private static <T extends Comparable<T>> void intercala(int inicio, int meio, int fim, T[] vetor) {
        int i = inicio;
        int m = meio;
        int k = 0;
        T[] aux = (T[]) new Comparable[vetor.length];
        while(i < meio && fim > m){
            if (vetor[i].compareTo(vetor[m]) < 0){
                aux[k++] = vetor[i++];
            }else{
                aux[k++] = vetor[m++];
            }
        }
        while(i < meio){
            aux[k++] = vetor[i++];
        }
        while (m < fim){
            aux[k++] = vetor[m++];
        }
        for (int h = inicio; h < fim; h++){
            vetor[h] = aux[h - inicio];
        }
    }

}
