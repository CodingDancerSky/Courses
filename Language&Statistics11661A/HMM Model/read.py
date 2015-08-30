import re

#Function:
# clean the training data up by making all letters uppercase,
# replacing all non-alphabetic characters with spaces,
# and then collapsing sequences of spaces to be one space.
#Output:
# a file of a stream of characters
def uppLetters():
    data = open('sources/training data', 'r')
    prodata = open("sources/uppLetters training data.txt", "w")

    # for line in open('sources/training data', 'r'):
    #     line = re.sub("[\W\d]", " ", line.strip())
    #     ret = " ".join(line.upper().split())
    #     data.write(ret)

    uppdata = data.read().upper()
    puredata = re.sub('[^A-Z]', ' ', uppdata)
    ret = " ".join(puredata.split())
    prodata.write(ret)

    return ret

if __name__=="__main__":
    print uppLetters()