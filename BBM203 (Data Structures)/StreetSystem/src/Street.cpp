//
// Created by b2210356016 on 19.11.2022.
//

#include "Street.h"


void Street::add_aparment(const std::string& newApartmentName, const std::string& position, const std::string& relApartmentName, int maxBandWidth) {

    if (position == "head") {
        apartments.addApartmentAtFront(newApartmentName, maxBandWidth);
    } else if (position == "after") {
        apartments.addApartmentAfterX(relApartmentName, newApartmentName, maxBandWidth);
    } else if (position == "before") {
        apartments.addApartmentBeforeX(relApartmentName, newApartmentName, maxBandWidth);
    }

}

ApartmentList* Street::remove_apartment(const std::string& apartName) {

    return apartments.removeApartment(apartName);

}

void Street::add_flat(const std::string& apartmentName, int insertionIndex, int initial_bandwidth, int flat_id) {

    //locate apartment
    Apartment& temp = apartments.getApartment(apartmentName);

    int maxNewBandWidth= temp.getMaxBandWidthValue() - temp.flatList.getSumOfFlatBandWidths();

    //validate bandwidth
    if (initial_bandwidth > maxNewBandWidth) {
        initial_bandwidth = maxNewBandWidth;
    }

    temp.flatList.addFlat(insertionIndex,flat_id,initial_bandwidth);

}


void Street::make_flat_empty(const std::string& apartmentName, int flat_id) {

    if (!apartments.isEmpty()) {
        Flat& temp = apartments.getApartment(apartmentName).flatList.getFlatByKey(flat_id);
        temp.setInitialBandWidth(0);
        temp.setIsEmptyFlag(1);
    }

}


std::string Street::find_sum_of_max_bandwidth() {

    std::string s;

    s.append("sum of bandwidth: ");
    int sum = apartments.getSumOfMaxBandwidths();
    s.append(std::to_string(sum));

    return s;
}

std::string Street::list_apartments() {

    std:: string s;

    if (apartments.isEmpty()) {

        s = "There are no apartments in the street.";
        return s;
    }

    s = apartments.toString();
    return s;
}


void Street::relocate_flats_to_same_apartments(const std::string& apartmentName, int sFId, std::vector<std::string> movedFlats) {


    Apartment& destApartment = apartments.getApartment(apartmentName);

    //loop through given flatÄ±d list reversely
    for(int i= movedFlats.size()-1; i >= 0; i--){


        std::string idInStr = movedFlats[i];

        int flatId = std::stoi(idInStr);

        Apartment* srchdApart;

        // traverse street to find the apartment flat is in
        for(int j= 0; j<apartments.getLength();j++) {

            srchdApart = apartments.getApartmentByIndex(j);

            if (srchdApart->flatList.FlatExists(flatId)) {

                break;
            }
        }

        //apartment is found, now get the node and it's list
        FlatList &searchedFlatList = srchdApart->flatList;

        // get the dest list
        FlatList &destFlatList = destApartment.flatList;

        // get bandwidth of flat before moving
        int bwOfSrchdFlat = searchedFlatList.getFlatByKey(flatId).getInitialBandWidth();

        //move flat with flatId next to sFýd
        FlatList::moveFlatToBeforeX(flatId,searchedFlatList,sFId,destFlatList);

        //substract bandwidth of flat from apartment
        srchdApart->setMaxBandWidthValue(srchdApart->getMaxBandWidthValue()-bwOfSrchdFlat);

        // add banwidth to the flat's aparment
        destApartment.setMaxBandWidthValue(destApartment.getMaxBandWidthValue()+bwOfSrchdFlat);

        // update Id for other shiftings
        sFId = flatId;

    }
}


// appends flats of apartment with name apartName2 to end of flatlist of apartName1
// apartName1.flatList, apartName2.flatList can be empty

FlatList& Street::merge_two_apartments(const std::string& apartName1, const std::string& apartName2) {

    Apartment& apartment1 = apartments.getApartment(apartName1);
    Apartment& apartment2 = apartments.getApartment(apartName2);

    // get lists
    FlatList& list1 = apartment1.flatList;
    FlatList& list2 = apartment2.flatList;

    //move flats of list2 to list1
    // make list2 empty (without deallocating memory of it's nodes)
    list1 = FlatList::merge(list1,list2);

    // add apartment2's maxbandwidth to apartment1
    apartment1.setMaxBandWidthValue(apartment2.getMaxBandWidthValue()+apartment1.getMaxBandWidthValue());

    //remove apartment2
    apartments.removeApartment(apartName2);

    return list1;

}


