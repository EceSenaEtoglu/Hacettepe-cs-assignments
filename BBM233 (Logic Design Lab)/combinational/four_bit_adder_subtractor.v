module four_bit_adder_subtractor(A, B, subtract, Result, Cout);
    input [3:0] A;
    input [3:0] B;
    input subtract;
    output [3:0] Result;
    output Cout;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    wire[7:0] inner;

    two_s_complement tc(.In(B),.Out(inner[3:0]));

    four_bit_2x1_mux mux(.In_0(B),.In_1(inner[3:0]),.Select(subtract),.Out(inner[7:4]));

    four_bit_rca rca(.A(A),.B(inner[7:4]),.Cin(1'b0),.S(Result),.Cout(Cout));



endmodule
