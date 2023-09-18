module four_bit_2x1_mux(In_1, In_0, Select, Out);
	input [3:0] In_1;
	input [3:0] In_0;
	input Select;
	output [3:0] Out;
	
	// Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
	

	//assign Out = (~Select & In_0) | (Select & In_1);

	assign Out[3] = (~Select & In_0[3]) | (Select & In_1[3]);
	assign Out[2] = (~Select & In_0[2]) | (Select & In_1[2]);
	assign Out[1] = (~Select & In_0[1]) | (Select & In_1[1]);
	assign Out[0] = (~Select & In_0[0]) | (Select & In_1[0]);


endmodule
