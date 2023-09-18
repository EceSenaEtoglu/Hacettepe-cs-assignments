module counter_jk(input reset, input clk, input mode, output [2:0] count);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!

    wire[2:0] state;

    //J:BC'M + BCM'
    //K:B'C'M + BCM'
    jk_sync_res JKA (.J((state[1] & ~state[0] & mode) | (state[1] & state[0] & ~mode)), 
    .K((~state[1] & ~state[0] & mode) | (state[1] & state[0] & ~mode)),.clk(clk), .sync_reset(reset),.Q(state[2]));

    //J:A'C + CM'
    //K:CM' + AC
    jk_sync_res JKB (.J((~state[2] & state[0]) | (state[0] & ~mode))
    , .K((state[0] & ~mode) | (state[2] & state[0])), .clk(clk), .sync_reset(reset),.Q(state[1]));

    //J: A'B' + M' + AB
    //K: M' + A'B + AB'
    jk_sync_res JKC (.J((~state[2] & ~state[1]) | (~mode) | (state[2] & state[1])), 
    .K((~mode) | (~state[2] & state[1]) | (state[2] & ~state[1])), .clk(clk), .sync_reset(reset),.Q(state[0]));

    assign count = state;


endmodule