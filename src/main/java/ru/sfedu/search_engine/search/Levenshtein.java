package ru.sfedu.search_engine.search;

public class Levenshtein {
    public static int distance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                        dp[i - 1][j] + 1,  // Удаление
                        Math.min(
                                dp[i][j - 1] + 1,  // Вставка
                                dp[i - 1][j - 1] + cost  // Замена
                        )
                );
            }
        }

        return dp[a.length()][b.length()];
    }
}