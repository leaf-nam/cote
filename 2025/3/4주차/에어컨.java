import java.util.*;

class Solution {
    
    final int inf = Integer.MAX_VALUE;
    
    public int solution(int temperature, int t1, int t2, int a, int b, int[] onboard) {
        int len = onboard.length;
        
        // DP[i][j] : 해당 시간[i], 온도[j]에서 사용한 소비전력의 최솟값 저장
        int[][] dp = new int[len][51];
        
        // DP 초기화
        Arrays.stream(dp).forEach(d -> Arrays.fill(d, inf));
        dp[0][temperature + 10] = 0;
        
        // 전체 시간 확인(i : 시간)
        for (int i = 1; i < len; i++) {
            
            // 탑승객 탑승여부
            boolean boarding = onboard[i] == 1;
            
            // 전체 온도 탐색(j : 이전 시간의 온도)
            for (int j = -10; j <= 40; j++) {
                
                if (dp[i - 1][j + 10] == inf) continue;
                
                /* 에어컨 켜기 */
                
                // 에어컨 온도 설정(k : 다음 설정온도)
                for (int k = -10; k <= 40; k++) {
                    
                    // 설정온도가 이전 온도보다 큰지 확인해서 다음온도 결정
                    int temp = j == k? j : j > k? j - 1 : j + 1;
                    if (boarding && (temp < t1 || temp > t2)) continue;
                    
                    // 다음 온도가 이전 온도와 같은지 확인해서 비용 결정
                    int cost = temp == j? b : a;
                    
                    // DP에 비용 최솟값 저장
                    dp[i][temp + 10] = Math.min(dp[i - 1][j + 10] + cost, dp[i][temp + 10]);
                }
                
                /* 에어컨 끄기 */
                
                // 에어컨 껐을 때의 온도 변화 결정
                int temp = j == temperature? j : j > temperature? j - 1 : j + 1;
                if (boarding && (temp < t1 || temp > t2)) continue;
                
                // DP에 비용 최솟값 저장
                dp[i][temp + 10] = Math.min(dp[i - 1][j + 10], dp[i][temp + 10]);
            }
            
        }
        
        // 마지막 DP의 온도 돌면서 비용 최솟값 선택
        int answer = inf;
        for (int i = -10; i <= 40; i++) {
            answer = Math.min(dp[len - 1][i + 10], answer);
        }
        
        return answer;
    }
}
