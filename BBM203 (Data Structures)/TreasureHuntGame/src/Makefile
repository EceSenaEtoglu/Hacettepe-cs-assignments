findtreasure:main
	g++ main.o FileOperations.o Starters.o TreasureHuntGame.o GameLogs.o -o findtreasure

main: FileOperations Starters TreasureHuntGame GameLogs
	g++ -std=c++11 -c main.cpp -o main.o

FileOperations:
	g++ -std=c++11 -c FileOperations.cpp -o FileOperations.o

Starters:
	g++ -std=c++11 -c Starters.cpp -o Starters.o

TreasureHuntGame:
	g++ -std=c++11 -c TreasureHuntGame.cpp -o TreasureHuntGame.o

GameLogs:
	g++ -std=c++11 -c GameLogs.cpp -o GameLogs.o
