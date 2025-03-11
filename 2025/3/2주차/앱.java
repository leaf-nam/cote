package week38;

import java.util.*;
public class Main_bj_7579_앱 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(); //앱 개수
		int m = sc.nextInt(); //최소 바이트
		
		int answer = Integer.MAX_VALUE; //최소 비용을 구해야 하니까...
		
		//바이트
		int[] apps = new int[n];
		for (int i = 0; i < n; i++) {
			apps[i] = sc.nextInt();
		}
		//비용
		int[] costs = new int[n];
		for (int i = 0; i < n; i++) {
			costs[i] = sc.nextInt();
		}
		
		//앱은 n개
		//앱 개수 최대 100개 * 비용 최대 100 = 10000 + 1
		int[][] dp = new int[n][10001];
		
		for (int i = 0; i < n; i++) { //i번째 앱 기준으로 탐색
			int cost = costs[i]; //비활성화 시 드는 비용
			int app = apps[i]; //앱 크기
			
			for (int c = 0; c < 10001; c++) {
				if(i == 0) { //첫번째 앱 일때, (비용이 되면 걍 집어넣는다)
					if(c >= cost) //비활성화 비용이 더 작다면... 그 앱은 비활성화 가능!
						dp[i][c] = app;
				}
				else { //두번째 앱부터는...
					dp[i][c] = dp[i-1][c];
					
					if(c >= cost) { //비활성화 비용이 더 작다면...
						//현재 앱을 비활성화 하지 않을 때 구간값과
						//비활성화 할 때 구간값 중에 
						//큰 값을 선택
						dp[i][c] = Math.max(dp[i-1][c], app+dp[i-1][c-cost]);
					}
				}
				
				if(dp[i][c] >= m) { //업데이트한 dp구간이 m보다 크면 == 요구하는 최소 크기를 만족한다면
					//answer을 더 작은 비용으로 갱신
					answer = Math.min(answer, c); //answer은 비용을 넣는 거니까,,, c와 비교!
				}
			}
			
			
		}
		
		System.out.println(answer);
	}

}
