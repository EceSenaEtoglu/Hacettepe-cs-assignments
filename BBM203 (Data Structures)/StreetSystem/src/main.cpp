#include <iostream>
#include <sstream>
#include "ApartmentList.h"
#include "Street.h"
#include "FileOperations.h"

using namespace std;

//custom split func
vector<string> split(const string& line, char delim) {

    // string to save tokens
    vector <string> tokens;

    // stringstream class ss
    stringstream ss(line);

    string element;

    // tokenize with char delim
    while(getline(ss, element, delim))
    {
        tokens.push_back(element);
    }

    return tokens;
}

int main(int argc,char** argv) {

    string inputFileName = argv[1];
    string outputFileName = argv[2];

    //core object that the operations will be held on
    Street myStreet;
    //logs to write to outputfile
    vector<string> logsOfCommands;
    //given inputs
    vector<string> inputLines = FileOperations::readFromFile(inputFileName);

    //start parsing commands
    vector<string> tokens;

    for(int i = 0; i<inputLines.size();i++) {

        tokens = split(inputLines[i],'\t');

        string firstWord = "";
        
        //eliminate seg errors
        if(!tokens.empty()) {
            firstWord = tokens[0];
        }

        //add_apartment	X	head	50
        //add_apartment	Z	after_X	75
        if (firstWord == "add_apartment") {

            string pos;
            string relApart;

            if(tokens[2] == "head") {
                pos = "head";
                relApart = " ";
            }

            else {
                vector<string> relPos = split(tokens[2],'_');

                pos = relPos[0];
                relApart = relPos[1]; }


            int bandwidth = stoi(tokens[3]);

            myStreet.add_aparment(tokens[1],pos,relApart,bandwidth);
        }

            //add_flat	C	0	50	3
        else if (firstWord == "add_flat") {

            string apartName = tokens[1];
            int insertionIndex = stoi(tokens[2]);
            int initialBW = stoi(tokens[3]);
            int flatId = stoi(tokens[4]);

            myStreet.add_flat(apartName,insertionIndex,initialBW,flatId);
        }

            //remove_apartment	X
        else if (firstWord == "remove_apartment") {

            string apartName = tokens[1];

            myStreet.remove_apartment(apartName);
        }

        else if (firstWord == "list_apartments") {

            string streetInfo = myStreet.list_apartments();

            logsOfCommands.push_back(streetInfo);

            //write to file
            FileOperations::writeToFile(outputFileName,logsOfCommands);
        }

            //make_flat_empty	E	17
        else if (firstWord == "make_flat_empty") {

            string apartName = tokens[1];
            int flatId = stoi(tokens[2]);

            myStreet.make_flat_empty(apartName,flatId);
        }


            //relocate_flats_to_same_apartment	G	29	[17,25]
            //relocate_flats_to_same_apartment	G	29	[17]
        else if (firstWord == "relocate_flats_to_same_apartment") {

            string desApartName = tokens[1];
            int shiftedFlatId = stoi(tokens[2]);

            //parse flatId list [17,25]

            string lstInStr = tokens[3];
            lstInStr.erase(0, 1);
            lstInStr.erase(lstInStr.size() - 1, 1);

            vector<string> movedIds;

            if (lstInStr.size() == 1) {

                movedIds.push_back(lstInStr);
            }

            else {

                movedIds = split(lstInStr, ',');

            }

            //relocate
            myStreet.relocate_flats_to_same_apartments(desApartName,shiftedFlatId,movedIds);

        }

        else if (firstWord == "find_sum_of_max_bandwidths") {

            string log = myStreet.find_sum_of_max_bandwidth();

            logsOfCommands.push_back(log);

            //write to file
            FileOperations::writeToFile(outputFileName,logsOfCommands);
        }

            //merge_two_apartments	C	U
        else if (firstWord == "merge_two_apartments") {

            string apartName1 = tokens[1];
            string apartName2 = tokens[2];

            myStreet.merge_two_apartments(apartName1,apartName2);
        }

        else {
            cout <<"Error on input file! Program did not crashed but command:" << "'"<< firstWord << "'" << "on line"<< i+1<< "is not valid! Check the input file" << endl;
        }

    }

    return 0;
}