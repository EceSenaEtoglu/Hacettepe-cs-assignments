//
// Created by b2210356016 on 21.11.2022.
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

            // remove '\r' char if exists

            //abc\r\n
            if (!line.empty() && line[line.size() - 1] == '\r') {

                line.erase(line.size() - 1,1);
            }


            lines.push_back(line);

        }
        myFile.close();
        return lines;

    }

    else {
        std::cout << "ERROR!" << fileName<<" can't be reached ! street system cannot be started. Check the file!"<< std::endl;

    }

}

void FileOperations::writeToFile(const std::string& fileName, const std::vector<std::string>& outputs) {


    std::fstream myFile;

    myFile.open(fileName,std::ios::out);

    int ct = 0;

    if (myFile.is_open()) {

        for (const std::string& line:outputs) {

            // if last item
            if(ct == outputs.size()-1) {
                myFile<<line;
            }

            else {
                myFile<<line<<"\n"<<"\n";

            }

            ct++;

        }

        myFile.close();
    }

}