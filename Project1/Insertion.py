import time


def sort(arr):
    inversions = 0

    start = time.time()
    for j in range(1, len(arr)):
        key = arr[j]
        i = j-1

        while i >= 0 and arr[i] > key:
            arr[i+1] = arr[i]
            i -= 1
            inversions += 1

        arr[i+1] = key
    end = time.time()
    return tuple([inversions, round(1000*(end-start))])
