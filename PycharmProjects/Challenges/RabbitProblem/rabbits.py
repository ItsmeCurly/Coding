DEATHAGE = 96
INITIALAGE = 2
FERTILEAGE = 4
BIRTHMALE = 5
BIRTHFEMALE = 9

def initpop(male, female):
    males = [0] * DEATHAGE + 1
    females = [0] * DEATHAGE + 1
    males[INITIALAGE] = male
    females[INITIALAGE] = female
    return males, females

def increasePop(males, females):
    breeding_females = sum(females[FERTILEAGE:])
    newMales = 5 * breeding_females
    newFemales = 9 * breeding_females


def run(male, female, maxPop):
