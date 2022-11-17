package PacDemo;

import java.util.ArrayList;

public class Dijisktra {
	public static int minDistance(int dist[], Boolean sptSet[]) {
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < dist.length; v++)
			if (sptSet[v] == false && dist[v] <= min) {
				min = dist[v];
				min_index = v;
			}

		return min_index;
	}
	public static ArrayList<Integer> mergeMap(ArrayList<Integer> mapU, int destination){
		ArrayList<Integer> result = new ArrayList<Integer>(mapU);
		result.add(destination);
		return result;
	}
	public static DataReceiveSchema dijkstra(int graph[][], int src) {
		int V = graph.length;
		int dist[] = new int[V];
		ArrayList<Integer> pred[] = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			pred[i] = new ArrayList<>();
		}
		Boolean sptSet[] = new Boolean[V];
		for (int i = 0; i < V; i++) {
			dist[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
		}
		dist[src] = 0;
		pred[src].add(src);
		for (int count = 0; count < V - 1; count++) {
			int u = minDistance(dist, sptSet);
			sptSet[u] = true;
			for (int v = 0; v < V; v++)
				if (!sptSet[v] && graph[u][v] != 0
						&& dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
							pred[v] = mergeMap(pred[u], v);
							dist[v] = dist[u] + graph[u][v];
				}
		}
		return new DataReceiveSchema(dist, pred);
	}
}
