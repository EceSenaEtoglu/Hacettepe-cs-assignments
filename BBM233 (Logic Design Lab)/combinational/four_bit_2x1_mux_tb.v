`timescale 1ns/10ps
module four_bit_2x1_mux_tb;
	
	// Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
	
	reg [3:0] In_1, In_0;
	reg Select;
	wire [3:0] Out;

	//elements to automatically write test cases
	localparam delay = 10 ;
	reg[3:0] counterI0 = 4'b0000;
	reg[3:0] counterI1 = 4'b0000;

	integer i;
	integer j;


	four_bit_2x1_mux UUT(.In_1(In_1),.In_0(In_0),.Select(Select),.Out(Out));

	initial begin

		$dumpfile("4_bit_2x1_mux.vcd");
		$dumpvars;

		//start automating cases

		//cases does not go in binary order on purpose

		Select = 0;
		In_1 = 4'b0000;

		j = 0;
		i = 0;

		for(j = 0; j<16;j++) begin

			//reset inner loop variables
			counterI0 = 4'b0000;
			i = 0;

			for(i = 0; i<16;i++) begin

				In_0 = counterI0;
				counterI0 ++;
				#delay;
				end

			In_1 = counterI1;
			counterI1++;
			

		end

		Select = 1;
		In_0 = 4'b0000;

		j = 0;
		i = 0;

		for(j = 0; j<16;j++) begin

			//reset inner loop variables
			counterI1 = 4'b0000;
			i = 0;

			for(i = 0; i<16;i++) begin

				In_1 = counterI1;
				counterI1 ++;
				#delay;
				end

			In_0 = counterI0;
			counterI0++;

		end

		#delay $finish;
		
	end

endmodule
