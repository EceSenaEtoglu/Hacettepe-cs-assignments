import sys


class NoCloseNeighborError(Exception):
    pass


class InvalidSizeError(Exception):
    pass


def matrix_printer(my_matrix):
    # if len(my_matrix) == 0:
    #    print("Matrix is clean!")

    for i in range(len(my_matrix)):
        for j in range(len(my_matrix[i])):
            print(my_matrix[i][j], end=" ")
        print()
    print("\nYour score is: {}".format(score))


def input_validation(lst_input):
    """Checks input validation for game.
    If no row or column is given; given row or column is negative,not an integer throws error,
    If (row,column)  does not refer to any ball (i.e space) in the matrix throws error"""

    try:
        row_input, column_input = lst_input[0], lst_input[1]
        row_input, column_input = int(row_input), int(column_input)
        if row_input < 0 or column_input < 0:
            raise IndexError
        item = matrix[row_input][column_input]
        if item == " ":
            raise IndexError

    except IndexError:
        raise InvalidSizeError

    except ValueError:
        raise InvalidSizeError


def ball_marker(row, column):
    """This func. detects the item in the matrix, if item is a bomb calls another func.
    If not a bomb, finds all neighbors with the SAME COLOUR of item and call another func"""

    item = matrix[row][column]

    if item == "X":
        delete_and_calculate((row, column), item)

    else:
        # neighbor of a ball is 1 square away ( left,right,up,down only) from that ball

        # neighbor of a ball that has same colour with ball is close neighbor to that ball

        neighbor = find_close_neighbor(row, column)   # find close neighbors store their positions in neighbor SET

        if len(neighbor) != 0:                        # if caught close neighbor keep searching
            neighbor.add((row, column))               # add item to the neighbor set
            keep_searching = True

            while keep_searching:                     # keep searching until all adjoining balls with same colour caught
                len_before = len(neighbor)
                for position in neighbor.copy():
                    row, column = position[0], position[1]
                    neighbor.update(find_close_neighbor(row, column))   # find close neighbor of every close neighbor
                len_after = len(neighbor)
                keep_searching = len_before != len_after

            delete_and_calculate(neighbor, item)

        else:
            raise NoCloseNeighborError   # send back to input


def delete_and_calculate(coordinates, ball):  # "coordinates" is a position collection.
    """This func. deletes positions from matrix and calculates score
    Activates chain bombs if there are chains"""

    global score

    if ball == "X":
        bomb_set = set()
        bomb_set.add(coordinates)

        # check chain bomb after every iteration
        while len(bomb_set) != 0:
            for coordinate in bomb_set.copy():
                bomb_set.update(horizontal_delete(*coordinate))    # delete with horizontal delete and store new bombs
                bomb_set.update(vertical_delete(*coordinate))      # delete with vertical delete and store new bombs
                bomb_set.remove(coordinate)                        # del the iterated bomb from set
                matrix[coordinate[0]][coordinate[1]] = " "         # del the iterated bomb from matrix
    else:
        for coordinate in coordinates:
            row, column = coordinate[0], coordinate[1]
            matrix[row][column] = " "
        score = score + colour_dict[ball] * len(coordinates)


def horizontal_delete(row_of_bomb, column_of_bomb):
    global score

    # negative index is the index if we start from end of a list
    # used negative index to do a turn in inner list, to come until to initial position (initial position not included)

    # to calculate neg index given index must be normal (must start from first element)
    # neg_index_in_inner is the neg. index of bomb in inner list

    len_column = len(matrix[row_of_bomb])             # len of inner list is column size in matrix
    neg_index_in_inner = column_of_bomb - len_column
    horizontal_set = set()

    for y in range(column_of_bomb, neg_index_in_inner, -1):
        if matrix[row_of_bomb][y] != "X" and matrix[row_of_bomb][y] != " ":
            score = score + colour_dict[matrix[row_of_bomb][y]]
            matrix[row_of_bomb][y] = " "

        elif matrix[row_of_bomb][y] == "X":
            if y < 0:
                y = y + len_column          # turn to normal index
            horizontal_set.add((row_of_bomb, y))

    return horizontal_set


def vertical_delete(row_of_bomb, column_of_bomb):
    global score

    # negative index is the index if we start from end of a list
    # used negative index to do a turn in outer list, to come until to initial position (initial position not included)

    # to calculate neg index given index must be normal  (must start from first element)

    # neg_index_in_outer means negative index of the list bomb is in, in outer list

    neg_index_in_outer = row_of_bomb - len(matrix)
    vertical_set = set()

    for x in range(row_of_bomb, neg_index_in_outer, -1):
        if matrix[x][column_of_bomb] != "X" and matrix[x][column_of_bomb] != " ":
            score = score + colour_dict[matrix[x][column_of_bomb]]
            matrix[x][column_of_bomb] = " "

        elif matrix[x][column_of_bomb] == "X":
            if x < 0:
                x = x + len(matrix)    # turn to normal index
            vertical_set.add((x, column_of_bomb))

    return vertical_set


