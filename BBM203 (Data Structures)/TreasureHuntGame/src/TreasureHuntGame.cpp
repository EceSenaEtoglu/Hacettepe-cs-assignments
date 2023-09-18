//
// Created by b2210356016 on 30.10.2022.
//

#include <iostream>
#include <sstream>
#include "TreasureHuntGame.h"
#include "GameLogs.h"
#include "FileOperations.h"

void TreasureHuntGame::search(int&direction, int& rsi, int& csi, int& mul,
                              int **mapMatrix, int **keyMatrix, int nKey, int nRowMap, int nColumnMap) {

    //calc mul for this search
    updateMul(mul, mapMatrix, keyMatrix, rsi, csi,nKey);

    // create output for this search
    std::string log = createLog(rsi, csi, nKey,mul);
    GameLogs::addLog(log);

    //print outputs to file
    FileOperations::writeToFile(GameLogs::getOutputFileName(),GameLogs::getGameOutputs());

    // calc next direction
    updateDirection(direction, rsi, csi, nKey,mul,nRowMap,nColumnMap);


    // treasure is found
    if (direction == 0) {
        return;
    }

    // edge case, it was not specified to be considered in the pdf

    /*// if no possible direction to go after reversing
    else if (direction == -1) {

        std::cout << "no where to go! search is stuck" << std::endl;

        return;
    }*/


    // search goes on
    else {
        search(direction, rsi, csi, mul, mapMatrix, keyMatrix, nKey, nRowMap, nColumnMap);
    }
}


void TreasureHuntGame::updateMul(int &mul, int **mapMatrix, int **keyMatrix, int rsi, int csi, int nKey) {

    int tempMul = calcMul(mapMatrix,keyMatrix,rsi,csi,nKey);

    while(tempMul <0) {

        tempMul += 5;
    }

    mul = tempMul;

}

int TreasureHuntGame::calcMul(int **mapMatrix, int **keyMatrix, int rsi, int csi, int nKey) {

    // find submatrix by calculating finish indexes of mapmatrix
    // finish indexes in mapmatrix
    int rowFinishIndex= rsi + (nKey -1);
    int columnFinishIndex= csi + (nKey -1);

    int sum = 0;

    //indexes to traverse in keymatrix
    int rowKey = 0;
    int columnKey = 0;

    // traverse in mapmatrix
    for(int i = rsi; i<=rowFinishIndex;i++) {

        columnKey = 0;

        for(int j = csi;j<=columnFinishIndex;j++) {

            // matrix multiplication
            int value = mapMatrix[i][j] * keyMatrix[rowKey][columnKey];
            sum += value;

            columnKey ++;
        }

        rowKey ++;
    }

    return sum;


}

//rowMidIndex,columnMidIndex:mul
std::string TreasureHuntGame::createLog(int rsi, int csi, int nKey,int mul) {


    int rowMidIndex = (2*rsi + nKey -1) /2;
    int columnMidIndex = (2*csi+nKey-1) / 2;

    // create string
    std::stringstream ss;
    ss << rowMidIndex<<","<<columnMidIndex<<":"<<mul;
    std::string log = ss.str();

    return log;

}


void TreasureHuntGame::updateDirection(int &direction, int &rsi, int &csi, int nKey,int mul,int nRowMap,int nColumnMap) {


    int estDirection = mul % 5;

    if (isAValidDirection(estDirection,rsi,csi,nKey,nRowMap,nColumnMap)) {

        // update direction
        direction = estDirection;

        // update indexes
        updateIndexes(direction,rsi,csi,nKey);
    }

    else {

        reverseDirection(estDirection);

        direction = estDirection;
        updateIndexes(direction,rsi,csi,nKey);



        //edge case that was not specified to check
        // use later

        /*if(isAValidDirection(estDirection,rsi,csi,nKey,nRowMap,nColumnMap)) {

            direction = estDirection;
            updateIndexes(direction,rsi,csi,nKey);

        }

        // if map matrix has dimensions (nkey, k*nkey)
        // direction can be invalid  even after reversing
        // bind -1 to direction in that case

        else {
            // direction -1 indicates nowhere to go
            estDirection = -1;
        }*/

    }

}

void TreasureHuntGame::reverseDirection(int &direction) {


    switch (direction) {

        case 1:
            direction= 2;
            break;

        case 2:
            direction= 1;
            break;

        case 3:
            direction = 4;
            break;

        case 4:
            direction = 3;
            break;


    }

}

bool TreasureHuntGame::isAValidDirection(int direction, int rowStartingIndex, int columnStartingIndex, int nKey,
                                         int nRowMap, int nColumnMap) {


    switch (direction) {

        int tempRowStartingIndex,tempColumnStartingIndex;
        int tempRowFinishIndex, tempColumnFinishIndex;


        case 0:
            return true;
            break;

            // go up
        case 1:

            tempRowStartingIndex = rowStartingIndex - nKey;
            tempRowFinishIndex = tempRowStartingIndex + (nKey -1);
            return isAValidRow(tempRowStartingIndex,nRowMap) && isAValidRow(tempRowFinishIndex,nRowMap);
            break;

            // go down
        case 2:

            tempRowStartingIndex = rowStartingIndex + nKey;
            tempRowFinishIndex = tempRowStartingIndex + (nKey -1);
            return isAValidRow(tempRowStartingIndex,nRowMap) && isAValidRow(tempRowFinishIndex,nRowMap);
            break;

            // go right
        case 3:

            tempColumnStartingIndex = columnStartingIndex + nKey;
            tempColumnFinishIndex = tempColumnStartingIndex + (nKey -1);
            return isAValidColumn(tempColumnStartingIndex,nColumnMap) && isAValidColumn(tempColumnFinishIndex,nColumnMap);
            break;

            // go left
        case 4:

            tempColumnStartingIndex = columnStartingIndex - nKey;
            tempColumnFinishIndex = tempColumnStartingIndex + (nKey-1);
            return isAValidColumn(tempColumnStartingIndex,nColumnMap) && isAValidColumn(tempColumnFinishIndex,nColumnMap);
            break;
    }

}



void TreasureHuntGame::updateIndexes(int direction, int &rowStartingIndex, int &columnStartingIndex,int nKey) {


    switch (direction) {

        // stuck in search
        case -1:
            break;


            //treasure is found
        case 0:
            break;

            // up
        case 1:

            rowStartingIndex -= nKey;
            break;

            //down
        case 2:

            rowStartingIndex += nKey;
            break;

            // right
        case 3:

            columnStartingIndex += nKey;
            break;

            // left
        case 4:

            columnStartingIndex -= nKey;
            break;

    }

}

bool TreasureHuntGame::isAValidColumn(int columnIndex, int nColumnMap) {

    return columnIndex >= 0 && columnIndex < nColumnMap;
}

bool TreasureHuntGame::isAValidRow(int rowIndex, int nRowMap) {

    return rowIndex >= 0 && rowIndex < nRowMap;
}
