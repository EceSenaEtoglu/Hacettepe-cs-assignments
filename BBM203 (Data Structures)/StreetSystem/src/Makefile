networkmap:main
	g++ main.o Street.o FileOperations.o ApartmentList.o FlatList.o -o networkmap

main: Street FileOperations ApartmentList FlatList 
	g++ -std=c++11 -c main.cpp -o main.o

FileOperations:
	g++ -std=c++11 -c FileOperations.cpp -o FileOperations.o

Street:
	g++ -std=c++11 -c Street.cpp -o Street.o

ApartmentList:
	g++ -std=c++11 -c ApartmentList.cpp -o ApartmentList.o

FlatList:
	g++ -std=c++11 -c FlatList.cpp -o FlatList.o

