import time
from bisect import bisect, insort
from heapq import heappush, heappop


def sort(arr):
    start = time.time()
    if len(arr) <= 1:
        return 0

    # We are going to use a regular list as a priority queue/heap via the heapq library
    # I don't consider this cheating because java has a built-in Priority Queue class that does the same.
    slist = []
    inversions = 0

    for idx, val in enumerate(arr):
        heappush(slist, (val, idx))

    arr_sorted = []

    while slist:
        (val, idx) = heappop(slist)
        y = bisect(arr_sorted, idx)
        inversions += idx - y
        insort(arr_sorted, idx)
    end = time.time()

    for i in range(len(arr_sorted)):
        arr[i] = arr_sorted[i]

    return tuple([inversions, round(1000*(end-start))])


if __name__ == "__main__":
    for i in range(1, 2):
        with open(f"dataset_s/f{i}.txt", "r") as fin:
            lines = fin.readlines()

        arr = []
        for line in lines:
            arr.append(int(line))

        data = sort(arr)
        print(arr)
        print(f"Dataset:\tdataset_s/f{i}.txt\n"
              f"Inversions with Heapsort:\t{data[0]}\n"
              f"Time with Heapsort (rounded ms):\t{data[1]}\n"
              f"----------")
