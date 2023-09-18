//
// Created by b2210356016 on 21.11.2022.
//

#ifndef ASM2_FILEOPERATIONS_H
#define ASM2_FILEOPERATIONS_H


#include <vector>
#include <string>

class FileOperations {


public:

    // reads file line by line, stores every line as an element in the vector
    static std::vector<std::string> readFromFile(const std::string& fileName);


    // creates a file with given name (truncates if such file exists)
    // writes each element in vector as a line in the file.
    static void writeToFile(const std::string& fileName, const std::vector<std::string>& outputs);




};


#endif //ASM2_FILEOPERATIONS_H
