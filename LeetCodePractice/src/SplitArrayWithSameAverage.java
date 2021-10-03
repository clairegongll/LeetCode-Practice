import java.util.*;
import java.util.stream.IntStream;

/**
 * @author lulugong
 * @date 2021/10/03
 * @description
    You are given an integer array nums.

    You should move each element of nums into one of the two arrays A and B such that A and B are non-empty,
    and average(A) == average(B).

    Return true if it is possible to achieve that and false otherwise.

    Note that for an array arr, average(arr) is the sum of all the elements of arr over the length of arr.

    Example :
    Input:
    [1,2,3,4,5,6,7,8]
    Output: true
    Explanation: We can split the array into [1,4,5,8] and [2,3,6,7], and both of them have the average of 4.5.

    Note:
    The length of nums will be in the range [1, 30].
    nums[i] will be in the range of [0, 10000].
 *
 * Adapted from https://leetcode.com/problems/split-array-with-same-average/discuss/170343/Java-Solution-using-Dynamic-Programming-and-Pruning
 */
public class SplitArrayWithSameAverage {

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,4,5,6,7,8};
        int[] nums2 = {3,1};
        System.out.println(splitArraySameAverage(nums1));
        System.out.println(splitArraySameAverage(nums2));
    }

    public static boolean splitArraySameAverage(int[] nums) {
        // Edge cases
        if (nums.length == 1) return false;
        if (nums.length == 2) {
            return nums[0] == nums[1];
        }

        Arrays.sort(nums);

        // https://www.geeksforgeeks.org/intstream-sum-java/
        int sum = IntStream.of(nums).sum();
        int length = nums.length;

        // Check whether it's possible to spliit the array with same average or not
        // If not possible, return false
        if (!canSplit(sum, length)) return false;

        List<Set<Integer>> sets = new ArrayList<>();
        for (int j = 0; j < (length / 2 + 1); j++) {
            sets.add(new HashSet<>());
        }
        sets.get(0).add(0);

        for (int i : nums) {
            for (int right = length / 2; right >= 1; right--) {
                Set<Integer> prevSet = sets.get(right - 1);
                Set<Integer> currSet = sets.get(right);
                for (int num : prevSet) {
                    currSet.add(num + i);
                }
            }
        }

        for (int k = 1; k <= length / 2; k++) {
            if (sum * k % length == 0) {
                int target  = sum * k / length;
                if (sets.get(k).contains(target)) return true;
            }
        }

        return false;
    }

    private static boolean canSplit(int sum, int length) {
        for (int i = 1; i <= length; i++) {
            if (sum * i % length == 0) return true;
        }
        return false;
    }
}
