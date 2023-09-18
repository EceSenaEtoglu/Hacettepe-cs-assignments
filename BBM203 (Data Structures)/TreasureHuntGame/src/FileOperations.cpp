//
// Created by b2210356016 on 29.10.2022.
//

#include "FileOperations.h"
#include <iostream>
#include <fstream>


std::vector<std::string> FileOperations::readFromFile(const std::string& fileName) {

    std::vector<std::string> lines;

    std::fstream myFile;
    std::string line;

    myFile.open(fileName, std::ios::in);

    if (myFile.is_open()) {


        while(getline(myFile,line)) {

            lines.push_back(line);

        }
        myFile.close();
        return lines;

    }

    else {
        std::cout << "ERROR!" << fileName<<  "can't be reached ! Game cannot be started. Check the file!"<< std::endl;

    }

}

void FileOperations::writeToFile(const std::string& fileName, const std::vector<std::string>& outputs) {


    std::fstream myFile;

    myFile.open(fileName,std::ios::out);

    if (myFile.is_open()) {

        for (const std::string& line:outputs) {

            myFile<<line<<"\n";

        }

        myFile.close();
    }

}

