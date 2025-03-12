import java.util.*;

class Pair {

    private int x;
    private int y;
    private int dir;
    private int cost;

    public Pair(int x, int y, int dir, int cost) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.cost = cost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDir() {
        return dir;
    }

    public int getCost() {
        return cost;
    }
}

class Solution {

    private static final int BLANK = 0;
    private static final int INF = (int) 1e9;

    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};

    public int solution(int[][] board) {
        int answer = 0;
        int n = board.length;
        Queue<Pair> q = new ArrayDeque<>();
        q.offer(new Pair(0, 0, -1, 0));
        int[][][] cost = new int[n][n][4];

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                Arrays.fill(cost[i][j], INF);
            }
        }

        cost[0][0][0] = 0;
        cost[0][0][1] = 0;
        cost[0][0][2] = 0;
        cost[0][0][3] = 0;

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            int curX = cur.getX();
            int curY = cur.getY();
            int curDir = cur.getDir();
            int curCost = cur.getCost();

            for (int dir = 0; dir < 4; ++dir) {
                int nx = curX + dx[dir];
                int ny = curY + dy[dir];
                int plus = 0;

                if (nx < 0 || ny < 0 || nx >= n || ny >= n) {
                    continue;
                }

                if (board[nx][ny] != BLANK) {
                    continue;
                }


                if (curDir == -1 || curDir == dir) {
                    plus = 100;
                } else {
                    plus = 600;
                }

                int nextCost = 0;

                if (curDir == -1) {
                    nextCost = cost[curX][curY][dir] + plus;
                } else {
                    nextCost = cost[curX][curY][curDir] + plus;
                }

                if (cost[nx][ny][dir] <= nextCost) {
                    continue;
                }

                cost[nx][ny][dir] = nextCost;
                q.offer(new Pair(nx, ny, dir, nextCost));
            }
        }

        return Math.min(Math.min(cost[n - 1][n - 1][0], cost[n - 1][n - 1][1]),
                Math.min(cost[n - 1][n - 1][2], cost[n - 1][n - 1][3]));
    }
}