public class WeightedUnionUF {

    private int[] id;
    private int[] sz;

    public WeightedUnionUF(int N) {
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            i = id[i];
        }
        return i;
    }

    public boolean find(int p, int q) {
        return root(p) == root(q);
    }

    public void union(int p, int q) {
        int root_p = root(p);
        int root_q = root(q);
        if (sz[root_p] < sz[root_q]) {
            id[root_p] = root_q;
            sz[root_q] += sz[root_p];
        }
        else {
            id[root_q] = root_p;
            sz[root_p] += sz[root_q];
        }
    }

    public static void main() {
    }
}
