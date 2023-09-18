//
// Created by b2210356016 on 7.12.2022.
//

#ifndef ASM3V2_FILEOPERATIONS_H
#define ASM3V2_FILEOPERATIONS_H

#include <vector>
#include <fstream>
#include <iostream>
#include <sstream>
#include "Customer.h"

class FileOperations{

public:

    //read customer data from file
    static void readFileData(const std::string& fileName,int& cashierNum,std::vector<Customer>& customers) {
        std::fstream myFile;
        std::string line;

        myFile.open(fileName, std::ios::in);

        //trace lines
        int i = 0;

        int numOfLeftCustomers;

        if (myFile.is_open()) {

            while(getline(myFile,line)) {

                //if specified number of customers has been processed
                if(numOfLeftCustomers == 0 && i ==2) {
                    break;}

                // remove 'r' char if exists
                //abc\r\n
                if (!line.empty() && line[line.size() - 2] == '\r') {

                    line.erase(line.size() - 2,1);
                }

                // on first line, get cashier num
                if (i == 0) {
                    cashierNum = std::stoi(line);
                    i++;
                    continue;
                }

                //on second line
                if (i == 1) {
                    numOfLeftCustomers = std::stoi(line);
                    i++;
                    continue;
                }

                //lines after second line
                if (i == 2) {

                    //0 20.75 24.89 10.30
                    std::vector<double> data = split(line);
                    Customer customer(data[0],data[1],data[2],data[3]);
                    customers.push_back(customer);
                    numOfLeftCustomers --;

                }

            }
            myFile.close();
        }

        else {
            std::cout << "ERROR!" << fileName<<" can't be reached ! \nCoffee system cannot be started.\nCheck the file!"<< std::endl;

        }

    }

    //write content of vector to file
    static void writeToFile(const std::string& fileName, const std::vector<double>& outputs) {


        std::fstream myFile;

        myFile.open(fileName,std::ios::out);

        if (myFile.is_open()) {

            for (const double & line:outputs) {

                myFile<<line<<"\n";

            }

            myFile.close();
        }

    }

    //write contents of vectors to file
    static void writeToFile(const std::string& fileName, const std::vector<double>& outputs1,const std::vector<double>& outputs2) {


        std::fstream myFile;

        myFile.open(fileName,std::ios::out);

        if (myFile.is_open()) {

            for (const double & line:outputs1) {

                myFile<<line<<"\n";

            }

            myFile<<"\n";

            for (const double & line:outputs2) {

                myFile<<line<<"\n";

            }

            myFile.close();
        }

    }

    static std::vector<double> split(const std::string& line) {

        // string to save tokens
        std::vector <double> tokens;

        // stringstream class ss
        std::stringstream ss(line);

        std::string element;

        // tokenize with space
        while(getline(ss, element,' '))
        {
            tokens.push_back(stod(element));
        }

        return tokens;
    }

    };

#endif //ASM3V2_FILEOPERATIONS_H
