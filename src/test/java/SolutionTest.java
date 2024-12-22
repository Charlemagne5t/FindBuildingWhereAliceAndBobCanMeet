import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void test1(){

    int[] heights = {5,3,8,2,6,1,4,6};
    int[][] queries = {
            {0, 7},
            {3, 5},
            {5, 2},
            {3,0},
            {1,6}
    };

    int[] expected = {7,6,-1,4,6};

        Assert.assertArrayEquals( expected ,new Solution().leftmostBuildingQueries(heights, queries));
    }
}
