`timescale 1ns/1ps

module counter_tb;
    reg reset, clk, mode; 
    wire [2:0] count;
    integer i;
    parameter clockCycle = 10;
	
	//Comment the next line out when testing your JK flip flop implementation.
    //counter_d uut(reset, clk, mode, count);
    // Uncomment the next line to test your JK flip flop implementation.
    counter_jk c1(reset, clk, mode, count);

    initial begin
        // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
        // Make sure to use $finish statement to avoid infinite loops.

        $dumpfile("test.vcd");
        $dumpvars;

        //initialize
        i = 0;
        mode = 0;
        reset = 1; #20;

        reset = 0; #400; $finish;
    end

    initial begin

        // Generate clock
        // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

        clk = 0;

        forever begin

            #(clockCycle/2);

            clk = ~clk;
        end

    end

        always @(posedge clk) 
        
        begin
            
            if(i < 11) begin
                i += 1;
            end

            else begin
                //reset
                i = 0;

                //delay the change to observe that output will be generated in the next posedge
                #(clockCycle*3/4);
                mode = ~mode;
            end


        end
        

endmodule