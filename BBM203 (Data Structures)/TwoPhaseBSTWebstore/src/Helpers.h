//
// Created by b2210356016 on 24.12.2022.
//

#ifndef ASM4_HELPERS_H
#define ASM4_HELPERS_H

#include <iostream>
#include <vector>
#include <fstream>
#include <sstream>

class Helpers{

public:

    static std::vector<std::string> readFromFile(const std::string& fileName) {

        std::vector<std::string> lines;

        std::fstream myFile;
        std::string line;

        myFile.open(fileName, std::ios::in);

        if (myFile.is_open()) {

            while(getline(myFile,line)) {

                if(line.empty()) {
                    continue;
                }

                // remove 'r' char if exists
                if (line[line.size() - 1] == '\r') {

                    line.erase(line.size() - 1,1);
                }


                lines.push_back(line);

            }
            myFile.close();
            return lines;

        }

        else {
            std::cout << "ERROR!" << fileName<<" can't be reached!\n Database cannot be initialized. Check the file!"<< std::endl;

        }
    }

    static void appendToFile(const std::string& fileName, const std::string& text) {

        std::ofstream myFile;

        myFile.open(fileName,std::ios::app);

        if (myFile.is_open()) {

            myFile << text << "\n";

            myFile.close();
        }

    }

    static void truncateFile(const std::string& fileName) {

        std::fstream myFile;

        myFile.open(fileName,std::ios::out);

        if (myFile.is_open()) {

            myFile << "";

            myFile.close();
        }

    }

    static std::vector<std::string> split(const std::string& text) {

        // string to save tokens
        std::vector <std::string> tokens;

        // stringstream class ss
        std::stringstream ss(text);

        std::string element;

        // tokenize with space
        while(getline(ss, element,'\t'))
        {
            tokens.push_back(element);
        }

        return tokens;

    }








};

#endif //ASM4_HELPERS_H
