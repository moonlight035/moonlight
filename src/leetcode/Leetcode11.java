package leetcode;

public class Leetcode11 {
    public int maxArea(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length; i++)
            for (int j = i+1; j < height.length; j++) {
                int now = (j - i) * Math.min(height[i], height[j]);
                if ( now > max)
                    max = now;
            }
        return max;
    }

    public int maxAreaGood(int[] height) {
        int max = 0;
        for (int i = 0,n=height.length-1; i < n;){
            int minHeight = Math.min(height[i],height[n]);
            max = Math.max(max,minHeight*(n-i));
            while(i<n&&height[i]<=minHeight) i++;
            while(i<n&&height[n]<=minHeight) n--;
        }
        return max;
    }
}
