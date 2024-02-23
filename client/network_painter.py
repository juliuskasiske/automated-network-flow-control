import networkx as nx
import matplotlib.pyplot as plt
def main():
    G = nx.DiGraph()

    # Adding nodes
    G.add_nodes_from(range(1, 11))

    # Adding edges
    edges = [
        (1, 2), (2, 3), (2, 4), (3, 5),
        (5, 6), (4, 7), (7, 8), (8, 9), (8, 10)
    ]
    G.add_edges_from(edges)

    pos = nx.spring_layout(G)  # positions for all nodes

    # Nodes
    nx.draw_networkx_nodes(G, pos, node_size=700)

    # Edges
    nx.draw_networkx_edges(G, pos, edgelist=edges, arrowstyle='->', arrowsize=20)

    # Labels
    nx.draw_networkx_labels(G, pos, font_size=20, font_family='sans-serif')

    plt.axis('off')  # Turn off the axis
    plt.show()

if __name__== "__main__":
    main()
