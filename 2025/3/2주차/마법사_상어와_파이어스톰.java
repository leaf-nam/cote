package solving;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class bj_20058_마법사_상어와_파이어스톰 {

    static int N;
    static int[] L;
    static int[][] ices;
    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        // 변수 초기화
        init();

        // 문제 풀이
        solution();
    }

    private static void solution() {

        // 파이어스톰 사용
        for (int i = 0; i < L.length; i++) firestorm(L[i]);

        // 정답 출력을 위한 변수
        int total = 0, size = 0;

        // BFS를 위한 방문 배열
        boolean[][] vst = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                // 전체 얼음 개수 세기
                total += ices[i][j];

                // 방문하지 않은 지점에서 BFS 수행(얼음 덩어리 크기 확인)
                if (!vst[i][j] && ices[i][j] != 0)
                    size = Math.max(size, bfs(vst, i, j));
            }
        }

        // 전체 개수와 덩어리 크기 출력
        System.out.println(total);
        System.out.println(size);
    }

    // 얼음 덩어리 크기 확인을 위한 BFS
    private static int bfs(boolean[][] vst, int r, int c) {
        Deque<int[]> q = new ArrayDeque<>();
        vst[r][c] = true;
        q.offer(new int[] {r, c});
        int cnt = 0;
        while (!q.isEmpty()) {
            int[] now = q.poll();

            // 방문 시 개수 증가
            cnt++;

            for (int i = 0; i < 4; i++) {
                int nr = now[0] + dr[i], nc = now[1] + dc[i];

                // 얼음이 아닌 지점 만나면 탐색 중단
                if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
                if (vst[nr][nc] || ices[nr][nc] == 0) continue;

                vst[nr][nc] = true;
                q.offer(new int[] {nr, nc});
            }
        }

        // 얼음 덩어리 크기 반환
        return cnt;
    }

    // 파이어스톰 구현
    private static void firestorm(int q) {
        // 해당지점 크기(2^q)로 돌리기
        tornado(q);

        // 얼음 한번에 녹이기(한번에 녹이기 위한 temp배열 정의)
        int[][] temp = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (ices[i][j] > 0) melt(i, j, temp);
            }
        }

        // 녹인 얼음 한번에 적용
        ices = temp;
    }

    // 토네이도 구현
    private static void tornado(int q) {

        // 돌릴 정사각형의 크기
        int n = (int) Math.pow(2, q);

        // 정사각형의 크기만큼 띄워서 이동
        for (int i = 0; i < N; i += n) {
            for (int j = 0; j < N; j += n) {

                // 해당 정사각형 모양 돌리기
                rotate(new int[]{i, j}, n);
            }
        }
    }

    // 얼음 회전 구현
    private static void rotate(int[] s, int n) {

        // 회전하기 전 상태 임시저장
        int[][] temp = new int[n][n];
        for (int i = 0; i < n; i++) {
            int ni = i + s[0];
            for (int j = 0; j < n; j++) {
                int nj = j + s[1];
                temp[i][j] = ices[ni][nj];
            }
        }

        // 회전(시계방향 90도) 구현
        for (int i = 0; i < n; i++) {
            int ni = i + s[0];
            for (int j = 0; j < n; j++) {
                int nj = j + s[1];

                // r -> N - c | c -> r
                ices[ni][nj] = temp[n - j - 1][i];
            }
        }
    }

    // 얼음 녹이기 구현
    private static void melt(int r, int c, int[][] temp) {
        int cnt = 0;

        // 4방 탐색해서 얼음의 개수 확인
        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i], nc = c + dc[i];
            if (nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
            if (ices[nr][nc] == 0) continue;
            cnt++;
        }

        // 4방의 얼음이 3개 미만이면 녹여서(1칸 줄여서) 저장, 3개 이상이면 녹이지 않고 저장
        if (cnt < 3) temp[r][c] = ices[r][c] - 1;
        else temp[r][c] = ices[r][c];
    }

    // 입력받아서 변수 초기화
    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = (int) Math.pow(2, Integer.parseInt(st.nextToken()));
        ices = new int[N][N];

        L = new int[Integer.parseInt(st.nextToken())];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) ices[i][j] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < L.length; i++) L[i] = Integer.parseInt(st.nextToken());
    }

}
