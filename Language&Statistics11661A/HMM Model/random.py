import random
from forwardANDbackward import *


def random_emission():
    # random_emission =
    NC = {'A', 'E', 'I', 'O', 'U', ' '}
    random_frequency_C = {} #
    random_frequency_NC = {} #a e i o u ' '
    sum_C = 0
    sum_NC = 0
    for c in NC:
        sum_NC += 1
        if random_frequency_NC.has_key(c) == False:
            random_frequency_NC[c] = random.randrange(0,1001)

    for c in '\A-Z':
        sum_C += 1
        if c not in NC:
            if random_frequency_C.has_key(c) == False:
                random_frequency_C[c] = random.randrange(0,1001)

    random_emission_NC = {}
    random_emission_C = {}
    for k, v in random_frequency_NC.items():
        random_emission_NC[k] = v/float(sum_NC)

    for k, v in random_frequency_C.items():
        random_emission_C[k] = v/float(sum_C)

    return random_emission_NC, random_emission_C