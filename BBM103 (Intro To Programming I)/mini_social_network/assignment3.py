import sys


def add_new_user(user):
    """This function adds a new user to network, prints error if user is already in network"""
    global social_network
    if user not in social_network:
        social_network[user] = []      # add user to dictionary
        final.write("User '{}' has been added to the social network successfully\n".format(user))
    else:
        final.write("ERROR: Wrong input type! for 'ANU'! -- This user already exists!!\n")


def del_existing_user(user):
    """This function deletes all relations of given user,prints error if user not in network"""
    global social_network
    if user in social_network:
        for friend in social_network[user]:     # go through user's friends
            social_network[friend].remove(user)  # delete the user from their friend's network

        del social_network[user]                 # delete user from network
        final.write("User '{}' and his/her all relations have been deleted successfully\n".format(user))

    else:
        final.write("ERROR: Wrong input type! for 'DEU'!--There is no user named '{}'!!\n".format(user))


def add_new_friend(source, target):
    """This func takes a source and a target and make them related. Prints error if they are already related or they don't exist in network"""
    global social_network
    if source in social_network and target in social_network:     # check if they both are in social network!

        if target in social_network[source]:    # if target and source are already friends throw error
            final.write("ERROR: A relation between '{}' and '{}' already exists!!.\n".format(source, target))
        else:
            social_network[source].append(target)  # add target to source's network
            social_network[target].append(source)   # add source to target's network
            final.write("Relation between '{}' and '{}' has been added successfully\n".format(source, target))

    elif source not in social_network and target in social_network:
        final.write("ERROR Wrong input type! for 'ANF'!--No user named '{}' found!!\n".format(source))

    elif target not in social_network and source in social_network:
        final.write("ERROR Wrong input type! for 'ANF'!--No user named '{}' found!!\n".format(target))

    else:  # if they both not in social network
        final.write("ERROR Wrong input type! for 'ANF'!--No user named '{}' and '{}' found!!\n".format(source, target))


def del_existing_friend(source, target):
    """This func. deletes relation between source and target. Prints error if they are not related or they don't exist in network."""
    global social_network
    if source in social_network and target in social_network:     # check if they both are in social network!

        if target not in social_network[source]:    # if target and source are not friends throw error
            final.write("ERROR: No relation between '{}' and '{}' found!!\n".format(source, target))
        else:
            social_network[source].remove(target)  # remove target from source's network
            social_network[target].remove(source)   # remove source from target's network
            final.write("Relation between '{}' and '{}' has been deleted successfully\n".format(source, target))

    elif source not in social_network and target in social_network:
        final.write("ERROR Wrong input type! for 'DEF'!--No user named '{}' found!!\n".format(source))

    elif target not in social_network and source in social_network:
        final.write("ERROR Wrong input type! for 'DEF'!--No user named '{}' found!!\n".format(target))

    else:  # if they both not in social network
        final.write("ERROR Wrong input type! for 'DEF'!--No user named '{}' and '{}' found!!\n".format(source, target))


def count_friends(node):
    """This function takes a user and counts friends, prints error if user not in network"""
    if node in social_network:
        count = len(social_network[node])           # len of list indicates number of friends
        final.write("User '{}' has '{}' friends.\n".format(node, count))
    else:
        final.write("ERROR Wrong input type! for 'CF'!--No user named '{}' found!!\n".format(node))


def find_possible_friends(user, distance):
    """This func finds possible friends of user in given max distance, max distance can be 1,2,3. Prints error if user does not exist or distance range is wrong"""
    distance = float(distance)
    if user not in social_network or distance not in range(1, 4):  # use range to give error also in float distance like 2.2
        if user not in social_network:
            final.write("ERROR: Wrong input type! for 'FPF'!--No user named '{}' found!\n".format(user))
        if distance not in range(1, 4):
            final.write("ERROR: Maximum distance must be 1,2 or 3!\n")

    else:
        distance = int(distance)
        possible_friends = set(social_network[user])        # add 1 distance friends of user to a set to avoid duplicate in operations below

        # condition for cases 2 and 3
        if distance > 1:
            for one_distance in social_network[user]:                    # outer loop iterates friends (1 distance) of user
                for friend in social_network[one_distance]:              # inner loop iterates friends of user's friends
                    if friend != user:
                        possible_friends.add(friend)                     # add 2 distance friends (friends of user's friend) don't add user !

        if distance == 3:
            for two_dis in possible_friends - set(social_network[user]):   # eliminate 1 distance friends when iterating, outer loop iterates 2 distance friends of user
                for three_dis in social_network[two_dis]:                  # find friends of two distance friends (three distance friends)
                    possible_friends.add(three_dis)

        # final result
        final.write("User '{}' has {} possible friends when maximum distance is {}\n".format(user, len(possible_friends), distance))
        if len(possible_friends) != 0:
            possible_print = str(sorted(possible_friends))[1:-1]  # sort with string order, put into string, remove list brackets with[1:-1]
            final.write("These possible friends: {{{}}}\n".format(possible_print))
        else:
            final.write("Sorry no possible friends to print for '{}' in max. distance {}\n".format(user, distance))


