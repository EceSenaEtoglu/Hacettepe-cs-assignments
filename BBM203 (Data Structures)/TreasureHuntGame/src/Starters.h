//
// Created by b2210356016 on 29.10.2022.
//

#ifndef FINDTREASURE_STARTERS_H
#define FINDTREASURE_STARTERS_H

#include <vector>

class Starters {

public:

    // fill matrix with lines given in fileData
    // e.g. fileData[0] elements correspond to inner matrix.
    // fileData[0] is split with " " to fill matrix
    static void fillMatrix(int **matrix, int columnsize, const std::vector<std::string>& fileData);

    // initialize starter values
    // direction = 3 ; rsi,csi,mul = 0
    static void initStarterValues(int& rsi, int& csi,int& direction,int& matrixMul);

    //initalize outputfile name in GameLogs class
    static void initOutputFileName(std::string fileName);

    // deallocate memory of given pointer matrix
    static void destroy(int** matrix,int nRow);

};


#endif //FINDTREASURE_STARTERS_H
