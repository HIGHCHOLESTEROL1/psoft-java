method dutch(arr: array?<char>) returns (k: int)
  modifies arr
  requires arr != null && arr.Length >= 0 && (forall x: int :: 0 <= x < arr.Length ==> arr[x] == 'r' || arr[x] == 'b')
  ensures 0 <= k <= arr.Length
  ensures forall n: int :: 0 <= n < k ==> arr[n] == 'r'
  ensures forall m : int :: k <= m < arr.Length ==> arr[m] == 'b'
{
  var i := 0;
  var j := arr.Length;
  while (j > i)
  invariant 0 <= i <= arr.Length && 0 <= j <= arr.Length
  invariant forall f: int :: 0 <= f < i ==> arr[f] == 'r'
  invariant forall b: int :: j <= b < arr.Length ==> arr[b] == 'b'
  decreases j - i
    {
      if arr[j - 1] == 'b'{
        j := j - 1;
      }
      else if arr[i] == 'r'{
        i := i + 1;
      }
      else{
        arr[i] := 'r';
        arr[j - 1] := 'b';
        if (i + 1 > j){
          i := i + 1;
        }
        j := j - 1;
      }
    }
    k := j;
}