def suggest_friend(node, md):
    """This func suggests friends THAT ARE NOT ALREADY FRIENDS OF given node, mutuality degree indicates number of 'how many friends of node's friend a person is' """
    """if md is 2 look for 2 and 3, if 3 look for only 3"""
    """Prints error if user does not exist or md is not in correct range or no suggestion found"""
    md = float(md)
    if node in social_network and md in range(2, 4):
        md = int(md)
        counter_dict = {}   # dictionary with key:value as name:number of mutuality

        for friend in social_network[node]:
            # filter 1 distance friends of node before iterating in friend's of nod
            filtered = (set(social_network[friend]) - set(social_network[node])) - {node}
            for possible_suggested in filtered:
                if possible_suggested in counter_dict:
                    counter_dict[possible_suggested] += 1
                else:
                    counter_dict[possible_suggested] = 1

        if md == 2:
            count_2 = [f_2 for f_2 in counter_dict if counter_dict[f_2] == 2]
            count_3 = [f_3 for f_3 in counter_dict if counter_dict[f_3] == 3]
            count_2.sort()
            count_3.sort()
            final.write("Suggestion list for '{}' (when MD is {})\n".format(node, md))
            for x_2 in count_2:
                final.write("'{}' has 2 mutual friends with '{}'\n".format(node, x_2))
            for x_3 in count_3:
                final.write("'{}' has 3 mutual friends with '{}'\n".format(node, x_3))
            if len(count_2+count_3) != 0:
                final.write("The suggested friends for '{}':{}\n".format(node, str(sorted(count_2+count_3))[1:-1]))  # eliminate curly brackets with [1:-1]
            else:
                final.write("ERROR NO SUGGESTION FOUND  for '{}'\n".format(node))

        else:  # md is 3
            count_3 = [f_3 for f_3 in counter_dict if counter_dict[f_3] == 3]
            count_3.sort()
            final.write("Suggestion list for '{}' (when MD is {})\n".format(node, md))
            for x_3 in count_3:
                final.write("'{}' has 3 mutual friends with '{}'\n".format(node, x_3))
            if len(count_3) != 0:
                final.write("The suggested friends for '{}':{}\n".format(node, str(count_3)[1:-1]))
            else:
                final.write("ERROR NO SUGGESTION FOUND  for '{}'\n".format(node))

    else:   # if node not in social network or md not in range
        if node not in social_network:
            final.write("ERROR: Wrong input type! for 'SF'!--No user named '{}' found!\n".format(node))
        if md not in range(2, 4):
            final.write("ERROR: Mutually Degree must be 2 or 3 !!\n")


# create dictionary to store names and friends, fill social_network with smn_path, get commands from command_path
smn_path = sys.argv[1]
command_path = sys.argv[2]
social_network = {}
with open(smn_path, encoding="utf-8") as network_tree:
    for line in network_tree:
        line = line.strip()       # remove \n and space if included

        if len(line.split(":")) == 2:                      # eliminate wrong data a: , a:b:c, :c
            nod, friend_names = line.split(":")
            temp_list = friend_names.strip().split()
            social_network[nod.strip()] = list(set(temp_list))    # list(set(x)) for duplicate values in network


with open(command_path, encoding="utf-8") as command_file:
    commands = [line.strip() for line in command_file if len(line.strip()) != 0]  # handle empty lines, put content to commands with removing \n


with open("output.txt", mode="w", encoding="utf-8") as final:
    for x in commands:
        if x.split()[0] == "ANU":
            add_new_user(x.split()[1])

        elif x.split()[0] == "DEU":
            del_existing_user(x.split()[1])

        elif x.split()[0] == "ANF":
            add_new_friend(x.split()[1], x.split()[2])

        elif x.split()[0] == "DEF":
            del_existing_friend(x.split()[1], x.split()[2])

        elif x.split()[0] == "CF":
            count_friends(x.split()[1])

        elif x.split()[0] == "FPF":
            find_possible_friends(x.split()[1], x.split()[2])

        elif x.split()[0] == "SF":
            suggest_friend(x.split()[1], x.split()[2])

        else:
            final.write("ERROR: WRONG COMMAND\n")

with open("output.txt", mode="r+", encoding="utf-8") as file_:       # this code removes last \n char from file
    file_to_string = file_.read()
    file_.seek(0)
    file_.truncate()
    file_.write(file_to_string.strip())
