//
// Created by b2210356016 on 30.10.2022.
//

#ifndef FINDTREASURE_TREASUREHUNTGAME_H
#define FINDTREASURE_TREASUREHUNTGAME_H


//DIRECTIONS:
// -1: no where to go
// 0 : found
// 1:up ; 2:down ; 3:right ; 4:left

class TreasureHuntGame {

public:

    /// core function of game (recursive)
    /// \param mapMatrix : matrix for map
    /// \param keyMatrix: matrix for keys , a square matrix
    /// \param nKey: size of key matrix
    /// \param nRowMap : number of rows of map matrix
    /// \param nColumnMap : number of columns of map matrix
    static void search(int&direction, int& rsi, int& csi, int& mul,
                       int **mapMatrix, int **keyMatrix, int nKey, int nRowMap, int nColumnMap);

private:

    /*
     * updates direction,row starting index, column starting index if given direction is valid
     * if not valid, reverses the direction (1 to 2 : 3 to 4 )
     * if still not valid after reversing, binds -1 to direction to indicate no where to go
     */

    static void updateDirection(int& direction,int& rowStartingIndex, int& columnStartingIndex,int nKey,int mul,int nRowMap,int nColumnMap);

    static void updateIndexes(int direction,int& rowStartingIndex,int& columnStartingIndex,int nKey);

    // check if given direction will be valid on map matrix
    static bool isAValidDirection(int direction,int rowStartingIndex,int columnStartingIndex,int nKey,int nRowMap,int nColumnMap);

    // calculates and updates matrix mul
    // if mul <0 adds 5 until >=0
    static void updateMul(int&mul, int **mapMatrix, int **keyMatrix, int rsi, int csi, int nKey);

    static void reverseDirection(int& direction);

    //check if given index is valid for a column in mapmatrix
    static bool isAValidColumn(int columnIndex,int nColumnMap);
    // check if given index is valid for a row in mapmatrix
    static bool isAValidRow(int rowIndex,int nRowMap);


    // if mul <0 adds 5 until >=0
    static int calcMul(int **mapMatrix, int **keyMatrix, int rsi, int csi, int nKey);

    // creates the log of game and adds it to Gamelogs.logs
    static std::string createLog(int rsi,int csi,int nkey,int mul);


};

#endif //FINDTREASURE_TREASUREHUNTGAME_H


