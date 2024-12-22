import java.util.Arrays;

class Solution {
    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int n = queries.length;
        SegmentTree st = new SegmentTree(heights);
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int a = Math.min(queries[i][0], queries[i][1]);
            int b = Math.max(queries[i][0], queries[i][1]);
            if (a == b) {
                res[i] = a;
                continue;
            }
            if (heights[b] > heights[a]) {
                res[i] = b;
                continue;
            }

            res[i] = -1;
            int h = Math.max(heights[a], heights[b]);

            int l = b;
            int r = heights.length - 1;
            int mid;

            while (l <= r) {
                mid = l + (r - l) / 2;
                if (st.getMaxInRange(b, mid) > h) {
                    res[i] = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }


        }


        return res;
    }
}

class SegmentTree {
    int length;
    int[] segmentTree;
    int[] nums;
    int n;

    public SegmentTree(int[] nums) {
        this.nums = nums;
        n = nums.length;
        if ((n != 1) && ((n & (n - 1)) == 0)) {
            length = n * 2 - 1;
        } else {
            int power = 1;
            while (power < n) {
                power *= 2;
            }
            length = power * 2 - 1;
        }
        segmentTree = new int[length];
        Arrays.fill(segmentTree, Integer.MIN_VALUE);
        buildTree(0, n - 1, 0);
    }

    public void buildTree(int low, int high, int position) {
        if (low == high) {
            segmentTree[position] = nums[low];
            return;
        }
        int mid = low + (high - low) / 2;

        buildTree(low, mid, 2 * position + 1);
        buildTree(mid + 1, high, 2 * position + 2);
        segmentTree[position] = Math.max(segmentTree[2 * position + 1], segmentTree[2 * position + 2]);
    }


    public int getMaxInRange(int queryLow, int queryHigh) {
        return getMaxInRangeHelper(0, n - 1, 0, queryLow, queryHigh);
    }

    private int getMaxInRangeHelper(int low, int high, int position, int queryLow, int queryHigh) {
        if (low > queryHigh || high < queryLow) {
            return Integer.MIN_VALUE;
        }

        if (low >= queryLow && high <= queryHigh) {
            return segmentTree[position];
        }

        int mid = low + (high - low) / 2;
        int leftMax = getMaxInRangeHelper(low, mid, 2 * position + 1, queryLow, queryHigh);
        int rightMax = getMaxInRangeHelper(mid + 1, high, 2 * position + 2, queryLow, queryHigh);

        return Math.max(leftMax, rightMax);
    }
}