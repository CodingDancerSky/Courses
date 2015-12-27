from read import *
import numpy as np
from decimal import*
from hmm import *

def getparas():
    data = uppLetters()
    NC = ('A','E','I','O','U',' ')
    #get the frequencies of 26 letters and 1 space, C and NC in training set
    frequency_total = {}
    sum_total = 0;
    for c in data:
        sum_total += 1
        if frequency_total.has_key(c) == False:
            frequency_total[c] = 1
        else:
            frequency_total[c] += 1
    # for f in count:
    #     print count.get(f)

    frequency_C = {} #
    frequency_NC = {} #a e i o u ' '
    sum_C = 0
    sum_NC = 0
    for c in data:
        if c in NC:
            sum_NC += 1
            if frequency_NC.has_key(c) == False:
                frequency_NC[c] = 1
            else:
                frequency_NC[c] += 1
        else:
            sum_C += 1
            if frequency_C.has_key(c) == False:
                frequency_C[c] = 1
            else:
                frequency_C[c] += 1
    # print sum_total, sum_NC, sum_C
    transition_probs = [[0.2, 0.8], [0.7, 0.3]]

    return data, sum_total,sum_NC, sum_C, frequency_NC, frequency_C, transition_probs


data, sum_total, sum_NC, sum_C, frequency_NC, frequency_C, transition_probs = getparas()

def genMatrix(row, col):
    matrix = [[0.0 for c in range(col)] for r in range(row)]
    return matrix
    # for i in range(row):
    #     for j in range(col):
    #         print matrix[i][j],
    #     print '\n'

#     #get emissions
def getEmission(state, c):
    emission_NC = {}
    emission_C = {}
    for k, v in frequency_NC.items():
        emission_NC[k] = v/float(sum_NC)

    for k, v in frequency_C.items():
        emission_C[k] = v/float(sum_C)

    # print emission_C
    emission_ = {}
    if state == 1:
        emission_ = emission_NC
    else: emission_ = emission_C

    if(emission_.has_key(c)):
        return emission_.get(c)
    else: return 0

def forward(data, transition_probs, sum_total):
    alpha = genMatrix(2, sum_total)
    alpha[0][0] = 1.0
    for t in range(1, sum_total):
        alpha[0][t] = (alpha[0][t - 1] * transition_probs[0][0] + alpha[1][t - 1] * transition_probs[1][0]) * getEmission(1, data[t])
        alpha[1][t] = (alpha[1][t - 1] * transition_probs[1][1] + alpha[0][t - 1] * transition_probs[0][1]) * getEmission(2, data[t])


    return alpha