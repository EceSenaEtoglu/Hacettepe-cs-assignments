`timescale 1 ns/10 ps
module full_adder_tb;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    
    reg A,B,Cin;
    wire S, Cout;

    full_adder UUT(.A(A),.B(B),.Cin(Cin),.S(S),.Cout(Cout));

    initial 
        begin
        $dumpfile("full_adder.vcd");
        $dumpvars;

        A = 0; B = 0; Cin = 0;
        #10 Cin = 1;
        #10 B = 1; Cin = 0;
        #10 Cin = 1;
        #10 A = 1; B = 0; Cin = 0;
        #10 Cin = 1;
        #10 B = 1; Cin = 0;
        #10 Cin = 1;
        #10 $finish;
        
        end

endmodule