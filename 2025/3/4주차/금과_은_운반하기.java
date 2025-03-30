/**
 이분탐색을 활용한 풀이
 **/
class Solution {

    public long solution(int a, int b, int[] g, int[] s, int[] w, int[] t) {
        int size = s.length;

        //start: 0
        //end: 걸릴 수 있는 최대 시간:
        //(왕복 옮겨야 할 금과 은)2*10^9 * 2*10^5(모두 왕복시 걸리는 시간) = 4*10^14
        long start = 0L;
        long end = (long)4e14;

        while (start < end) {
            long mid = (start + end) / 2L;

            if (isPossibleTime(a, b, g, s, w, t, size, mid)) {
                end = mid;
            } else  {
                start = mid + 1;
            }

        }

        return start;
    }

    private boolean isPossibleTime(int a, int b, int[] g, int[] s,
                                   int[] w, int[] t, int size, long time) {

        long total_gold_silver_sum = 0;
        long total_gold_sum = 0;
        long total_silver_sum = 0;

        // 금, 은 사이즈 돌아보며 계산하기
        for (int i = 0; i < size; ++i) {
            long count = time / (t[i] * 2L); // 해당 시간에 움직이는 횟수

            if (time % (t[i] * 2L) >= t[i]) { // 편도가 남았다면
                ++count;
            }

            // 금과 은, 금, 은 모두 계산하기
            long all_total = count * w[i];// count 적용했을 때 가져갈 수 있는 총 무게
            total_gold_silver_sum += Math.min(all_total, g[i] + s[i]);
            total_gold_sum += Math.min(all_total, g[i]);
            total_silver_sum += Math.min(all_total, s[i]);

            if (isOver(a, b, total_gold_silver_sum,
                    total_gold_sum, total_silver_sum)) {
                return true;
            }
        }

        if (isOver(a, b, total_gold_silver_sum,
                total_gold_sum, total_silver_sum)) {
            return true;
        }

        return false;
    }

    private boolean isOver(int a, int b, long total_gold_silver_sum,
                           long total_gold_sum, long total_silver_sum) {
        return total_gold_silver_sum >= a + b && total_gold_sum >= a
                && total_silver_sum >= b;
    }
}