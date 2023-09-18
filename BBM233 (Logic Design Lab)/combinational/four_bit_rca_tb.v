`timescale 1 ns/10 ps
module four_bit_rca_tb;

  // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

  //inputs
  reg [3:0] A,B;
  reg Cin;
  
  //outputs
  wire [3:0] S;
  wire Cout;

  //automate cases
  localparam delay = 10 ;
  reg [8:0] counter;
  integer i;

  four_bit_rca UUT(.A(A),.B(B),.Cin(Cin),.S(S),.Cout(Cout));

  initial 
    begin

    $dumpfile("test.vcd");
    $dumpvars;

    counter = 9'b000000000;
    for(i= 0; i<512;i++) begin

      {A,B,Cin} = counter;

      counter ++;

      #delay;

    end

    #delay $finish;

    end


endmodule