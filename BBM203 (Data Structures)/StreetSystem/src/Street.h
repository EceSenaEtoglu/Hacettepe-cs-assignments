//
// Created by b2210356016 on 19.11.2022.
//

#ifndef ASM2_STREET_H
#define ASM2_STREET_H

#include <string>
#include <vector>
#include "ApartmentList.h"

class Street {

private:

    ApartmentList apartments;

public:

    //adds a new apartment at required position in the apartment linked list.
    void add_aparment(const std::string& apartmentName,const std::string& position, const std::string& relApartmentName, int maxBandWidth);

    //removes the apartment from apartment list
    // returns the changed apartmentlist
    // if there are no items in apartment after removal, returns null
    ApartmentList* remove_apartment(const std::string& apartName);

    // adds a new flat to the flatlist of given apartmentName
    // before adding, validates bandwidth accord. max bandwidth possible
    void add_flat(const std::string& apartmentName,int insertionIndex, int initial_bandwidth,int flat_id);

    //makes the flat with given id  in apartment apartmentName empty
    // set flatbandwidth to 0, is emptyFlag to 1
    void make_flat_empty(const std::string& apartmentName,int flat_id);

    //returns the sum of maxbandwidth of the apartments
    std::string find_sum_of_max_bandwidth();

    //lists all apartments and their flats with max_bandwidth value and initial_bandwidth values, respectively.
    // e.g
    //X(105)\tFlat1(25)
    //T(175)\tFlat16(50)\tFlat15(30)\tFlat14(25)
    std::string list_apartments();

    // adds flats of apartname2 to the end of flatlist of apartname1
    // adds apartname2's bandwidth to apartname1
    // removes apartment apartname2 from linked list apartments
    // returns changed flatlistof apartName1
    FlatList& merge_two_apartments(const std::string& apartName1, const std::string& apartName2);

    //relocate flats with flatId's given in movedFlats vector to apartmentName
    // sFýd is the id of flat in aparment to be pushed when relocating
    void relocate_flats_to_same_apartments(const std::string& apartmentName,int sFId,std::vector<std::string> movedFlats);


};


#endif //ASM2_STREET_H
