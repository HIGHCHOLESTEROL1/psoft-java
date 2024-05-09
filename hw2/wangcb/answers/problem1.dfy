method sumn(n : int) returns (t : int)
    requires n >= 0 // precondition
    ensures t == (n * (n+1)) / 2 // postcondition
    {
        var i := 0;
        t := 0;
        while (i < n) 
            // loop invariants
            invariant i <= n
            invariant t == (i * (i + 1)) / 2
            // decrementing function
            decreases n - i
        {
            i := i + 1;
            t := t + i;
        }
    }


// method Main()
// {
//     // testing function
//     var n1 := sumn(0);
//     var n2 := sumn(2);
//     var n3 := sumn(6);
//     assert n1 == 0;
//     assert n2 == 3;
//     assert n3 == 21;

// }

