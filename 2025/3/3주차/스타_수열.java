import java.util.*;

class Solution {
    public int solution(int[] a) {
        int answer = 0;
        
        int[] cnt = new int[a.length];
        for(int i=0; i<a.length; i++) {
            cnt[a[i]] ++ ;
        }
        
        for(int i=0; i<cnt.length; i++) {
            if(cnt[i] <= answer)
                continue;
            int ans = 0;
            for(int j=0; j<a.length-1; j++) {
                if(a[j]!=a[j+1] && (i==a[j] || i==a[j+1])) {
                    ans++;
                    j++;
                }
            }
            
            answer = Math.max(answer, ans);
        }
        return answer*2; //교집합이 0이라고 하면, 모든 쌍이 (0,?)이므로 총 개수는 0의개수*2
    }
}