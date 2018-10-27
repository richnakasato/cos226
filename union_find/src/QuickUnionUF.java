public class QuickUnionUF {

    private int[] id;

    public QuickUnionUF(int N) {
       id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
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
        int p_root = root(p);
        int q_root = root(q);
        id[p_root] = q_root;
    }

    public static void main() {
    }
}
