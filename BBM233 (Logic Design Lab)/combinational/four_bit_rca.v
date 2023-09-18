module four_bit_rca(
    input [3:0] A,
    input [3:0] B,
    input Cin,
    output [3:0] S,
    output Cout
);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    //structural design, explicit assoc.

    wire[2:0] w;

    full_adder f0(.A(A[0]),.B(B[0]),.Cin(Cin),.S(S[0]),.Cout(w[0]));
    full_adder f1 (.A(A[1]),.B(B[1]),.Cin(w[0]),.S(S[1]),.Cout(w[1]));
    full_adder f2 (.A(A[2]),.B(B[2]),.Cin(w[1]),.S(S[2]),.Cout(w[2]));
    full_adder f3 (.A(A[3]),.B(B[3]),.Cin(w[2]),.S(S[3]),.Cout(Cout));


endmodule