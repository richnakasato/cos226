public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // this is fast (i.e. quick-find)
    public boolean find(int p, int q) {
        return id[p] == id[q];
    }

    // this is slow (i.e. quick-find == slow-union)
    public void union(int p, int q) {
        int search_id = id[p];
        int replace_id = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == search_id) {
                id[i] = replace_id;
            }
        }
    }

    public static void main() {
    }
}
