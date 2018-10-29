def bonus(word):
    file_name = "wordfunnel\\enable1.txt"
    file = open(file_name, "r")

    ret_arr = []
    for line in file:
        for i in range(0, len(word)):
            create_word = word[:i] + word[i+1:]
            
            if line.replace("\n", "") == create_word and create_word not in ret_arr:
                ret_arr.append(create_word)
    return ret_arr

print(bonus("dragoon"))
print(bonus("boats"))