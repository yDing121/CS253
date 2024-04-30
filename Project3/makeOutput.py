import os

tiny_out_files = ["dijkstra_vertex1_tinyEWD_output.txt",
                  "bellman_vertex1_tinyEWD_output.txt",
                  "dijkstra_pairs_tinyEWD_output_full.txt",
                  "bellman_pairs_tinyEWD_output_full.txt"]

rome_out_files = ["dijkstra_vertex1_Rome_output.txt",
                  "bellman_vertex1_Rome_output.txt",
                  "dijkstra_pairs_Rome_output_first_500.txt",
                  "dijkstra_pairs_Rome_output_last_500.txt",
                  "bellman_pairs_Rome_output_first_500.txt",
                  "bellman_pairs_Rome_output_last_500.txt"]

tenthou_out_files = ["dijkstra_vertex1_10000EWD_output.txt",
                     "bellman_vertex1_10000EWD_output.txt",
                     "dijkstra_pairs_10000EWD_output_first_500.txt",
                     "dijkstra_pairs_10000EWD_output_last_500.txt",
                     "bellman_pairs_10000EWD_output_first_500.txt",
                     "bellman_pairs_10000EWD_output_last_500.txt"]

names = ["output_tiny", "output_rome", "output_10000"]
labels = ["Dijkstra Single Source Problem",
          "Bellman Ford Single Source Problem",
          "Dijkstra Pairs Problem",
          "Bellman Ford Pairs Problem"]


for thing in zip([tiny_out_files, rome_out_files, tenthou_out_files], names):
    if not os.path.exists(f"./outputs"):
        os.makedirs(f"./outputs")

    with open(f"./outputs/{thing[-1]}", "w") as fout:
        for f in thing[0]:
            fout.write(f"\n\n{'===='*2}{f[:-4]}{'===='*2}\n\n\n")
            with open(f, "r") as fin:
                fout.write("".join(fin.readlines()))

