import time


def sort(arr, n):
    start = time.time()
    temp_arr = [0] * n
    inversions = helper(arr, temp_arr, 0, n - 1)
    end = time.time()

    return tuple([inversions, round(1000*(end-start))])


def helper(arr, temp_arr, left, right):
    inv_count = 0

    if left < right:
        mid = (left + right) // 2

        inv_count += helper(arr, temp_arr, left, mid)

        inv_count += helper(arr, temp_arr, mid + 1, right)

        inv_count += merge(arr, temp_arr, left, mid, right)
    return inv_count


def merge(arr, temp_arr, left, mid, right):
    i = left
    j = mid + 1
    k = left
    inv_count = 0

    while i <= mid and j <= right:
        if arr[i] <= arr[j]:
            temp_arr[k] = arr[i]
            k += 1
            i += 1
        else:
            temp_arr[k] = arr[j]
            k += 1
            j += 1
            inv_count += (mid - i + 1)

    while i <= mid:
        temp_arr[k] = arr[i]
        k += 1
        i += 1

    while j <= right:
        temp_arr[k] = arr[j]
        k += 1
        j += 1

    for t in range(left, right + 1):
        arr[t] = temp_arr[t]

    return inv_count


# arr = [1, 20, 6, 4, 5]
# n = len(arr)
# result = sort(arr, n)
# print("Number of inversions are", result)

if __name__ == "__main__":
    for i in range(1, 7):
        with open(f"dataset_s/f{i}.txt", "r") as fin:
            lines = fin.readlines()

        arr = []
        for line in lines:
            arr.append(int(line))

        data = sort(arr, len(arr))
        print(f"Dataset:\tdataset_s/f{i}.txt\n"
              f"Inversions with MergeSort:\t{data[0]}\n"
              f"Time with MergeSort (rounded ms):\t{data[1]}\n"
              f"----------")
