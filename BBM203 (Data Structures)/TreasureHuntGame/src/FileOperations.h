//
// Created by b2210356016 on 29.10.2022.
//

#ifndef FINDTREASURE_FILEOPERATIONS_H
#define FINDTREASURE_FILEOPERATIONS_H


#include <vector>
#include <string>

class FileOperations {


public:

    // reads file line by line, stores every line as an element in the vector
    static std::vector<std::string> readFromFile(const std::string& fileName);


    // creates a file with given name (truncates)
    // writes each element in vector as a line in the file.
    static void writeToFile(const std::string& fileName, const std::vector<std::string>& outputs);



};


#endif //FINDTREASURE_FILEOPERATIONS_H
