method loopysqrt(n:int) returns (root:int)
    ensures n >= 0 ==>root * root <= n
    ensures n < 0 ==> root * root > n
    {
        root := 0;
        var a := n;
        while ((root + 1) * (root + 1) <= n && a >= 0)
        invariant a <= n
        invariant n >= 0 ==> root * root <= n
        invariant n < 0 ==> root * root > n
        decreases n - (root * root)
        {
            root := root + 1;
            a := a - (2 * root -1);
        }
    }

// method Main(){
//     // testing loopy sqrt works
//     var a := loopysqrt(50);
//     print(a);
    
// }
