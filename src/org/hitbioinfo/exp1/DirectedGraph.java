package org.hitbioinfo.exp1;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectedGraph {
    private List<Integer> mGraph;
    private List<Boolean> mVertexColor;
    private List<Boolean> mEdgeColor;
    private int mSize;

    // Utility function to convert Integer to boolean
    private boolean toBoolean(Integer x) {
        return (x != 0);
    }

    /* ----------------- Constructors ----------------- */

    public DirectedGraph(int size) {
        mSize = size;

        // Initialize mGraph
        Integer[] temp1 = new Integer[size * size];
        Arrays.fill(temp1, 0);
        mGraph = Arrays.asList(temp1);

        // Initialize mVertexColor
        Boolean[] temp2 = new Boolean[size];
        Arrays.fill(temp2, false);
        mVertexColor = Arrays.asList(temp2);

        // Initialize mEdgeColor
        temp2 = new Boolean[size * size];
        Arrays.fill(temp2, false);
        mEdgeColor = Arrays.asList(temp2);
    }

    /* ----------------- Primary Operations ----------------- */

    // Test whether there is an arc from v1 to v2.
    public boolean isArc(int v1, int v2) {
        return toBoolean(mGraph.get(v1 * mSize + v2));
    }

    // Add an arc from v1 to v2. Return false if there is already an arc.
    public boolean addArc(int v1, int v2, Integer weight) {
        if (isArc(v1, v2)) {
            return false;
        }
        mGraph.set(v1 * mSize + v2, weight);
        return true;
    }

    // Return an set contain all the adjacent vertices to src.
    public List<Integer> adjacentVertices(int src) {
        List<Integer> adjVertices = new ArrayList<Integer>();
        for (int i = 0; i < mSize; ++i) {
            if (isArc(src, i)) {
                adjVertices.add(i);
            }
        }
        return adjVertices;
    }

    /* ----------------- Color ----------------- */

    // Test whether the vertex is colored.
    public boolean isColored(int vertex) {
        return mVertexColor.get(vertex);
    }

    // Test whether the edge is colored.
    public boolean isColored(int v1, int v2) {
        return mEdgeColor.get(v1 * mSize + v2);
    }

    // Color a vertex. Return false if it is already colored.
    public boolean color(int vertex) {
        if (isColored(vertex)) {
            return false;
        }
        mVertexColor.set(vertex, true);
        return true;
    }

    // Color an edge. Return false if it is already colored.
    public boolean color(int v1, int v2) {
        if (isColored(v1, v2)) {
            return false;
        }
        mEdgeColor.set(v1 * mSize + v2, true);
        return true;
    }

    // Wipe all colors on vertices and edges.
    public void cleanColors() {
        for (int i = 0; i < mSize; ++i) {
            mVertexColor.set(i, false);
        }
        for (int i = 0; i < mSize * mSize; ++i) {
            mEdgeColor.set(i, false);
        }
    }
}
