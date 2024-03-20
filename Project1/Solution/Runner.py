import Insertion
import Merge
import Heap


def formatData(data, name, fnum):
    return (f"Dataset:\tdataset_s/f{fnum}.txt\n"
          f"Inversions with {name}:\t{data[0]}\n"
          f"Time with {name} (rounded ms):\t{data[1]}\n"
          f"----------")


fout = open("log.log", "a+")

for i in range(1, 7):
    print("===="*10)
    fout.write("===="*10+"\n")
    with open(f"dataset_s/f{i}.txt", "r") as fin:
        lines = fin.readlines()

    arr = []
    for line in lines:
        arr.append(int(line))

    m_data = Merge.sort(arr.copy(), len(arr))
    output = formatData(m_data, "Merge Sort", i)
    print(output)
    fout.write(output + "\n")

    h_data = Heap.sort(arr.copy())
    output = formatData(h_data, "Heap Sort", i)
    print(output)
    fout.write(output + "\n")

    # i_data = Insertion.sort(arr.copy())
    # output = formatData(i_data, "Insertion Sort", i)
    # print(output)
    # fout.write(output + "\n")

fout.close()
