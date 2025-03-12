import java.io.*;

public class Main {
    static int N;
    static boolean[][] isPalindrome;
    static int[] dp;
    static String s;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        s = br.readLine();
        N = s.length();

        isPalindrome = new boolean[N][N];
        dp = new int[N];

        for (int len = 1; len <= N; len++) {
            for (int i = 0; i + len - 1 < N; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    if (len == 1 || len == 2) isPalindrome[i][j] = true;
                    else isPalindrome[i][j] = isPalindrome[i + 1][j - 1];
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (isPalindrome[0][i]) {
                dp[i] = 1;
            }
            else {
                dp[i] = i + 1;
                for (int j = 1; j <= i; j++)
                    if (isPalindrome[j][i]) dp[i] = Math.min(dp[i], dp[j - 1] + 1);
            }
        }
        System.out.println(dp[N - 1]);
    }
}
