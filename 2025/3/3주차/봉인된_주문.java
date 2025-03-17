import java.util.*;

class Solution {
    
    public String solution(long n, String[] bans) {
        
        // 정렬을 통해 삭제된 주문 순서대로 확인
        Arrays.sort(bans, (o1, o2) -> compare(o1, o2));
        
        // 삭제된 주문이 현재 주문보다 크다면 주문 숫자 1 증가(삭제 처리)
        for (String ban : bans) {
            if (compare(ban, getSpell(n)) <= 0) n++;
        }
        
        // 최종 주문 반환
        return getSpell(n);
    }
    
    // 주문 간의 크기 비교
    int compare(String s1, String s2) {

        // 두 주문의 길이가 다르다면, 길이가 긴 주문이 더 큼
        if (s1.length() != s2.length()) 
            return Integer.compare(s1.length(), s2.length());
        
        // 두 주문의 길이가 같다면, 앞에서부터 각 주문의 문자 비교
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) < s2.charAt(i)) return -1;
            else if (s1.charAt(i) > s2.charAt(i)) return 1;
        }
        
        // 모든 비교가 끝났다면 동일한 주문으로 판단
        return 0;
    }
    
    // 숫자 -> 주문 변환(진법 변환)
    String getSpell(long n) {
        StringBuilder sb = new StringBuilder();
        
        // 해당 숫자 맨뒤에서부터 26으로 나눈 값 문자로 치환
        while(n > 0){
            sb.append((char)('a' + (n - 1) % 26));
            n = (n - 1) / 26;
        }

        // 뒤에서부터 확인했으므로 뒤집기
        return sb.reverse().toString();
    }
}
