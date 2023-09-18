#include <iostream>
#include "Starters.h"
#include "FileOperations.h"
#include "TreasureHuntGame.h"
#include <string>
#include <vector>
#include <sstream>

using namespace std;


// gets mapMatrix sizes from given string data
// e.g. string = 16x18 >  nRowMap = 16, nColumnMap = 18
void getMapMatrixSizes(const string& mapMatrixData,int& nRowMap, int& nColumnMap) {

    vector <int> mapMatrixSizes;

    // use stringstream to split
    stringstream ss(mapMatrixData);
    string size;

    for(int i = 0; i < 2; i++) {

        getline(ss, size, 'x');
        mapMatrixSizes.push_back(stoi(size));
    }

    /*
     * warning: addres of the local variable may escape function ???
    nRowMap = mapMatrixSizes[0];
    nColumnMap = mapMatrixSizes[1];*/

    int nrow = mapMatrixSizes[0];
    int ncolumn = mapMatrixSizes[1];

    nRowMap = nrow;
    nColumnMap = ncolumn;


}


int main(int argc,char** argv) {

    // Datas given in command line arg.

    // matrix datas
    int nRowMap;
    int nColumnMap;
    int nKey;

    // init matrix datas
    getMapMatrixSizes(argv[1],nRowMap,nColumnMap);
    nKey = stoi(argv[2]);

    // file datas
    string mapMatrixFileName = argv[3];
    string keyMatrixFileName = argv[4];
    string outputFileName = argv[5]; // END OF cmd datas



    //GLOBAL VALUES DECLARATIONS

    // rowStartingIndex = starter row in submatrix
    int rowStartingIndex;
    int columnStartingIndex;

    int direction;
    int matrixMul;

    int **mapMatrix = new int*[nRowMap];
    int **keyMatrix = new int*[nKey]; // END OF GLOBAL VALUES


    //INIT GLOBAL VALUES

    vector<string> mapMatrixData = FileOperations::readFromFile(mapMatrixFileName);
    vector<string> keyMatrixData = FileOperations::readFromFile(keyMatrixFileName);

    Starters::fillMatrix(mapMatrix, nColumnMap, mapMatrixData);
    Starters::fillMatrix(keyMatrix, nKey, keyMatrixData);
    Starters::initStarterValues(rowStartingIndex, columnStartingIndex, direction, matrixMul);
    Starters::initOutputFileName(outputFileName);

    // start the game
    TreasureHuntGame::search(direction,rowStartingIndex,columnStartingIndex,matrixMul,mapMatrix,keyMatrix,nKey,nRowMap,nColumnMap);

    //Deallocate memory
    Starters::destroy(mapMatrix, nRowMap);
    Starters::destroy(keyMatrix, nKey);

    return 0;

}
