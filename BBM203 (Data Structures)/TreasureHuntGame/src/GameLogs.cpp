//
// Created by b2210356016 on 30.10.2022.
//

#include "GameLogs.h"


std::string GameLogs::outputFileName = "output.txt";
std::vector<std::string> GameLogs::gameOutputs;

void GameLogs::setOutputFileName(std::string fileName) {

    // init file name of class
    GameLogs::outputFileName = std::move(fileName);
}


void GameLogs::addLog(const std::string& log) {

    GameLogs::gameOutputs.push_back(log);

}

std::string GameLogs::getOutputFileName() {

    return GameLogs::outputFileName;
}

std::vector<std::string> GameLogs::getGameOutputs() {
    return GameLogs::gameOutputs;
}

