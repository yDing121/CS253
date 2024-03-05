from heapq import heappush, heappop
from bisect import bisect, insort


def getNumOfInversions(A):
    N = len(A)
    if N <= 1:
        return 0

    sortList = []
    result = 0

    # Heapsort, O(N*log(N))
    for i, v in enumerate(A):
        heappush(sortList, (v, i))

    # Create a sorted list of indexes
    x = []
    while sortList:
        # O(log(N))
        v, i = heappop(sortList)

        # Find the current minimum's index
        # the index y can represent how many minimums on the left
        y = bisect(x, i)

        # i can represent how many elements on the left
        # i - y can find how many bigger nums on the left
        result += i - y

        insort(x, i)

    return result


# Driver Code
if __name__ == '__main__':
    with open("dataset_s/f1.txt", "r") as fin:
        lines = fin.readlines()
    arr = []

    for line in lines:
        arr.append(int(line))

    print(arr)
    result = getNumOfInversions(arr)
    print(f'Number of inversions are {result}')
    print(arr)
