
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MetodosOrdenacao {
    static ArrayList<Integer> arrayCrescente = new ArrayList<>();
    static ArrayList<Integer> arrayDecrescente = new ArrayList<>();
    static ArrayList<Integer> arraySemRepetidos = new ArrayList<>();
    static ArrayList<Integer> arrayComRepetidos = new ArrayList<>();

    public static void main(String[] args) {
        createArrayCrescente(128);
        createArrayDecrescente(128);
        createArraySemRepetidos(65536);
        createArrayComRepetidos(128);

        System.out.println("Original array (crescente): " + arrayCrescente);
        System.out.println("Original array (decrescente): " + arrayDecrescente);
        System.out.println("Original array (sem repetidos): " + arraySemRepetidos);
        System.out.println("Original array (com repetidos): " + arrayComRepetidos);

        System.out.println("Bubble Sort: " + bubbleSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::bubbleSort, new ArrayList<>(arraySemRepetidos)));

        System.out.println("Insertion Sort: " + insertionSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::insertionSort, new ArrayList<>(arraySemRepetidos)));

        System.out.println("Selection Sort: " + selectionSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::selectionSort, new ArrayList<>(arraySemRepetidos)));

        System.out.println("Heap Sort: " + heapSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::heapSort, new ArrayList<>(arraySemRepetidos)));

        System.out.println("Shell Sort: " + shellSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::shellSort, new ArrayList<>(arraySemRepetidos)));

        System.out.println("Merge Sort: " + mergeSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::mergeSort, new ArrayList<>(arraySemRepetidos)));
        
        System.out.println("Quick Sort: " + quickSort(new ArrayList<>(arraySemRepetidos)));
        System.out.println("Tempo de execução (ns): " + measureTime(MetodosOrdenacao::quickSort, new ArrayList<>(arraySemRepetidos)));
    }

    public static ArrayList<Integer> generateRandomArray(int n) {
        ArrayList<Integer> array = new ArrayList<>(n);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            array.add(rand.nextInt(n * 10) + 1);
        }
        return array;
    }

    public static void createArrayCrescente(int n) {
        arrayCrescente = generateRandomArray(n);
        Collections.sort(arrayCrescente);
    }

    public static void createArrayDecrescente(int n) {
        arrayDecrescente = generateRandomArray(n);
        arrayDecrescente.sort(Collections.reverseOrder());
    }

    public static void createArraySemRepetidos(int n) {
        Set<Integer> set = new HashSet<>();
        Random rand = new Random();
        while (set.size() < n) {
            set.add(rand.nextInt(n * 10) + 1);
        }
        arraySemRepetidos = new ArrayList<>(set);
    }

    public static void createArrayComRepetidos(int n) {
        arrayComRepetidos = new ArrayList<>(n);
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arrayComRepetidos.add(rand.nextInt(n / 2 + 1) + 1);
        }
    }

    // Bubble Sort
    public static ArrayList<Integer> bubbleSort(ArrayList<Integer> arr) {
        int n = arr.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr.get(j) > arr.get(j + 1)) {
                    Collections.swap(arr, j, j + 1);
                }
            }
        }
        return arr;
    }

    // Insertion Sort
    public static ArrayList<Integer> insertionSort(ArrayList<Integer> arr) {
        int n = arr.size();
        for (int i = 1; i < n; i++) {
            int key = arr.get(i);
            int j = i - 1;
            while (j >= 0 && arr.get(j) > key) {
                arr.set(j + 1, arr.get(j));
                j = j - 1;
            }
            arr.set(j + 1, key);
        }
        return arr;
    }

    // Merge Sort
    public static ArrayList<Integer> mergeSort(ArrayList<Integer> arr) {
        if (arr.size() <= 1) {
            return arr;
        }

        int mid = arr.size() / 2;
        ArrayList<Integer> left = new ArrayList<>(arr.subList(0, mid));
        ArrayList<Integer> right = new ArrayList<>(arr.subList(mid, arr.size()));

        return merge(mergeSort(left), mergeSort(right));
    }

    public static ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right) {
        ArrayList<Integer> result = new ArrayList<>();
        int leftIndex = 0, rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) < right.get(rightIndex)) {
                result.add(left.get(leftIndex));
                leftIndex++;
            } else {
                result.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        result.addAll(left.subList(leftIndex, left.size()));
        result.addAll(right.subList(rightIndex, right.size()));
        return result;
    }

    // Quick Sort
    public static ArrayList<Integer> quickSort(ArrayList<Integer> arr) {
        if (arr.size() <= 1) {
            return arr;
        }

        int pivot = arr.get(arr.size() / 2);
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();

        for (int i = 0; i < arr.size(); i++) {
            if (i == arr.size() / 2) continue;

            if (arr.get(i) < pivot) {
                left.add(arr.get(i));
            } else {
                right.add(arr.get(i));
            }
        }

        ArrayList<Integer> result = new ArrayList<>(quickSort(left));
        result.add(pivot);
        result.addAll(quickSort(right));
        return result;
    }

    // Heap Sort
    public static ArrayList<Integer> heapSort(ArrayList<Integer> arr) {
        int n = arr.size();

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            Collections.swap(arr, 0, i); // Move current root to end
            heapify(arr, i, 0); // Call max heapify on the reduced heap
        }

        return arr;
    }

    public static void heapify(ArrayList<Integer> arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr.get(left) > arr.get(largest)) {
            largest = left;
        }

        if (right < n && arr.get(right) > arr.get(largest)) {
            largest = right;
        }

        if (largest != i) {
            Collections.swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    // Selection Sort
    public static ArrayList<Integer> selectionSort(ArrayList<Integer> arr) {
        int n = arr.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr.get(j) < arr.get(minIndex)) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Collections.swap(arr, i, minIndex);
            }
        }

        return arr;
    }

    // Shell Sort
    public static ArrayList<Integer> shellSort(ArrayList<Integer> arr) {
        int n = arr.size();
        int gap = n / 2;

        while (gap > 0) {
            for (int i = gap; i < n; i++) {
                int temp = arr.get(i);
                int j;
                for (j = i; j >= gap && arr.get(j - gap) > temp; j -= gap) {
                    arr.set(j, arr.get(j - gap));
                }
                arr.set(j, temp);
            }
            gap /= 2;
        }
        return arr;
    }

    // Função para medir o tempo de execução
    @FunctionalInterface
    public interface SortingFunction {
        ArrayList<Integer> apply(ArrayList<Integer> arr);
    }

    public static long measureTime(SortingFunction fn, ArrayList<Integer> arr) {
        long start = System.nanoTime();
        fn.apply(arr);
        long end = System.nanoTime();
        return end - start; // Time in nanoseconds
    }
}
