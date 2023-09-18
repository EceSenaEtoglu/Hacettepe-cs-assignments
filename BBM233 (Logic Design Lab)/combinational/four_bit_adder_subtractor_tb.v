`timescale 1ns/1ps
module four_bit_adder_subtractor_tb;

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    
    reg [3:0] A,B;
    reg subtract;

    wire [3:0] Result;
    wire Cout;

    //elements to automate tests
    localparam delay = 10;
    reg [7:0] counter = 8'b00000000;


    four_bit_adder_subtractor UUT(.A(A),.B(B),.subtract(subtract),.Result(Result),.Cout(Cout));

    initial begin
        
        $dumpfile("test.vcd");
        $dumpvars;

        //cases for addition
        subtract = 0;
        counter = 8'b00000000;
        for(integer i = 0; i<256;i++) begin

            {A,B} = counter;

            counter ++;

            #delay;

        end

        //cases for substraction
        subtract = 1;
        counter = 8'b00000000;

        for(integer j = 0; j<256; j++) begin
            
            {A,B} = counter;
            counter ++;

            #delay;
        end

        #delay $finish;

    end


endmodule
