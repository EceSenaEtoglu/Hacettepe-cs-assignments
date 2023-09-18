//
// Created by b2210356016 on 29.10.2022.
//

#include <string>
#include <sstream>
#include "Starters.h"
#include "GameLogs.h"

void Starters::fillMatrix(int **matrix, int columnsize, const std::vector<std::string>& fileData) {

    // i is for rows, j is for column indexes
    int i = 0;
    int j = 0;

    for (const std::string& line : fileData) {

        j = 0;
        matrix[i] = new int[columnsize];

        //split line accord. to " "
        std::stringstream ss(line);
        std::string numInString;

        // store splitted elements one by one in matrixInput
        while (ss >> numInString) {

            //create string stream for conversion
            std::stringstream strToInt;

            // put numInString to string stream
            strToInt << numInString;

            // get int version and store in matrixInput
            strToInt >> matrix[i][j];

            j++;
        }

        i++;

    }


}

void Starters::initStarterValues(int &rsi, int &csi, int &direction, int &matrixMul) {

    rsi = 0;
    csi = 0;
    direction = 3;

    matrixMul = 0;


}

void Starters::initOutputFileName(std::string fileName) {

    GameLogs::setOutputFileName(std::move(fileName));
}

void Starters::destroy(int **matrix, int nRow) {

    for(int i = 0;i<nRow;i++) {

        delete [] matrix[i];
    }

    delete[] matrix;

}
