from forwardANDbackward import *

def get_sigma():
    data, sum_total, sum_NC, sum_C, frequency_NC, frequency_C, transition_probs = getparas()

    alpha, beta = get_alphabeta()
    sigma = genMatrix(2, sum_total)

    for obs in range(1, sum_total):
        for state in range(1, 2):
            sigma[state][obs] = alpha[state][obs] * beta[state][obs]
        normonizer = sum(sigma[:][obs])
        for state in range(1, 2):
            sigma[state][obs] = sigma[state][obs] / normonizer

    return sigma
