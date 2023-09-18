//
// Created by b2210356016 on 14.11.2022.
//

#ifndef ASM2_FLAT_H
#define ASM2_FLAT_H


#include <string>

class Flat {

private:

    //IDs of the flats will be unique for the whole street not only for the apartment.
    int id;

    // shows if there are residents, default is false
    int isEmptyFlag = 0;

    // shows the initial bandwidth of the flat
    int initialBandWidth;


public:

    // constructor
    Flat(int id,int initialBandWidth) {

        Flat::id = id;
        Flat::initialBandWidth = initialBandWidth;

        if(Flat::initialBandWidth == 0) {
            Flat::isEmptyFlag = 1;
        }

        else {
            Flat::isEmptyFlag = 0;
        }

    }


    int getId() const {
        return Flat::id;
    }

    bool getIsEmptyFlag() const {
        return Flat::isEmptyFlag;
    }
    void setIsEmptyFlag(int isEmptyFlag) {
        Flat::isEmptyFlag = isEmptyFlag;
    }

    int getInitialBandWidth() const {
        return Flat::initialBandWidth;
    }
    void setInitialBandWidth(int initialBandWidth) {
        Flat::initialBandWidth = initialBandWidth;
    }
};


#endif //ASM2_FLAT_H
