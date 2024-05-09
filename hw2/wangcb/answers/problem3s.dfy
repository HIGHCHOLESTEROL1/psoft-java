method differences(arr: seq<int>) returns (diffs: seq<int>)
    requires |arr| > 0 
    ensures |diffs| == |arr| - 1
    ensures forall k: int:: 0 <= k < |diffs| ==> diffs[k] == arr[k+1] - arr[k]
{
    diffs := [];
    var a := 0;
    while( a < |arr| - 1)
        invariant 0 <= a <= |arr| - 1
        invariant |diffs| == a
        invariant forall k: int :: 0 <= k < a ==> diffs[k] == arr[k+1] - arr[k]
        decreases |arr| - 1 - a
    {
        // using sequence concatenation
        diffs := diffs + [arr[a+1] - arr[a]];
        a := a + 1;
    }
}
