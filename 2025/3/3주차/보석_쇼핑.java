import java.util.*;

/**
 슬리이딩 윈도우 진행
 **/
class Solution {

    private static final int INF = (int) 1e9;

    public int[] solution(String[] gems) {
        // 1. 보석의 종류 개수 구하기
        Set<String> gemKinds = new HashSet<>(Arrays.asList(gems));
        int totalKinds = gemKinds.size(); // 보석 종류의 개수

        // 2. 슬라이딩 윈도우 진행
        Map<String, Integer> windowCount = new HashMap<>(); // 구간 내 보석의 개수
        int start = 0, end = 0; // 투 포인터
        int len = gems.length;

        int minLen = INF; // 최소 길이
        int answerStart = 0; // 시작점
        int matchedCount = 0; // 일치한 보석의 개수

        while (end < len) {
            String gem = gems[end]; // 현재 끝지점 가져오기
            windowCount.put(gem, windowCount.getOrDefault(gem, 0) + 1); // 보석 카운팅

            if (windowCount.get(gem) == 1) { // 처음 카운팅 당하는 거라면
                ++matchedCount;
            }

            // 모든 종류를 다 살펴봤는지 확인하기 (시작 지점 줄이면서 최소 구간 구해내기)
            while (matchedCount == totalKinds) {
                int curLen = end - start + 1; // 현재 구간의 길이 (중복된 보석도 있어서 길이 제각각)

                if (curLen < minLen) {
                    minLen = curLen;
                    answerStart = start; // 시작 지점 업데이트
                }

                // start 지점 업데이트
                String leftGem = gems[start];
                int countLeft = windowCount.get(leftGem);
                windowCount.put(leftGem, countLeft - 1);
                if (countLeft - 1 == 0) { // 해당 종류의 보석이 사라진 거라면
                    --matchedCount; // 한 종류의 보석이 사라짐 처리
                }
                ++start;
            }
            ++end;
        }

        return new int[]{answerStart + 1, answerStart + minLen};
    }
}