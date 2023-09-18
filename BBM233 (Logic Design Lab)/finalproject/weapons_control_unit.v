`timescale 1us/1ns

module weapons_control_unit(
    input target_locked,
    input clk,
    input rst,
    input fire_command,
    output reg launch_missile,
    output [3:0] remaining_missiles,
    output [1:0] WCU_state
);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    // You should implement the weapons_control_unit module HERE using behavioral design approach. 
    // You should read the instructions first and make sure you understand the problem completely.
    // Please inspect the provided waveforms very carefully and try to produce the same results.

    parameter s0 = 2'b00, s1 = 2'b01, s2 = 2'b10, s3 = 2'b11;
    reg[1:0] state;

    //starter value of currMissile
    integer currMissile = 4;

    //change output synchrounously

    always @(posedge clk) begin

        //GO BACK TO IDLE STATE
        if(rst == 1) begin

            // go to idle state
            state = s0;
            
            //reset
            currMissile = 4;
            launch_missile = 0;
            
        end

        else begin

            case(state)

                s0: if(target_locked == 1)
                    // go to target locked state
                    begin
                    state = s1;
                    end
                
                s1: 
                    // go back to idle state
                    if(target_locked == 0)
                    begin
                    state = s0;
                    end

                    // go to fire state
                    else if(fire_command == 1) begin

                    state = s2;

                    //update missile
                    launch_missile = 1;
                    currMissile -= 1;
                    end
                s2:
                    
                    //if no remaning missiles is left go to out_of_ammo state
                    if(remaining_missiles == 4'b0000) begin

                        //reset
                        launch_missile = 0;

                        //shift state
                        state = s3;

                    end

                    else begin

                        if(target_locked == 0) begin

                            //reset
                            launch_missile = 0;

                            // go back to IDLE STATE
                            state = s0;
                         
                        end

                        else if(target_locked == 1) begin

                            //reset
                            launch_missile = 0;

                            //shift state
                            state = s1;
                        end

                    end
            endcase

        end

        end

        assign WCU_state = state;
        assign remaining_missiles = currMissile;

endmodule