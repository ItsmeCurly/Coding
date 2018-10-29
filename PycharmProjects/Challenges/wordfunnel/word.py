def funnel(original_word, new_word):
    for i in range (0, len(original_word) - 1):
        create_word = original_word[0:i] + original_word[i+1:len(original_word)]
        if create_word == new_word:
            return True
    return False

print(funnel("leave", "eave"))
print(funnel("reset", "rest"))
print(funnel("dragoon", "dragon"))
print(funnel("eave", "leave"))
print(funnel("sleet", "lets"))
print(funnel("skiff", "ski"))