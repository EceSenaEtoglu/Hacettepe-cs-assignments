module counter_d(input reset, input clk, input mode, output [2:0] count);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    
    wire[2:0] state;

    dff_sync_res DA (.D((state[1] & (~state[0]) & mode) | ((~state[2]) & state[1] & state[0] & !mode) | (state[2] & ~state[1] & ~mode) |(state[2] & state[0] & mode) | (state[2] & ~state[0] & ~mode)), 
    .clk(clk),.sync_reset(reset),.Q(state[2]));

    dff_sync_res DB (.D((state[1] & ~state[0]) |(!state[1] & state[0] & ~mode) | (~state[2] & state[0]& mode)),
    .clk(clk),.sync_reset(reset),.Q(state[1]));

    dff_sync_res DC (.D((~state[2] & ~state[1] & mode) | (~state[0] & !mode) | (state[2] & state[1] & mode)),
    .clk(clk),.sync_reset(reset),.Q(state[0]));

    assign count = state;





endmodule