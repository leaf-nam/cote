import java.util.*;

class Solution {
    static int[] dx = {-1, 0, 0, 1, 0};
    static int[] dy = {0, -1, 1, 0, 0};
    static int xSize = -1, ySize = -1;
    static int redStartX, redStartY, blueStartX, blueStartY;
    static int redEndX, redEndY, blueEndX, blueEndY;
    
    static class State {
        int redX, redY;
        int blueX, blueY;
        int time;
        boolean[][] redVisited;
        boolean[][] blueVisited;
        
        State(int redX, int redY, int blueX, int blueY, int time,
              boolean[][] redVisited, boolean[][] blueVisited) {
            this.redX = redX; this.redY = redY;
            this.blueX = blueX; this.blueY = blueY;
            this.time = time;
            this.redVisited = redVisited; this.blueVisited = blueVisited;
        }
    }
    
    public int solution(int[][] maze) {
        xSize = maze.length;
        ySize = maze[0].length;
        
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if (maze[i][j] == 1) {
                    redStartX = i; redStartY = j;
                }
                else if (maze[i][j] == 2) {
                    blueStartX = i; blueStartY = j;
                }
                else if (maze[i][j] == 3) {
                    redEndX = i; redEndY = j;
                }
                else if (maze[i][j] == 4) {
                    blueEndX = i; blueEndY = j;
                }
            }
        }
        
        boolean[][] redVisited = new boolean[xSize][ySize];
        boolean[][] blueVisited = new boolean[xSize][ySize];
        redVisited[redStartX][redStartY] = true;
        blueVisited[blueStartX][blueStartY] = true;
        
        Queue<State> q = new ArrayDeque<>();
        State init = new State(redStartX, redStartY, blueStartX, blueStartY, 0, redVisited, blueVisited);
        q.offer(init);
        
        while (!q.isEmpty()) {
            State cur = q.poll();
            if (cur.redX == redEndX && cur.redY == redEndY &&
                cur.blueX == blueEndX && cur.blueY == blueEndY)
                return cur.time;
            
            boolean redDest = (cur.redX == redEndX && cur.redY == redEndY);
            boolean blueDest = (cur.blueX == blueEndX && cur.blueY == blueEndY);
            
            for (int d1 = 0; d1 < 5; d1++) {
                int nextRedX = cur.redX + dx[d1];
                int nextRedY = cur.redY + dy[d1];
                
                if (!redDest) {
                    if (!isRange(nextRedX, nextRedY) || maze[nextRedX][nextRedY] == 5) continue;
                    if (cur.redVisited[nextRedX][nextRedY]) continue;
                }
                else {
                    nextRedX = cur.redX; nextRedY = cur.redY;
                }
                
                for (int d2 = 0; d2 < 5; d2++) {          
                    int nextBlueX = cur.blueX + dx[d2];
                    int nextBlueY = cur.blueY + dy[d2];
                    
                    if (!blueDest) {
                        if (!isRange(nextBlueX, nextBlueY) || maze[nextBlueX][nextBlueY] == 5) continue;
                        if (cur.blueVisited[nextBlueX][nextBlueY]) continue;
                    }
                    else {
                        nextBlueX = cur.blueX; nextBlueY = cur.blueY;
                    }
                    
                    if (nextRedX == nextBlueX && nextRedY == nextBlueY) continue;

                    if (nextRedX == cur.blueX && nextRedY == cur.blueY &&
                        nextBlueX == cur.redX && nextBlueY == cur.redY) continue;
                    
                    boolean[][] newRedVisited = copy2D(cur.redVisited);
                    boolean[][] newBlueVisited = copy2D(cur.blueVisited);
                    
                    if (!redDest) newRedVisited[nextRedX][nextRedY] = true;
                    if (!blueDest) newBlueVisited[nextBlueX][nextBlueY] = true;
                    
                    State nextState = new State(nextRedX, nextRedY, nextBlueX, nextBlueY, cur.time + 1, newRedVisited, newBlueVisited);
                    
                    q.offer(nextState);
                }
            }
        }
        
        return 0;
    }
    
    private boolean[][] copy2D(boolean[][] arr) {
        boolean[][] copy = new boolean[xSize][ySize];
        for (int i = 0; i < xSize; i++) copy[i] = Arrays.copyOf(arr[i], ySize);
        return copy;
    }
    
    private boolean isRange(int x, int y) {
        return (0 <= x && x < xSize && 0 <= y && y < ySize);
    }
}
