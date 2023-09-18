# Programmer's note: this algorithm is not so efficient and pretty long, I had to. Don't judge my skills based on this one :)

def matrix_create(n1):             # creates a matrix with n+2 size, map outer squares +, inner squares " "
    matrix = []
    for i in range(0, n1 + 2):
        for j in range(0, n1 + 2):
            if i == 0 or j == 0:
                matrix.append([i, j, "+"])
            elif i == (n1+1) or j == (n1+1):
                matrix.append([i, j, "+"])
            else:
                matrix.append([i, j, " "])
    return matrix


def brush_(x):
    global brush
    if x == "2":   # 2= brush up
        brush = False
    elif x == "1":   # 1= brush down
        brush = True


def right():
    global direction
    direction = direction + 1
    direction = direction % 4


def left():
    global direction
    direction = direction + 3
    direction = direction % 4


def reverse():
    global direction
    direction = direction + 2
    direction = direction % 4


def pos_mod(b, my_pos):  # my_pos is current pos in matrix , n is user specified matrix ,removable place, 
                         #b  is move increase
    # global pos
    if direction == 0:
        size = my_pos[1] + b
        if size % n == 0:
            size = n
        else:
            size = (size % n)

        my_pos[1] = size
        return my_pos

    elif direction == 2:
        size = my_pos[1] - b
        while size <= 0:
            size = size + n
        my_pos[1] = size
        return my_pos

    elif direction == 1:
        size = my_pos[0] + b
        if size % n == 0:
            size = n
        else:
            size = (size % n)
        my_pos[0] = size
        return my_pos

    elif direction == 3:
        size = my_pos[0] - b
        while size <= 0:
            size = size + n
        my_pos[0] = size
        return my_pos


def five_x(a):  # a is 5_a, a is greater than zero
    global my_matrix
    global pos
    if brush:                # if brush equals true
        if direction == 0:
            for x in range(0, a+1):
                if (pos[1]+x) <= n:           # check if current position exists
                    my_index = index_finder(pos[0], pos[1]+x)
                    my_matrix[my_index][2] = "*"
                    continue

                else:
                    mod_element = pos.copy()    # temp position
                    mod_element = pos_mod(x, mod_element)       # fix position
                    my_index = index_finder(pos[0], mod_element[1])
                    my_matrix[my_index][2] = "*"

        elif direction == 2:
            for x in range(0, a+1):
                if 0 < (pos[1]-x) <= n:   # check if current position exists
                    my_index = index_finder(pos[0], pos[1]-x)
                    my_matrix[my_index][2] = "*"
                    continue
                else:
                    mod_element = pos.copy()       # temp position
                    mod_element = pos_mod(x, mod_element)       # fix position
                    my_index = index_finder(pos[0], mod_element[1])
                    my_matrix[my_index][2] = "*"

        elif direction == 1:
            for x in range(0, a+1):
                if (pos[0]+x) <= n:           # check if current position exists
                    my_index = index_finder(pos[0]+x, pos[1])
                    my_matrix[my_index][2] = "*"
                    continue

                else:
                    mod_element = pos.copy()    # temp position
                    mod_element = pos_mod(x, mod_element)       # fix position
                    my_index = index_finder(mod_element[0], mod_element[1])
                    my_matrix[my_index][2] = "*"

        elif direction == 3:
            for x in range(0, a+1):
                if 0 < (pos[0]-x) <= n:   # check if current position exists
                    my_index = index_finder(pos[0]-x, pos[1])
                    my_matrix[my_index][2] = "*"
                    continue
                else:
                    mod_element = pos.copy()       # temp position
                    mod_element = pos_mod(x, mod_element)       # fix position
                    my_index = index_finder(mod_element[0], mod_element[1])
                    my_matrix[my_index][2] = "*"

        pos = pos_mod(a, pos)    # fix final position
    else:
        pos = pos_mod(a, pos)


def jump():
    global brush
    # global pos
    brush = False
    five_x(3)


def index_finder(x, y):   # global matrix for given x,y values returns [x,y,z] index of x,y
    index = 0
    for item in my_matrix:
        if item[0] == x and item[1] == y:
            index = my_matrix.index(item)
            break
    return index


def matrix_print():   # n is accessed from main scope, my_matrix is accessed from main scope too
    for i in range(1, n+3):   # this matrix size is n+2 (includes + aka outer lines)
        for j in range(1, n+3):
            counter = (i-1) * (n+2) + j
            value = my_matrix[counter-1][2]
            print(value, end="")
        print(""
              "")
        

print("<----RULES---->")
print("1. BRUSH DOWN")
print("2. BRUSH UP")
print("3. VEHICLE ROTATES RIGHT")
print("4. VEHICLE ROTATES LEFT")
print("5. MOVE UP TO X")
print("6. JUMP")
print("7. REVERSE DIRECTION")
print("8. VIEW THE MATRIX")
print("0. EXIT")
print("Please enter the commands with a plus sign (+) between them.")

playing = True

while playing:
    commands = input().split("+")
    n = commands.pop(0)
    n = int(n)  # user specified matrix size
    my_matrix = matrix_create(n)     # create matrix with size n+2 + on outer space, inside " "
    pos = [1, 1]   # initial position
    direction = 0  # initial direction  0:right, 2:left , 3:up, 1: down
    brush = False  # initial brush
    for command in commands:
        if command == "1" or command == "2":
            brush_(command)
            if brush:
                location = index_finder(pos[0], pos[1])
                my_matrix[location][2] = "*"
            continue
        elif command == "3":
            right()
            continue
        elif command == "4":
            left()
            continue
        elif command[0:2] == "5_":
            five_x(int(command[2:]))
            continue

        elif command == "6":
            jump()
            continue
        elif command == "7":
            reverse()
            continue
        elif command == "8":
            matrix_print()

        elif command == "0":
            playing = False
            break
        else:
            print("You entered an incorrect command.Please try again!")
            break