def find_close_neighbor(row_of_item, column_of_item):
    """This function checks matrix for close neighbor of given ball,stores these neighbors in a set, returns the set.
     X and space are eliminated during search
    neighbor of a ball that has same colour with ball is close neighbor to that ball
    """

    item = matrix[row_of_item][column_of_item]
    close_neighbors = set()
    if item != "X" and item != " ":
        try:
            if matrix[row_of_item][column_of_item+1] == item:  # check right
                close_neighbors.add((row_of_item, column_of_item + 1))
        except IndexError:
            pass
        try:
            if matrix[row_of_item+1][column_of_item] == item:   # check down
                close_neighbors.add((row_of_item + 1, column_of_item))
        except IndexError:
            pass

        if column_of_item-1 >= 0 and matrix[row_of_item][column_of_item-1] == item:  # check left
            close_neighbors.add((row_of_item, column_of_item - 1))
        if row_of_item - 1 >= 0 and matrix[row_of_item-1][column_of_item] == item:   # check up
            close_neighbors.add((row_of_item - 1, column_of_item))

    return close_neighbors


def playing_game_check():
    for i in range(len(matrix)):          # number of rows
        for j in range(len(matrix[i])):   # number of columns
            item = matrix[i][j]
            if item == "X":
                return True               # if there is bomb(s) game continues

            else:
                if len(find_close_neighbor(i, j)) != 0:     # returns true if only a COLOUR has neighbor
                    return True
    return False


def trim_matrix():
    global matrix
    # P.S this is an abstract function

    # used fix_list function to fix columns in matrix
    # Balls in column are treated as list members, list aka column is fixed with fix_list function

    def fix_list(lst):
        """This in place function puts all " " to the start in the given list without ruining the order of other elements
         i.e [1,2,3," ",4," "] turns into [" "," ",1,2,3,4]"""

        for i in range(len(lst.copy())):
            if lst[i] == " ":
                lst.pop(i)
                lst.insert(0, " ")

    def space_list(list3):   # returns true also in empty list but it is not likely to have an empty list:
        """returns a boolean based on given list, if list consists of only space char. returns true, otherwise false"""
        for x in list3:
            if x == " ":
                continue
            else:
                return False
        return True

    fixed_matrix = [[] for row in matrix]      # create a  matrix to assign to original later
    column_number = len(matrix[0])             # number of columns

    for j in range(column_number):
        column_list = []
        for k in range(len(matrix)):           # iterate rows
            column_list.append(matrix[k][j])   # create a list from column members
            fix_list(column_list)              # fix ball's positions in columns

        if not space_list(column_list):
            # this condition eliminates empty columns
            # if not a space list (list consisting of spaces) add to fixed_matrix

            for row in range(len(matrix)):
                fixed_matrix[row].append(column_list[row])

    matrix = fixed_matrix.copy()    # update "matrix"
    fixed_matrix.clear()

    # loop eliminates empty rows
    for row in matrix.copy():
        if space_list(row):
            matrix.remove(row)


# fill the matrix with given txt file
matrix = []
try:
    with open(sys.argv[1], mode="r", encoding="utf-8") as file:
        for line in file:
            row_list = line.strip("\n").split()
            matrix.append(row_list)
except IOError:
    print("no file named {} found".format(sys.argv[1]))

else:
    # store weights of balls in colour_dict, set initial score to 0, check if playing game
    score = 0
    colour_dict = {"B": 9, "G": 8, "W": 7, "Y": 6, "R": 5, "P": 4, "O": 3, "D": 2, "F": 1}
    playing = playing_game_check()

    matrix_printer(matrix)

    # if not playing:
    #    print("\nSelect a different board you can't play with this")

    # start taking row and column inputs
    while playing:
        print()
        input_list = input("Please enter a row and column number: ").strip().split()
        print()
        try:
            input_validation(input_list)
            ball_marker(int(input_list[0]), int(input_list[1]))
            trim_matrix()
            matrix_printer(matrix)

        except InvalidSizeError:
            print("Please enter a valid size!")

        except NoCloseNeighborError:
            matrix_printer(matrix)

        except Exception:
            print("Oops something went wrong")

        playing = playing_game_check()

print("\nGame over!")