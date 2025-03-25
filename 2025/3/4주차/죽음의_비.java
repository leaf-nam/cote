import java.io.*;
import java.util.*;

public class BOJ22944 {
    static int N, H, D;
    static char[][] arr;
    static int[][] visited;
    static int[] dx = {-1, 0, 0, 1};
    static int[] dy = {0, -1, 1, 0};
    static int sx, sy, ex, ey;

    static class Node {
        int x, y, cnt;
        int hp, umbrella;
        Node(int x, int y, int cnt, int hp, int umbrella) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
            this.hp = hp;
            this.umbrella = umbrella;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        arr = new char[N][N];
        visited = new int[N][N];
        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine().toCharArray();
            Arrays.fill(visited[i], -1);
            for (int j = 0; j < N; j++) {
                char c = arr[i][j];
                if (c == 'S') {
                    sx = i; sy = j;
                }
                else if (c == 'E') {
                    ex = i; ey = j;
                }
            }
        }

        Queue<Node> q = new ArrayDeque<>();
        q.add(new Node(sx, sy, 0, H, 0));
        visited[sx][sy] = H;
        int ans = -1;
        loop: while (!q.isEmpty()) {
            Node tmp = q.poll();
            if (tmp.x == ex && tmp.y == ey) {
                ans = tmp.cnt; break;
            }
            for (int i = 0; i < 4; i++) {
                int nx = tmp.x + dx[i];
                int ny = tmp.y + dy[i];
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) continue;

                int newHp = tmp.hp;
                int newUmbrella = tmp.umbrella;
                char cell = arr[nx][ny];

                if (cell == 'E') {
                    ans = tmp.cnt + 1; break loop;
                }

                if (cell == 'U') newUmbrella = D;

                if (newUmbrella > 0) newUmbrella--;
                else newHp--;

                if (newHp <= 0) continue;

                int total = newHp + newUmbrella;
                if (total <= visited[nx][ny]) continue;
                visited[nx][ny] = total;
                q.add(new Node(nx, ny, tmp.cnt + 1, newHp, newUmbrella));
            }
        }
        System.out.println(ans);
    }
}
