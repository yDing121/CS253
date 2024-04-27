import sys

FILES = [
    "tinyEWD.txt",
    "10000EWD.txt",
    "Rome.txt"
    ]

for fname in FILES:
    with open(fname, "r") as fin:
        n = int(fin.readline().strip())
        lcount = int(fin.readline().strip())
        min_weight = sys.float_info.max
        max_weight = sys.float_info.min

        for _ in range(lcount):
            line = fin.readline().strip()
            # print(line.split())
            # print(line)
            # print(line.split()[2])
            weight = float(line.split()[2])

            if weight > max_weight:
                max_weight = weight
            if weight < min_weight:
                min_weight = weight

        with open(f"{fname}_summary.txt", "w") as fout:
            fout.write(f"Vertex count:\t{n}\n")
            fout.write(f"Edge count:\t{lcount}\n")
            fout.write(f"Minimum edge weight:\t{min_weight}\n")
            fout.write(f"Maximum edge weight:\t{max_weight}\n")

            if min_weight < 0:
                fout.write(f"Negative edge cycle possible:\tYes\n")
            else:
                fout.write(f"Negative edge cycle possible:\tNo\n")

