//
// Created by b2210356016 on 30.10.2022.
//

#ifndef FINDTREASURE_GAMELOGS_H
#define FINDTREASURE_GAMELOGS_H


#include <vector>
#include <string>

class GameLogs {

public:

    // sets output file name of the game
    static void setOutputFileName(std::string fileName);

    //GETTERS
    static std::string  getOutputFileName();
    static std::vector<std::string> getGameOutputs();

    // add given log in vector gameOutputs
    static void addLog(const std::string& log);



private:

    // output filename to put the logs into
    static std::string outputFileName;

    // outputs of the game
    static std::vector<std::string> gameOutputs;

};


#endif //FINDTREASURE_GAMELOGS_H
