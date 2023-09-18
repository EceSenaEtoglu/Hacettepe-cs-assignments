`timescale 1us/1ns

module target_tracking_unit(
    input rst,
    input track_target_command,
    input clk,
    input echo,
    output reg trigger_radar_transmitter,
    output reg [13:0] distance_to_target,
    output reg target_locked,
    output [1:0] TTU_state
);

    // Your code goes here.  DO NOT change anything that is already given! Otherwise, you will not be able to pass the tests!
    // You should implement the target_tracking_unit module HERE using behavioral design approach. 
    // You should read the instructions first and make sure you understand the problem completely.
    // Please inspect the provided waveforms very carefully and try to produce the same results.

    parameter s0 = 2'b00, s1 = 2'b01, s2 = 2'b10, s3 = 2'b11;
    reg [1:0] state, next_state;

    parameter waitingTimeForRadarInms = 50;
    parameter waitingTimeForEchoInms = 100;
    parameter speedOfLightInMeterSecond = 300000000;

    //counters to remain in the state for a specified clock cycle
    integer counterForTransmitState = 0;
    integer counterForEchoState = 0;
    integer counterForTrackState = 0;

    time startingTimerForEcho;
    time signalTimeForEcho;

    //always block to update state
    always @(posedge clk) begin

        state = next_state;

        if(state == s1) begin

            counterForTransmitState += 1;

            // 5cycle
            if(counterForTransmitState == 6) begin

                //go to ECHO state
                state = s2;
                next_state = s2;

                counterForTransmitState = 0;

            end

        end

        else if (state == s2) begin

            counterForEchoState += 1;

            // 10 cycle
            if(counterForEchoState == 11) begin

                //return to the IDLE state without changing any outputs
                state = s0;
                next_state = s0;

                counterForEchoState = 0;
            end
        end

        else if(state == s3) begin

            counterForTrackState += 1;

            //30 cycle
            if(counterForTrackState ==31)begin

                //should return to the IDLE state and reset all outputs.
                state = s0;
                next_state = s0;
                
                trigger_radar_transmitter = 0;
                distance_to_target = 0;
                target_locked = 0;

                counterForTrackState = 0;
            end

        end

    end

    always @(posedge trigger_radar_transmitter) begin
        trigger_radar_transmitter = #waitingTimeForRadarInms ~trigger_radar_transmitter;

        startingTimerForEcho = $time; end

    //always block to create output asynchronously
    //calculate next state to swich to in the next posedge
    always @(state or posedge rst or posedge track_target_command or posedge echo)
    begin 

        if(echo)begin
            signalTimeForEcho = $time;

        end

        if(rst == 1) begin

            //all outputs are 0
            trigger_radar_transmitter = 0;
            distance_to_target = 0;
            target_locked = 0;

            //go back to IDLE state
            next_state = s0;
        end

        else begin
    
        case(state)

        s0: if(track_target_command) begin

            trigger_radar_transmitter = 1;

            //go to TRANSMIT state
            next_state = s1;

            end

        s1:if(track_target_command)begin

            target_locked = 0;

        end


        s2: if(echo)begin

            if(signalTimeForEcho -startingTimerForEcho <= waitingTimeForEchoInms) begin

                 //calc distance
                distance_to_target = speedOfLightInMeterSecond * (signalTimeForEcho-startingTimerForEcho)  / 2000000;
                target_locked = 1;

                //go to TRACK state
                next_state = s3;

            end

            end

        s3:if(track_target_command)begin

            trigger_radar_transmitter = 1;

            //go to TRANSMIT state
            next_state = s1;

        end


        endcase 
    end 
    end

    assign TTU_state = state;

    
endmodule